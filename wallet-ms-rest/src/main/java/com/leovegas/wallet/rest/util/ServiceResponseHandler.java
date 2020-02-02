package com.leovegas.wallet.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.leovegas.wallet.core.dto.response.BaseServiceResponse;
import com.leovegas.wallet.core.enumaration.ServiceResultEnum;

public abstract class ServiceResponseHandler {
	
	public static <T extends BaseServiceResponse> ResponseEntity<T> handleResponse(T response) {		
		if (ServiceResultEnum.SUCCESS.getCode().equals(response.getResultCode())) {
			return new ResponseEntity<T>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<T>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
