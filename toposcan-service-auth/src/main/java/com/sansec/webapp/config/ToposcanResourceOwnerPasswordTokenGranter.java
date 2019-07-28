package com.sansec.webapp.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.sansec.module.user.Employee;
import com.sansec.service.oauth.UserDetailsServiceImpl;
import com.util.AESUtil;
import com.util.StringUtil;

public class ToposcanResourceOwnerPasswordTokenGranter extends ResourceOwnerPasswordTokenGranter {

	private UserDetailsService userDetailsService;
	
	public ToposcanResourceOwnerPasswordTokenGranter(
			AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
			OAuth2RequestFactory requestFactory,UserDetailsService userDetailsService) {
		super(null, tokenServices, clientDetailsService, requestFactory);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

		Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
		String username = parameters.get("username");
		String imgCode = parameters.get("imgCode");
		logger.info("验证码：" + imgCode);
		logger.info("登录用户信息：" + username);
		String password = parameters.get("password");
		// Protect from downstream leaks of password
		parameters.remove("password");
		
		if(StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			throw new InvalidGrantException("用户名密码错误");
		}
		
		password = AESUtil.encrypt(password);
		
		Employee emp = ((UserDetailsServiceImpl)userDetailsService).login(username, password);
		if(emp == null) {
			throw new InvalidGrantException("用户名密码错误");
		}
		
		if(emp.getStatus() == 0) {
			throw new InvalidGrantException("用户已被锁定");
		}
		
		UserDetails user = userDetailsService.loadUserByUsername(username);
		
		Authentication userAuth = new UsernamePasswordAuthenticationToken(username, "", user.getAuthorities());
		
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);		
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}
}
