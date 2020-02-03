package com.leovegas.wallet.rest.test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import com.leovegas.wallet.core.util.ServiceRequestContext;
import com.leovegas.wallet.rest.test.config.TestConfig;

@WebMvcTest
@ComponentScan(basePackageClasses = TestConfig.class)
@ActiveProfiles(value ="test")
public abstract class BaseTest {
	protected static final String SR_HEADER_USERID = "sr_userId";
	protected static final String SR_HEADER_LOCALE = "sr_locale";	

	protected void setServiceRequestContextUserId(String userId) {
		ServiceRequestContext.getCurrentInstance().setUserId(userId);
	}

	protected void setServiceRequestContextLocale(String locale) {
		ServiceRequestContext.getCurrentInstance().setLocale(locale);
	}
}
