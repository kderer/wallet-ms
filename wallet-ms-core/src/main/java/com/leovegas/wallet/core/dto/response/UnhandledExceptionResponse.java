package com.leovegas.wallet.core.dto.response;

import com.leovegas.wallet.core.constant.ServiceResultEnum;

public class UnhandledExceptionResponse extends BaseServiceResponse {
	
	public UnhandledExceptionResponse(ServiceResultEnum serviceResultEnum, String... i18nVariables) {
		super(serviceResultEnum, i18nVariables);
	}
	

}
