package com.leovegas.wallet.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leovegas.wallet.core.dto.response.BaseServiceResponse;
import com.leovegas.wallet.core.dto.response.UnhandledExceptionResponse;
import com.leovegas.wallet.core.enumaration.ServiceResultEnum;
import com.leovegas.wallet.core.exception.DuplicateTransactionIdException;
import com.leovegas.wallet.core.exception.InvalidAmountException;
import com.leovegas.wallet.core.exception.InvalidTransactionIdException;
import com.leovegas.wallet.rest.util.ServiceResponseHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(DuplicateTransactionIdException.class)
	@ResponseBody
	public ResponseEntity<BaseServiceResponse> handleDuplicateTransactionIdException(HttpServletRequest req,
			DuplicateTransactionIdException ex) {
		UnhandledExceptionResponse response = new UnhandledExceptionResponse(ServiceResultEnum.DUPLICATE_TRANSACTION_ID,
				ex.getTransactionId());

		return ServiceResponseHandler.handleResponse(response);
	}

	@ExceptionHandler(InvalidAmountException.class)
	@ResponseBody
	public ResponseEntity<BaseServiceResponse> handleInvalidAmountException(HttpServletRequest req,
			InvalidAmountException ex) {
		UnhandledExceptionResponse response = new UnhandledExceptionResponse(ServiceResultEnum.INVALID_AMOUNT);

		return ServiceResponseHandler.handleResponse(response);
	}
	
	@ExceptionHandler(InvalidTransactionIdException.class)
	@ResponseBody
	public ResponseEntity<BaseServiceResponse> handleInvalidTransactionIdException(HttpServletRequest req,
			InvalidTransactionIdException ex) {
		UnhandledExceptionResponse response = new UnhandledExceptionResponse(ServiceResultEnum.INVALID_TRANSACTION_ID);

		return ServiceResponseHandler.handleResponse(response);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<BaseServiceResponse> handleException(HttpServletRequest req, Exception ex) {
		String message = ex.getMessage();

		if (StringUtils.isEmpty(message)) {
			Throwable cause = ex.getCause();

			if (cause != null) {
				message = cause.getMessage();
			}

			if (StringUtils.isEmpty(message)) {
				message = ex.getClass().getName();
			}
		}

		logger.error("Unhandled exception caught: {}", message);

		UnhandledExceptionResponse response = new UnhandledExceptionResponse(ServiceResultEnum.UNEXPECTED_ERROR,
				message);

		return ServiceResponseHandler.handleResponse(response);
	}

}
