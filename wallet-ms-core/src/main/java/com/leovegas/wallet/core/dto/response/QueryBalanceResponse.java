package com.leovegas.wallet.core.dto.response;

import java.math.BigDecimal;

import com.leovegas.wallet.core.enumaration.ServiceResultEnum;
import com.leovegas.wallet.core.util.CurrencyUtil;

public class QueryBalanceResponse extends BaseServiceResponse {
	private String userId;
	private BigDecimal balance;

	public QueryBalanceResponse() {

	}

	public QueryBalanceResponse(String userId, BigDecimal balance) {
		super();
		this.userId = userId;
		this.balance = CurrencyUtil.setScale(balance);
	}

	public QueryBalanceResponse(ServiceResultEnum serviceResultEnum, String... i18nVariables) {
		super(serviceResultEnum, i18nVariables);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = CurrencyUtil.setScale(balance);
	}
}
