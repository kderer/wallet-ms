package com.leovegas.wallet.core.dto.request;

import com.leovegas.wallet.core.util.ServiceRequestContext;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseServiceRequest {
	@ApiModelProperty(hidden = true)
	private String userId;
	@ApiModelProperty(hidden = true)
	private String locale;
	
	public BaseServiceRequest() {
		this.userId = ServiceRequestContext.getCurrentInstance().getUserId();
		this.locale = ServiceRequestContext.getCurrentInstance().getLocale();
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getLocale() {
		return locale;
	}

}
