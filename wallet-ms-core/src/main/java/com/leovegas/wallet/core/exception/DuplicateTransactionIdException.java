package com.leovegas.wallet.core.exception;

public class DuplicateTransactionIdException extends Exception {

	private static final long serialVersionUID = 1L;	
	
	public DuplicateTransactionIdException(String transactionId) {
		super();
		this.transactionId = transactionId;
	}

	private String transactionId;

	public String getTransactionId() {
		return transactionId;
	}
	
	@Override
	public String getMessage() {
		return String.format("Transaction ID %s already exist in the database", transactionId);
	}

}
