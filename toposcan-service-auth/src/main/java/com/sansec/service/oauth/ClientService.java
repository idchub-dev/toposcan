package com.sansec.service.oauth;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.sansec.mapper.oauth.ClientDetailsMapper;
import com.sansec.module.oauth.OauthClientDetails;
import com.util.StringUtil;

@Service("clientService")
public class ClientService implements ClientDetailsService {

	private final Logger logger = LogManager.getLogger(ClientService.class);
	
	private JsonMapper mapper = createJsonMapper();
	
	@Autowired
	private ClientDetailsMapper clientDetailsMapper;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		try {
			OauthClientDetails jdbcClient = clientDetailsMapper.queryByClientId(clientId);
			if(jdbcClient == null) {
				throw new RuntimeException("客户端ID不存在");
			}
			BaseClientDetails client = new BaseClientDetails();
			client.setClientId(jdbcClient.getClientId());
			client.setClientSecret(jdbcClient.getClientSecret());
			String scope = jdbcClient.getScope();
			if(StringUtil.isNotEmpty(scope) && StringUtils.hasText(scope)) {
				client.setScope(StringUtils.commaDelimitedListToSet(scope));
			}
			String resourceIds = jdbcClient.getResourceId();
			if(StringUtil.isNotEmpty(resourceIds) && StringUtils.hasText(resourceIds)) {
				client.setResourceIds(StringUtils.commaDelimitedListToSet(resourceIds));
			}
			String authorizedGrantTypes = jdbcClient.getAuthorizedGrantTypes();
			if(StringUtil.isNotEmpty(authorizedGrantTypes) && StringUtils.hasText(authorizedGrantTypes)) {
				client.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(authorizedGrantTypes));
			}
			String webServerRedirectUri = jdbcClient.getWebServerRedirectUri();
			if(StringUtil.isNotEmpty(webServerRedirectUri) && StringUtils.hasText(webServerRedirectUri)) {
				client.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(webServerRedirectUri));
			}
			String authorities = jdbcClient.getAuthorities();
			if(StringUtil.isNotEmpty(authorities) && StringUtils.hasText(authorities)) {
				Set<String> grant = StringUtils.commaDelimitedListToSet(authorities);
				Set<GrantedAuthority> grantSet = new HashSet<GrantedAuthority>();
				if(!grant.isEmpty()) {
					for(String role : grant) {
						grantSet.add(new SimpleGrantedAuthority("ROLE_" + role));
					}
				}
				client.setAuthorities(grantSet);
			}
			client.setAccessTokenValiditySeconds(jdbcClient.getAccessTokenValidity());
			client.setRefreshTokenValiditySeconds(jdbcClient.getRefreshTokenValidity());
			String additionalInformation = jdbcClient.getAdditionalInformation();
			if(StringUtil.isNotEmpty(additionalInformation) && StringUtils.hasText(additionalInformation)) {
				@SuppressWarnings("unchecked")
				Map<String,Object> additionalInformationMap = mapper.read(additionalInformation, Map.class);
				client.setAdditionalInformation(additionalInformationMap);
			}
			String autoApprove = jdbcClient.getAutoapprove();
			if(StringUtil.isNotEmpty(autoApprove) && StringUtils.hasText(autoApprove)) {
				client.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(autoApprove));
			}
			return client;
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}
	}

	interface JsonMapper {
		String write(Object input) throws Exception;

		<T> T read(String input, Class<T> type) throws Exception;
	}
	
	private static JsonMapper createJsonMapper() {
		if (ClassUtils.isPresent("org.codehaus.jackson.map.ObjectMapper", null)) {
			return new JacksonMapper();
		}
		else if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", null)) {
			return new Jackson2Mapper();
		}
		return new NotSupportedJsonMapper();
	}

	private static class JacksonMapper implements JsonMapper {
		private org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}

	private static class Jackson2Mapper implements JsonMapper {
		private com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

		@Override
		public String write(Object input) throws Exception {
			return mapper.writeValueAsString(input);
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			return mapper.readValue(input, type);
		}
	}

	private static class NotSupportedJsonMapper implements JsonMapper {
		@Override
		public String write(Object input) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}

		@Override
		public <T> T read(String input, Class<T> type) throws Exception {
			throw new UnsupportedOperationException(
					"Neither Jackson 1 nor 2 is available so JSON conversion cannot be done");
		}
	}
}
