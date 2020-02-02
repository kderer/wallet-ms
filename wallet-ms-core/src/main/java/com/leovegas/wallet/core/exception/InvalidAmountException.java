package com.leovegas.wallet.core.exception;

public class InvalidAmountException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Amount must be greater than zero";
	}

}
