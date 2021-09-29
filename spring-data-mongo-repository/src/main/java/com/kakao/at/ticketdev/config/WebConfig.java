package com.kakao.at.ticketdev.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "index")
				.setKeepQueryParams(true)
				.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
		super.addViewControllers(registry);
	}
}
