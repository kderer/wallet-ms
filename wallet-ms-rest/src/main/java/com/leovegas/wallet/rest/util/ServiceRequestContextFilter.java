package com.leovegas.wallet.rest.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.leovegas.wallet.core.util.ServiceRequestContext;
import com.leovegas.wallet.rest.constant.ServiceRequestConstants;

@Component
@Order(1)
public class ServiceRequestContextFilter implements Filter, ServiceRequestConstants {

	@Autowired
	private ResourceBundleMessageSource serviceI18nBundle;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServRequest = (HttpServletRequest) request;

		if (!StringUtils.isEmpty(httpServRequest.getRequestURI())) {
			ServiceRequestContext serviceRequestContext = ServiceRequestContext.getCurrentInstance();

			serviceRequestContext.setUserId(httpServRequest.getHeader(SERVICE_REQ_HEADER_USERID));
			serviceRequestContext.setLocale(SERVICE_REQ_CONTEXT_DEFAULT_LOCALE);
			if (!StringUtils.isEmpty(httpServRequest.getHeader(SERVICE_REQ_HEADER_LOCALE))) {
				serviceRequestContext.setLocale(httpServRequest.getHeader(SERVICE_REQ_HEADER_LOCALE));
			}
			serviceRequestContext.setServiceI18nBundle(serviceI18nBundle);
		}

		chain.doFilter(request, response);
	}

}
