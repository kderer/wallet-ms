package com.leovegas.wallet.core.dto.response;

import org.springframework.util.StringUtils;

import com.leovegas.wallet.core.constant.ServiceResultEnum;
import com.leovegas.wallet.core.util.ServiceRequestContext;

public abstract class BaseServiceResponse {
	private ServiceResultEnum serviceResultEnum = ServiceResultEnum.SUCCESS;;
	private String[] i18nVariables;
	private String customResultMessage;

	public BaseServiceResponse() {

	}

	public BaseServiceResponse(ServiceResultEnum serviceResultEnum, String... i18nVariables) {
		this.serviceResultEnum = serviceResultEnum;
		this.i18nVariables = i18nVariables;
	}

	public String getResultCode() {
		return serviceResultEnum.getCode();
	}	

	public void setCustomResultMessage(String customResultMessage) {
		this.customResultMessage = customResultMessage;
	}

	public String getResultMessage() {
		if (!StringUtils.isEmpty(customResultMessage)) {
			return customResultMessage;
		}

		return ServiceRequestContext.getCurrentInstance().resolveI18nMessage(serviceResultEnum.getI18nKey(),
				i18nVariables);
	}

}
