package com.leovegas.wallet.rest.test.controller;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.wallet.core.dto.WalletTransactionDto;
import com.leovegas.wallet.core.dto.request.CreditRequest;
import com.leovegas.wallet.core.dto.request.TransactionHistoryRequest;
import com.leovegas.wallet.core.dto.request.WithdrawRequest;
import com.leovegas.wallet.core.dto.response.CreditResponse;
import com.leovegas.wallet.core.dto.response.QueryBalanceResponse;
import com.leovegas.wallet.core.dto.response.TransactionHistoryResponse;
import com.leovegas.wallet.core.dto.response.WithdrawResponse;
import com.leovegas.wallet.core.enumaration.ServiceResultEnum;
import com.leovegas.wallet.core.service.WalletService;
import com.leovegas.wallet.rest.test.BaseTest;

public class WalletControllerTest extends BaseTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private WalletService service;

	private ObjectMapper objMapper = new ObjectMapper();

	@Test
	public void testCredit() throws Exception {
		CreditRequest request = new CreditRequest();
		WalletTransactionDto wtDto = new WalletTransactionDto();
		CreditResponse response = new CreditResponse(wtDto, request);

		BDDMockito.when(service.credit(Mockito.any())).thenReturn(response);

		mvc.perform(MockMvcRequestBuilders.post("/wallet/credit").contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.resultCode",
						CoreMatchers.is(ServiceResultEnum.SUCCESS.getCode())));
	}

	@Test
	public void testWithdrawInsufficentBalance() throws Exception {
		WithdrawResponse response = new WithdrawResponse(ServiceResultEnum.INSUFFICIENT_BALANCE);

		BDDMockito.when(service.withdraw(Mockito.any())).thenReturn(response);

		mvc.perform(MockMvcRequestBuilders.post("/wallet/withdraw").contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(new WithdrawRequest())))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.resultCode", CoreMatchers.is(ServiceResultEnum.INSUFFICIENT_BALANCE.getCode())));
	}

	@Test
	public void testTransactionHistory() throws Exception {
		List<WalletTransactionDto> txList = Arrays.asList(new WalletTransactionDto(), new WalletTransactionDto());

		TransactionHistoryResponse response = new TransactionHistoryResponse(2L, 1, txList,
				new TransactionHistoryRequest());

		BDDMockito.when(service.transactionHistory(Mockito.any())).thenReturn(response);

		mvc.perform(MockMvcRequestBuilders.get("/wallet/history").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.resultCode",
						CoreMatchers.is(ServiceResultEnum.SUCCESS.getCode())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.transactionList", Matchers.hasSize(2)));
	}

	@Test
	public void testQueryBalanceUserNotFound() throws Exception {
		BDDMockito.given(service.queryBalance(Mockito.any()))
				.willReturn(new QueryBalanceResponse(ServiceResultEnum.USERID_NOT_FOUND));

		mvc.perform(MockMvcRequestBuilders.get("/wallet/balance").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.resultCode", CoreMatchers.is(ServiceResultEnum.USERID_NOT_FOUND.getCode())));
	}
	
	
	@Test
	public void testUnhandledException() throws Exception {
		BDDMockito.given(service.credit(Mockito.any())).willThrow(new NumberFormatException());

		mvc.perform(MockMvcRequestBuilders.post("/wallet/credit").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.resultCode", CoreMatchers.is(ServiceResultEnum.UNEXPECTED_ERROR.getCode())));
	}

}
