package com.leovegas.wallet.core.test.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.leovegas.wallet.core.db.model.Wallet;
import com.leovegas.wallet.core.db.repository.WalletRepository;
import com.leovegas.wallet.core.test.BaseTest;
import com.leovegas.wallet.core.util.CurrencyUtil;

public class WalletRepositoryTest extends BaseTest {
	private static final String WALLET_REPO_TEST_USER_ID = "wallet_repo_test_user";

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private TransactionTemplate txTemplate;

	@Test
	public void testAddBalance() {
		BigDecimal initialBalance = CurrencyUtil.createBigDecimal(15.73D);
		BigDecimal addAmount = CurrencyUtil.createBigDecimal(28.56D);

		Wallet wallet = new Wallet();
		wallet.setUserId(WALLET_REPO_TEST_USER_ID);
		wallet.setBalance(initialBalance);

		walletRepository.save(wallet);

		txTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				walletRepository.addBalance(WALLET_REPO_TEST_USER_ID, addAmount);
			}
		});

		wallet = walletRepository.findByUserId(WALLET_REPO_TEST_USER_ID);

		assertEquals(CurrencyUtil.setScale(initialBalance.add(addAmount)), CurrencyUtil.setScale(wallet.getBalance()));
	}

	@Test
	public void testSubtractBalance() {
		BigDecimal initialBalance = CurrencyUtil.createBigDecimal(82.73D);
		BigDecimal addAmount = CurrencyUtil.createBigDecimal(19.56D);
		BigDecimal effectiveAmount = CurrencyUtil.setScale(addAmount.multiply(CurrencyUtil.createBigDecimal(-1D)));

		Wallet wallet = new Wallet();
		wallet.setUserId(WALLET_REPO_TEST_USER_ID);
		wallet.setBalance(initialBalance);

		walletRepository.save(wallet);

		txTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				walletRepository.addBalance(WALLET_REPO_TEST_USER_ID, effectiveAmount);
			}
		});

		wallet = walletRepository.findByUserId(WALLET_REPO_TEST_USER_ID);

		assertEquals(CurrencyUtil.setScale(initialBalance.subtract(addAmount)),
				CurrencyUtil.setScale(wallet.getBalance()));
	}
}
