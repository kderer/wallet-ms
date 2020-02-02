package com.leovegas.wallet.core.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.leovegas.wallet.core.dto.WalletTransactionDto;
import com.leovegas.wallet.core.dto.request.CreditRequest;
import com.leovegas.wallet.core.dto.request.QueryBalanceRequest;
import com.leovegas.wallet.core.dto.request.TransactionHistoryRequest;
import com.leovegas.wallet.core.dto.request.WithdrawRequest;
import com.leovegas.wallet.core.dto.response.CreditResponse;
import com.leovegas.wallet.core.dto.response.QueryBalanceResponse;
import com.leovegas.wallet.core.dto.response.TransactionHistoryResponse;
import com.leovegas.wallet.core.dto.response.WithdrawResponse;
import com.leovegas.wallet.core.enumaration.ServiceResultEnum;
import com.leovegas.wallet.core.exception.DuplicateTransactionIdException;
import com.leovegas.wallet.core.exception.InvalidAmountException;
import com.leovegas.wallet.core.exception.InvalidTransactionIdException;
import com.leovegas.wallet.core.service.WalletService;
import com.leovegas.wallet.core.test.BaseTest;
import com.leovegas.wallet.core.util.CurrencyUtil;
import com.leovegas.wallet.core.util.ServiceRequestContext;

@TestMethodOrder(OrderAnnotation.class)
public class WalletServiceTest extends BaseTest {

	@Autowired
	private WalletService walletService;

	@Test
	@Order(1)
	public void testCredit()
			throws DuplicateTransactionIdException, InvalidAmountException, InvalidTransactionIdException {
		CreditRequest request1 = new CreditRequest();
		request1.setTransactionId("test_01" + Calendar.getInstance().getTimeInMillis());
		request1.setAmount(new BigDecimal(35.78));

		CreditResponse response = walletService.credit(request1);

		CreditRequest request2 = new CreditRequest();
		request2.setTransactionId("test_02" + Calendar.getInstance().getTimeInMillis());
		request2.setAmount(new BigDecimal(14.02));

		response = walletService.credit(request2);

		QueryBalanceRequest queryBalanceRequest = new QueryBalanceRequest();

		QueryBalanceResponse queryBalanceResponse = walletService.queryBalance(queryBalanceRequest);

		assertEquals(response.getTransaction().getBalanceAfter(), queryBalanceResponse.getBalance());
	}

	@Test
	@Order(2)
	public void testWithdrawInsufficientBalance()
			throws DuplicateTransactionIdException, InvalidAmountException, InvalidTransactionIdException {
		WithdrawRequest request = new WithdrawRequest();
		request.setAmount(new BigDecimal(1000000L));
		request.setTransactionId("123123123123");

		WithdrawResponse response = walletService.withdraw(request);

		assertEquals(response.getResultCode(), ServiceResultEnum.INSUFFICIENT_BALANCE.getCode());
	}

	@Test
	@Order(3)
	public void testWithdraw()
			throws DuplicateTransactionIdException, InvalidAmountException, InvalidTransactionIdException {
		BigDecimal amount = new BigDecimal(3.45);

		WithdrawRequest request = new WithdrawRequest();
		request.setAmount(amount);
		request.setTransactionId("asdqwezxc1");

		WithdrawResponse response = walletService.withdraw(request);

		assertEquals(CurrencyUtil.setScale(amount.multiply(new BigDecimal(-1))), response.getTransaction().getAmount());
	}

	@Test
	@Order(4)
	public void testQueryBalanceUserNotFound() {
		setServiceRequestContextUserId("adasd");

		QueryBalanceRequest request = new QueryBalanceRequest();

		QueryBalanceResponse response = walletService.queryBalance(request);

		assertEquals(response.getResultMessage(), ServiceRequestContext.getCurrentInstance()
				.resolveI18nMessage(ServiceResultEnum.USERID_NOT_FOUND.getI18nKey()));
	}

	@Test
	@Order(5)
	public void testQueryBalance() {
		BigDecimal balance = new BigDecimal(0);

		QueryBalanceResponse response = walletService.queryBalance(new QueryBalanceRequest());

		TransactionHistoryRequest historyRequest = new TransactionHistoryRequest();
		historyRequest.setPageSize(-1);

		TransactionHistoryResponse historyResponse = walletService.transactionHistory(historyRequest);
		for (WalletTransactionDto transaction : historyResponse.getTransactionList()) {
			balance = CurrencyUtil.setScale(balance.add(transaction.getAmount()));
		}

		assertEquals(response.getBalance(), balance);
	}

	@Test
	@Order(6)
	public void testQueryTransactionHistory() {
		TransactionHistoryRequest request = new TransactionHistoryRequest();
		request.setPageSize(-1);

		TransactionHistoryResponse response = walletService.transactionHistory(request);

		response.getTransactionList().stream().forEach(transaction -> System.out.println(transaction.getCreateTime()));

		assertEquals(response.getTransactionList().size(), response.getTotalCount());
	}

	@Test
	@Order(7)
	public void testQueryTransactionHistoryPaged() {
		TransactionHistoryRequest request = new TransactionHistoryRequest();
		request.setPageSize(2);
		request.setPageNumber(0);
		request.setStartTime(Calendar.getInstance().getTimeInMillis() - 1000 * 60 * 60 * 2);
		request.setAscendingByTime(true);

		TransactionHistoryResponse response = walletService.transactionHistory(request);

		response.getTransactionList().stream().forEach(transaction -> System.out.println(transaction.getCreateTime()));

		assertEquals(response.getPageCount(), (int) (response.getTotalCount() / 2L + 1L));
	}

}
