package com.leovegas.wallet.core.db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.leovegas.wallet.core.util.CurrencyUtil;

@Entity
public class WalletTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	@Column(unique = true, nullable = false)
	private String externalId;
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private BigDecimal amount;
	@Column(nullable = false)
	private BigDecimal balanceAfter;	
	@Column(nullable = false)
	private Long createTime;
	
	public Long getId() {
		return id;
	}
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
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
