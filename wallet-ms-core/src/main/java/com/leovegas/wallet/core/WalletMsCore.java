package com.leovegas.wallet.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;

@EnableAutoConfiguration
@ComponentScan("com.leovegas.wallet.core")
public class WalletMsCore {

	@Bean
	public ResourceBundleMessageSource serviceI18nBundle() {
		ResourceBundleMessageSource i18nResource = new ResourceBundleMessageSource();
		i18nResource.setBasenames("i18n.messages");

		return i18nResource;
	}	

}
