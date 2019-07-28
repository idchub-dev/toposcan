package com.sansec.webapp.interceptor;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 传递头信息设置 此功能需要配置Hystrix隔离策略配置为 hystrix.command.default.execution.isolation.strategy: SEMAPHORE
 * @author zhigongzhang
 *
 */
@Configuration
public class FeignHeadersInterceptor implements RequestInterceptor {

	private final Logger logger = LoggerFactory.getLogger(FeignHeadersInterceptor.class);
	
	@Value("${spring.api.oauth.clientId}")
	private String clientId;
	
	@Value("${spring.api.oauth.clientSecret}")
	private String clientSecret;
	
	@Value("${spring.api.oauth.accessTokenUrl}")
	private String accessTokenUrl;
	
	@Value("${spring.api.oauth.scope}")
	private String scope;
	
	@Override
	public void apply(RequestTemplate template) {
		
		HttpServletRequest request = getHttpServletRequest();

		if (Objects.isNull(request)) {
			return;
		}
		
		RestOperations restTemplate = new RestTemplate();
		((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			// Ignore 400
			public void handleError(ClientHttpResponse response) throws IOException {
				if (response.getStatusCode() != HttpStatus.BAD_REQUEST) {
					super.handleError(response);
				}
			}
		});
		
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("grant_type", "client_credentials"); //密码模式
		formData.add("client_id", clientId);
		formData.add("client_secret", clientSecret);
		formData.add("scope", scope);
		
		@SuppressWarnings("rawtypes")
		Map map = restTemplate.exchange(accessTokenUrl, HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, new HttpHeaders()), Map.class).getBody();
		if(map.containsKey("error") || !map.containsKey("access_token")) {
			throw new RuntimeException(map.get("error_description").toString());
		}
		String token = map.get("access_token").toString();
		
		/*String token = "8b95df3d-957a-4e25-90df-de407b14b4ca";*/
		logger.info("Get token success ......");
		
		Map<String, String> headers = getHeaders(request);
		if (headers.size() > 0) {
			Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				String key = entry.getKey();
				String value = entry.getValue();
				if("authorization".equals(key.toLowerCase())) {
					value = "Bearer " + token;
				}
				System.out.println(key + ":" + value);
				template.header(key, value);
			}
		}
	}

	private HttpServletRequest getHttpServletRequest() {
		try {
			return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		} catch (Exception e) {
			return null;
		}
	}

	private Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> map = new LinkedHashMap<>();
		Enumeration<String> enums = request.getHeaderNames();
		while (enums.hasMoreElements()) {
			String key = enums.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}
