package com.leovegas.wallet.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class CurrencyUtil {
	
	public static BigDecimal createBigDecimal(Double amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		
		return bdAmount.setScale(6, RoundingMode.HALF_DOWN);		
	}
	
	public static BigDecimal setScale(BigDecimal amount) {
		if (amount != null) {
			return amount.setScale(6, RoundingMode.HALF_DOWN);
		}
		
		return null;
	}

}
