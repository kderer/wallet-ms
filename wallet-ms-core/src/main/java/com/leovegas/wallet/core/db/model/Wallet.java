package com.leovegas.wallet.core.db.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.leovegas.wallet.core.util.CurrencyUtil;

@Entity
public class Wallet {
	
	@Id
	private String userId;	
	
	private BigDecimal balance;
	
	public Wallet() {
		
	}

	public Wallet(String userId, BigDecimal balance) {
		super();
		this.userId = userId;
		this.balance = CurrencyUtil.setScale(balance);
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
