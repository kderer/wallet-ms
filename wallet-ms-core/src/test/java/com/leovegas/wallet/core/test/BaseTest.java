package com.leovegas.wallet.core.test;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ActiveProfiles;

import com.leovegas.wallet.core.WalletMsCore;
import com.leovegas.wallet.core.util.ServiceRequestContext;

@SpringBootTest(classes = WalletMsCore.class)
@ActiveProfiles("test")
public abstract class BaseTest {
	@Autowired
	private ResourceBundleMessageSource serviceI18nBundle;

	@PostConstruct
	public void init() {
		ServiceRequestContext context = ServiceRequestContext.getCurrentInstance();
		context.setUserId("kadir");
		context.setLocale("en");
		context.setServiceI18nBundle(serviceI18nBundle);
	}

	protected void setServiceRequestContextUserId(String userId) {
		ServiceRequestContext.getCurrentInstance().setUserId(userId);
	}

	protected void setServiceRequestContextLocale(String locale) {
		ServiceRequestContext.getCurrentInstance().setLocale(locale);
	}
}
