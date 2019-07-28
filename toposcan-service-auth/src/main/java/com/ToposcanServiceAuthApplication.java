package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 身份认证服务
 * 
 * @author zhigongzhang
 *
 */
@SpringBootApplication
@EnableAuthorizationServer
public class ToposcanServiceAuthApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ToposcanServiceAuthApplication.class);
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ToposcanServiceAuthApplication.class).run(args);
	}

}