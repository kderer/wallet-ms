package com.leovegas.wallet.core.dto.response;

import com.leovegas.wallet.core.dto.WalletTransactionDto;
import com.leovegas.wallet.core.dto.request.WithdrawRequest;
import com.leovegas.wallet.core.enumaration.ServiceResultEnum;

public class WithdrawResponse extends BaseServiceResponse {
	private WalletTransactionDto transaction;
	private WithdrawRequest request;

	public WithdrawResponse(ServiceResultEnum serviceResultEnum, String... i18nVariables) {
		super(serviceResultEnum, i18nVariables);
	}
	
	public WithdrawResponse(WalletTransactionDto transaction, WithdrawRequest request) {
		super();
		this.transaction = transaction;
		this.request = request;
	}

	public WalletTransactionDto getTransaction() {
		return transaction;
	}

	public void setTransaction(WalletTransactionDto transactionDto) {
		this.transaction = transactionDto;
	}

	public WithdrawRequest getRequest() {
		return request;
	}

	public void setRequest(WithdrawRequest request) {
		this.request = request;
	}
}
