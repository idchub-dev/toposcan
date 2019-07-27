package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class GatewayApiTest {

	@Test
	public void testSendHeader() throws MalformedURLException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					super.handleError(response);
				}
			}
		});
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		//formData.add("id", "1");
		HttpHeaders headers = new HttpHeaders();
		headers.add("abc", "198");
		Map<String,Object> map = restTemplate.exchange("http://localhost:8050/api/user/queryById?id=1", HttpMethod.GET,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		System.out.println(map);
	}
}
