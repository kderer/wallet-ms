package com.leovegas.wallet.core.dto;

import java.math.BigDecimal;

import com.leovegas.wallet.core.db.model.WalletTransaction;
import com.leovegas.wallet.core.util.CurrencyUtil;

public class WalletTransactionDto {

	private String transactionId;
	private String userId;
	private BigDecimal amount;
	private BigDecimal balanceAfter;
	private Long createTime;

	public WalletTransactionDto() {

	}

	public WalletTransactionDto(WalletTransaction walletTransaction) {
		super();
		this.transactionId = walletTransaction.getExternalId();
		this.userId = walletTransaction.getUserId();
		this.createTime = walletTransaction.getCreateTime();
		this.amount = CurrencyUtil.setScale(walletTransaction.getAmount());
		this.balanceAfter = CurrencyUtil.setScale(walletTransaction.getBalanceAfter());
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = CurrencyUtil.setScale(amount);
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = CurrencyUtil.setScale(balanceAfter);
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}
