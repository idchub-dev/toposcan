package com.sansec.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class AuthResourceConfig extends ResourceServerConfigurerAdapter {

	@Value("${spring.api.oauth.clientId}")
	private String clientId;
	
	@Value("${spring.api.oauth.clientSecret}")
	private String clientSecret;
	
	@Value("${spring.api.oauth.checkTokenUrl}")
	private String checkTokenUrl;
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(clientSecret);
        tokenService.setCheckTokenEndpointUrl(checkTokenUrl);
        resources.tokenServices(tokenService);
    }
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//用户管理	
		.antMatchers("/user/queryById","/user/query").hasAnyRole("QUERY_USER");
	}
}
