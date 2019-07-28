package com.oauth2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class Oauth2TokenTest {
	
	private RestOperations restTemplate;
	
	@Before
	public void init() {
		restTemplate = new RestTemplate();
		((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			// Ignore 400
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					super.handleError(response);
				}
			}
		});
	}
	
	@Test
	public void testLogin() {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("username", "admin");
		formData.add("password", "admin123");
		formData.add("grant_type", "password"); //密码模式
		formData.add("client_id", "toposcan");
		formData.add("client_secret", "toposcan");
		formData.add("scope", "cloud");
		HttpHeaders headers = new HttpHeaders();
		Map map = restTemplate.exchange("http://localhost:8050/auth/oauth/token", HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		System.out.println(map);
	}
	
	@Test
	public void checkToken() throws UnsupportedEncodingException {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("token", "8995bb7b-ced9-4a0a-b8db-80e643d3bf4f");
		String creds = String.format("%s:%s", "toposcan", "toposcan");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + new String(Base64.encode(creds.getBytes("UTF-8"))));
		Map map = restTemplate.exchange("http://localhost:8050/auth/oauth/check_token", HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		System.out.println(map);
	}
	
	@Test
	public void testClient() {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("grant_type", "client_credentials"); //密码模式
		formData.add("client_id", "api");
		formData.add("client_secret", "api");
		formData.add("scope", "cloud");
		HttpHeaders headers = new HttpHeaders();
		Map map = restTemplate.exchange("http://localhost:8050/auth/oauth/token", HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		System.out.println(map);
	}
	
	@Test
	public void testApi() {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer 8995bb7b-ced9-4a0a-b8db-80e643d3bf4f");
		Map map = restTemplate.exchange("http://localhost:8050/api/user/queryById?id=2", HttpMethod.GET,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		System.out.println(map);
	}
}
