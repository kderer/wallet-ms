package com.leovegas.wallet.core.enumaration;

public enum ServiceResultEnum {	
	SUCCESS("000000", "message.service.result.success"),
	UNEXPECTED_ERROR("0000001", "message.service.result.error.unexpected"),
	USERID_NOT_FOUND("000002", "message.service.result.error.user.id.notfound"),
	DUPLICATE_TRANSACTION_ID("0000003", "message.service.result.error.transaction.id.existing"),
	INVALID_TRANSACTION_ID("0000004", "message.service.result.error.transaction.id.invalid"),
	INSUFFICIENT_BALANCE("000005", "message.service.result.error.transanction.balance.insufficient"),
	INVALID_AMOUNT("000006", "message.service.result.error.transanction.amount.invalid");
	
	private String code;
	private String i18nKey;
	
	private ServiceResultEnum(String code, String i18nKey) {
		this.code = code;
		this.i18nKey = i18nKey;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getI18nKey() {
		return this.i18nKey;
	}

}
