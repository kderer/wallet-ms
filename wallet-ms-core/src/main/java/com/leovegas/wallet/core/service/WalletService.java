package com.leovegas.wallet.core.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.leovegas.wallet.core.constant.ServiceResultEnum;
import com.leovegas.wallet.core.db.model.Wallet;
import com.leovegas.wallet.core.db.model.WalletTransaction;
import com.leovegas.wallet.core.db.repository.WalletRepository;
import com.leovegas.wallet.core.db.repository.WalletTransactionRepository;
import com.leovegas.wallet.core.dto.WalletTransactionDto;
import com.leovegas.wallet.core.dto.request.CreditRequest;
import com.leovegas.wallet.core.dto.request.QueryBalanceRequest;
import com.leovegas.wallet.core.dto.request.TransactionHistoryRequest;
import com.leovegas.wallet.core.dto.request.WithdrawRequest;
import com.leovegas.wallet.core.dto.response.CreditResponse;
import com.leovegas.wallet.core.dto.response.QueryBalanceResponse;
import com.leovegas.wallet.core.dto.response.TransactionHistoryResponse;
import com.leovegas.wallet.core.dto.response.WithdrawResponse;
import com.leovegas.wallet.core.util.CurrencyUtil;
import com.leovegas.wallet.core.util.DuplicateTransactionIdException;
import com.leovegas.wallet.core.util.InvalidAmountException;
import com.leovegas.wallet.core.util.InvalidTransactionIdException;
import com.leovegas.wallet.core.util.ObjectMapperUtil;

