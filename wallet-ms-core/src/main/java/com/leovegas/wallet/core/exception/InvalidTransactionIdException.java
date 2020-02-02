package com.leovegas.wallet.core.exception;

public class InvalidTransactionIdException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Transaction ID cannot be empty.";
	}

}
