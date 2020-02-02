package com.leovegas.wallet.core.util;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class ServiceRequestContext {
	private String userId;
	private String locale;
	private ResourceBundleMessageSource serviceI18nBundle;	

	private static ThreadLocal<ServiceRequestContext> instance = new ThreadLocal<ServiceRequestContext>() {
		@Override
		protected ServiceRequestContext initialValue() {
			return new ServiceRequestContext();
		}
	};

	private ServiceRequestContext() {

	}

	public static ServiceRequestContext getCurrentInstance() {
		return instance.get();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public ResourceBundleMessageSource getServiceI18nBundle() {
		return serviceI18nBundle;
	}

	public void setServiceI18nBundle(ResourceBundleMessageSource serviceI18nBundle) {
		this.serviceI18nBundle = serviceI18nBundle;
	}
	
	public String resolveI18nMessage(String i18nKey, String... i18nVariables) {
		return serviceI18nBundle.getMessage(i18nKey, i18nVariables, Locale.forLanguageTag(locale));
	}
}
