package com.leovegas.wallet.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leovegas.wallet.core.dto.request.CreditRequest;
import com.leovegas.wallet.core.dto.request.QueryBalanceRequest;
import com.leovegas.wallet.core.dto.request.TransactionHistoryRequest;
import com.leovegas.wallet.core.dto.request.WithdrawRequest;
import com.leovegas.wallet.core.dto.response.CreditResponse;
import com.leovegas.wallet.core.dto.response.QueryBalanceResponse;
import com.leovegas.wallet.core.dto.response.TransactionHistoryResponse;
import com.leovegas.wallet.core.dto.response.WithdrawResponse;
import com.leovegas.wallet.core.exception.DuplicateTransactionIdException;
import com.leovegas.wallet.core.exception.InvalidAmountException;
import com.leovegas.wallet.core.exception.InvalidTransactionIdException;
import com.leovegas.wallet.core.service.WalletService;
import com.leovegas.wallet.core.util.ObjectMapperUtil;
import com.leovegas.wallet.rest.util.ServiceResponseHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/wallet")
@Api(tags = "Wallet Management APIs")
public class WalletController {
	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	@Autowired
	private WalletService walletService;

	@ApiOperation("Credit per player")
	@RequestMapping(value = "/credit", method = RequestMethod.POST)
	public ResponseEntity<CreditResponse> credit(@RequestBody CreditRequest request)
			throws DuplicateTransactionIdException, InvalidAmountException, InvalidTransactionIdException {
		logger.debug("Credit request is received for {}", ObjectMapperUtil.stringifyObject(request));

		return ServiceResponseHandler.handleResponse(walletService.credit(request));
	}

	@ApiOperation("Withdrawal per player")
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public ResponseEntity<WithdrawResponse> withdraw(@RequestBody WithdrawRequest request)
			throws DuplicateTransactionIdException, InvalidAmountException, InvalidTransactionIdException {
		logger.debug("Withdraw request is received for {}", ObjectMapperUtil.stringifyObject(request));

		return ServiceResponseHandler.handleResponse(walletService.withdraw(request));
	}

	@ApiOperation("Current balance per player")
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public ResponseEntity<QueryBalanceResponse> balance() {
		QueryBalanceRequest request = new QueryBalanceRequest();

		logger.debug("Balance query request is received for {}", ObjectMapperUtil.stringifyObject(request));

		return ServiceResponseHandler.handleResponse(walletService.queryBalance(request));
	}

	@ApiOperation("Transaction history per player")
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public ResponseEntity<TransactionHistoryResponse> history(
			@ApiParam(name = "startTime", value = "transaction time filter start value in milliseconds", example = "0") @RequestParam(value = "startTime", required = false) Long startTime,
			@ApiParam(name = "endTime", value = "transaction time filter end value in milliseconds", example = "9999999999999") @RequestParam(value = "endTime", required = false) Long endTime,
			@ApiParam(name = "pageSize", value = "-1 disables paging", example = "25") @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "ascendingByTime", required = false) Boolean ascByTime) {

		TransactionHistoryRequest request = new TransactionHistoryRequest();
		request.setStartTime(startTime);
		request.setEndTime(endTime);
		request.setPageSize(pageSize);
		request.setPageNumber(pageNumber);
		request.setAscendingByTime(ascByTime != null ? ascByTime.booleanValue() : false);

		logger.debug("Transaction history query request is received for {}", ObjectMapperUtil.stringifyObject(request));

		return ServiceResponseHandler.handleResponse(walletService.transactionHistory(request));
	}

}
