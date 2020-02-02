package com.leovegas.wallet.core.dto.response;

import com.leovegas.wallet.core.dto.WalletTransactionDto;
import com.leovegas.wallet.core.dto.request.CreditRequest;

public class CreditResponse extends BaseServiceResponse {
	private WalletTransactionDto transaction;
	private CreditRequest request;

	public CreditResponse(WalletTransactionDto transaction, CreditRequest request) {
		super();
		this.transaction = transaction;
		this.request = request;
	}

	public WalletTransactionDto getTransaction() {
		return transaction;
	}

	public void setTransaction(WalletTransactionDto transaction) {
		this.transaction = transaction;
	}

	public CreditRequest getRequest() {
		return request;
	}

	public void setRequest(CreditRequest request) {
		this.request = request;
	}
}
