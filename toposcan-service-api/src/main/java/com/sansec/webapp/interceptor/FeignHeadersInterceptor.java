package com.sansec.webapp.interceptor;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
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
	
	@Override
	public void apply(RequestTemplate template) {
		
		HttpServletRequest request = getHttpServletRequest();

		if (Objects.isNull(request)) {
			return;
		}

		Map<String, String> headers = getHeaders(request);
		if (headers.size() > 0) {
			Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				logger.info(entry.getKey());
				template.header(entry.getKey(), entry.getValue());
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