@Service
public class WalletService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

	private static final String COL_CREATE_TIME = "createTime";
	private static final String REQ_TYPE_CREDIT = "Credit";
	private static final String REQ_TYPE_WITHDRAW = "Withdraw";

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private WalletTransactionRepository walletTransactionRepository;
	
	@Transactional(rollbackOn = Exception.class)
	public CreditResponse credit(CreditRequest request)
			throws DuplicateTransactionIdException, InvalidAmountException, InvalidTransactionIdException {
		logger.info("Processing credit request. Transaction ID: {}, User ID: {}, Amount: {}",
				request.getTransactionId(), request.getUserId(), request.getAmount());

		validateAmount(request.getAmount(), REQ_TYPE_CREDIT);
		validateTransactionId(request.getTransactionId(), REQ_TYPE_CREDIT);
		uniqueTransactionCheck(request.getTransactionId(), REQ_TYPE_CREDIT);

		WalletTransaction transaction = new WalletTransaction();
		transaction.setAmount(request.getAmount());
		transaction.setCreateTime(Calendar.getInstance().getTimeInMillis());
		transaction.setExternalId(request.getTransactionId());
		transaction.setUserId(request.getUserId());

		Wallet wallet = walletRepository.findByUserId(request.getUserId());
		if (wallet == null) {
			logger.info("Creating wallet for {} ", request.getUserId());

			wallet = new Wallet(request.getUserId(), BigDecimal.ZERO);
			walletRepository.save(wallet);
		}

		transaction.setBalanceAfter(wallet.getBalance().add(request.getAmount()));
		walletTransactionRepository.save(transaction);

		logger.info("Transaction is saved. Transaction ID: {}, Balance Before: {}", request.getTransactionId(),
				wallet.getBalance());

		walletRepository.addBalance(request.getUserId(), request.getAmount());

		logger.info("Transaction amount is added to balance. Transaction ID: {}, Amount: {}",
				request.getTransactionId(), request.getAmount());

		return new CreditResponse(new WalletTransactionDto(transaction), request);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public WithdrawResponse withdraw(WithdrawRequest request)
			throws DuplicateTransactionIdException, InvalidAmountException, InvalidTransactionIdException {
		logger.info("Processing withdraw request. Transaction ID: {}, User ID: {}, Amount: {}",
				request.getTransactionId(), request.getUserId(), request.getAmount());

		validateAmount(request.getAmount(), REQ_TYPE_WITHDRAW);
		validateTransactionId(request.getTransactionId(), REQ_TYPE_WITHDRAW);
		uniqueTransactionCheck(request.getTransactionId(), REQ_TYPE_WITHDRAW);

		Wallet wallet = walletRepository.findByUserId(request.getUserId());
		if (wallet == null) {
			logger.warn("Wallet is not found for withdraw request. Transaction ID: {}, User ID: {}",
					request.getTransactionId(), request.getUserId());

			WithdrawResponse response = new WithdrawResponse(ServiceResultEnum.USERID_NOT_FOUND);
			response.setRequest(request);

			return response;
		}

		if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
			logger.warn(
					"Insufficient balance for withdraw request. Transaction ID: {}, User ID: {}, Amount: {}, Balance: {}",
					request.getTransactionId(), request.getUserId(), request.getAmount(), wallet.getBalance());

			WithdrawResponse response = new WithdrawResponse(ServiceResultEnum.INSUFFICIENT_BALANCE);
			response.setRequest(request);

			return response;
		}

		BigDecimal effectiveAmount = CurrencyUtil
				.setScale(request.getAmount().multiply(CurrencyUtil.createBigDecimal(-1D)));

		WalletTransaction transaction = new WalletTransaction();
		transaction.setAmount(effectiveAmount);
		transaction.setCreateTime(Calendar.getInstance().getTimeInMillis());
		transaction.setExternalId(request.getTransactionId());
		transaction.setUserId(request.getUserId());
		transaction.setBalanceAfter(wallet.getBalance().subtract(request.getAmount()));

		walletTransactionRepository.save(transaction);

		logger.info("Transaction is saved. Transaction ID: {}, Balance Before: {}", request.getTransactionId(),
				wallet.getBalance());

		walletRepository.addBalance(request.getUserId(), effectiveAmount);

		logger.info("Transaction amount is subtracted from balance. Transaction ID: {}, Amount: {}",
				request.getTransactionId(), request.getAmount());

		return new WithdrawResponse(new WalletTransactionDto(transaction), request);
	}

	public QueryBalanceResponse queryBalance(QueryBalanceRequest request) {
		logger.info("Processing balance query for User ID: {}", request.getUserId());

		Optional<Wallet> wallet = walletRepository.findById(request.getUserId());

		if (wallet.isPresent()) {
			logger.info("Balance is queried for User ID: {}", request.getUserId());

			return new QueryBalanceResponse(request.getUserId(), wallet.get().getBalance());
		}

		logger.warn("Wallet is not found for User ID {}", request.getUserId());

		QueryBalanceResponse response = new QueryBalanceResponse(ServiceResultEnum.USERID_NOT_FOUND);
		response.setUserId(request.getUserId());

		return response;
	}

	public TransactionHistoryResponse transactionHistory(TransactionHistoryRequest request) {
		logger.info("Processing transaction history query for {}", ObjectMapperUtil.stringifyObject(request));

		Direction orderbyTimeDirection = Direction.DESC;

		if (request.isAscendingByTime()) {
			orderbyTimeDirection = Direction.ASC;
		}

		Sort sort = Sort.by(new Order(orderbyTimeDirection, COL_CREATE_TIME));

		Pageable pageRequest = generatePageRequest(request.getPageSize(), request.getPageNumber(), sort);

		Long startTime = request.getStartTime();
		Long endTime = request.getEndTime();

		if (StringUtils.isEmpty(startTime)) {
			startTime = 0L;
		}

		if (StringUtils.isEmpty(endTime)) {
			endTime = Calendar.getInstance().getTimeInMillis();
		}

		List<WalletTransactionDto> transactionList = null;
		Long totalCount = 0L;
		Integer pageCount = 1;

		if (pageRequest == null) {
			transactionList = walletTransactionRepository.findByUserId(request.getUserId(), startTime, endTime, sort)
					.stream().map(transaction -> new WalletTransactionDto(transaction)).collect(Collectors.toList());

			totalCount = Long.valueOf(transactionList.size());

			logger.debug("Transaction history is queried without paging. {} results found.", totalCount);
		} else {
			Page<WalletTransaction> pageResult = walletTransactionRepository.findByUserIdPaged(request.getUserId(),
					startTime, endTime, pageRequest);

			transactionList = pageResult.getContent().stream().map(transaction -> new WalletTransactionDto(transaction))
					.collect(Collectors.toList());

			totalCount = pageResult.getTotalElements();
			pageCount = pageResult.getTotalPages();
		}

		logger.info("Transaction history is queried for {}", ObjectMapperUtil.stringifyObject(request));

		return new TransactionHistoryResponse(totalCount, pageCount, transactionList, request);
	}

	private void uniqueTransactionCheck(String transactionId, String requestType)
			throws DuplicateTransactionIdException {
		if (walletTransactionRepository.findByExternalId(transactionId) != null) {
			logger.error("{} request is done with an existing transaction ID {}.", requestType, transactionId);

			throw new DuplicateTransactionIdException(transactionId);
		}
	}

	private void validateAmount(BigDecimal amount, String requestType) throws InvalidAmountException {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) < 1) {
			logger.error("{} request is done with an invalid amount {}.", requestType, amount);

			throw new InvalidAmountException();
		}
	}

	private void validateTransactionId(String transactionId, String requestType) throws InvalidTransactionIdException {
		if (StringUtils.isEmpty(transactionId)) {
			logger.error("{} request is done with an empty transaction ID.", requestType);

			throw new InvalidTransactionIdException();
		}
	}

}
