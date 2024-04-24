package com.devAlbaladejo.PokeCards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	//Enable cors so that it can access the indicated urls
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				
				registry.addMapping("/login")
					.allowedOrigins("http://localhost:4200")
					.allowedMethods("*")
					.exposedHeaders("*");
				
				registry.addMapping("/api/**")
					.allowedOrigins("http://localhost:4200")
					.allowedMethods("*");
			}
		};
	}
}
