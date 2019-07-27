package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ToposcanServiceUserApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ToposcanServiceUserApplication.class);
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ToposcanServiceUserApplication.class).run(args);
	}

}
