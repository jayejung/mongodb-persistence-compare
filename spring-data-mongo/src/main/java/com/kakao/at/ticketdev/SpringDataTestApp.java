package com.kakao.at.ticketdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringDataTestApp extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(SpringDataTestApp.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringDataTestApp.class);
	}
}
