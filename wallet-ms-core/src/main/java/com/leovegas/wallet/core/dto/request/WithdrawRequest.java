package com.leovegas.wallet.core.dto.request;

import java.math.BigDecimal;

import com.leovegas.wallet.core.util.CurrencyUtil;

public class WithdrawRequest extends BaseServiceRequest {
	private BigDecimal amount;
	private String transactionId;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = CurrencyUtil.setScale(amount);
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
