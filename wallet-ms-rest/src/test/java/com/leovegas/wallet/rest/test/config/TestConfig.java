package com.leovegas.wallet.rest.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class TestConfig {
	
	@Bean
	public ResourceBundleMessageSource serviceI18nBundle() {
		ResourceBundleMessageSource i18nResource = new ResourceBundleMessageSource();
		i18nResource.setUseCodeAsDefaultMessage(true);

		return i18nResource;
	}

}
