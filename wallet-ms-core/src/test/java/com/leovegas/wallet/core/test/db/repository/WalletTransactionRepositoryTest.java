package com.leovegas.wallet.core.test.db.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.Assert;

import com.leovegas.wallet.core.db.model.WalletTransaction;
import com.leovegas.wallet.core.db.repository.WalletTransactionRepository;
import com.leovegas.wallet.core.test.BaseTest;
import com.leovegas.wallet.core.util.CurrencyUtil;

public class WalletTransactionRepositoryTest extends BaseTest {
	private static final String WALLET_TX_REPO_TEST_USER_ID = "wallet_tx_repo_test_user";

	@Autowired
	private WalletTransactionRepository walletTransactionRepository;

	@Test
	public void testSaveWalletTransaction() {
		WalletTransaction transaction = new WalletTransaction();
		transaction.setAmount(CurrencyUtil.createBigDecimal(85.73));
		transaction.setBalanceAfter(new BigDecimal(85.73));
		transaction.setCreateTime(Calendar.getInstance().getTimeInMillis());
		transaction.setExternalId("test1_" + Calendar.getInstance().getTimeInMillis());
		transaction.setUserId(WALLET_TX_REPO_TEST_USER_ID);

		walletTransactionRepository.save(transaction);

		Assert.notNull(transaction.getId(), "Id shouldn't be null.");
	}

	@Test
	public void testFindByTransactionId() {
		String extenalId = "test1_" + Calendar.getInstance().getTimeInMillis();

		WalletTransaction transaction = new WalletTransaction();
		transaction.setAmount(CurrencyUtil.createBigDecimal(85.73));
		transaction.setBalanceAfter(new BigDecimal(85.73));
		transaction.setCreateTime(Calendar.getInstance().getTimeInMillis());
		transaction.setExternalId(extenalId);
		transaction.setUserId(WALLET_TX_REPO_TEST_USER_ID);

		walletTransactionRepository.save(transaction);

		WalletTransaction found = walletTransactionRepository.findByExternalId(extenalId);

		assertNotNull(found, "Shouldn't be null.");
	}

	@Test
	public void testQueryTransactionHistoryPaged() {
		for (int i = 0; i < 10; i++) {
			WalletTransaction transaction = new WalletTransaction();
			transaction.setAmount(CurrencyUtil.createBigDecimal(85.73));
			transaction.setBalanceAfter(new BigDecimal(85.73));
			transaction.setCreateTime(Calendar.getInstance().getTimeInMillis());
			transaction.setExternalId("wtx_paging_test_" + i + "_" + Calendar.getInstance().getTimeInMillis());
			transaction.setUserId(WALLET_TX_REPO_TEST_USER_ID + "_paging");

			walletTransactionRepository.save(transaction);
		}

		Sort sort = Sort.by(Arrays.asList(new Order(Direction.DESC, "createTime")));
		Pageable pageRequest = PageRequest.of(0, 4, sort);

		Page<WalletTransaction> resultPage = walletTransactionRepository.findByUserIdPaged(WALLET_TX_REPO_TEST_USER_ID + "_paging", 0L,
				Calendar.getInstance().getTimeInMillis(), pageRequest);

		resultPage.stream().forEach(transaction -> System.out.println(transaction.getCreateTime()));

		assertNotEquals(0, resultPage.getNumberOfElements());
		assertEquals(3, resultPage.getTotalPages());
	}

	@Test
	public void testQueryTransactionHistory() {
		for (int i = 0; i < 10; i++) {
			WalletTransaction transaction = new WalletTransaction();
			transaction.setAmount(CurrencyUtil.createBigDecimal(85.73));
			transaction.setBalanceAfter(new BigDecimal(85.73));
			transaction.setCreateTime(Calendar.getInstance().getTimeInMillis());
			transaction.setExternalId("wtx_history_test_" + i + "_" + Calendar.getInstance().getTimeInMillis());
			transaction.setUserId(WALLET_TX_REPO_TEST_USER_ID + "_history");

			walletTransactionRepository.save(transaction);
		}

		Sort sort = Sort.by(Arrays.asList(new Order(Direction.ASC, "createTime")));

		List<WalletTransaction> resultList = walletTransactionRepository.findByUserId(WALLET_TX_REPO_TEST_USER_ID + "_history", 0L,
				Calendar.getInstance().getTimeInMillis(), sort);

		resultList.stream().forEach(transaction -> System.out.println(transaction.getCreateTime()));

		assertEquals(10, resultList.size());
	}

}
