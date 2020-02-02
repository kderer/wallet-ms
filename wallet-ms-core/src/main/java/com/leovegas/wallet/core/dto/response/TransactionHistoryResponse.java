package com.leovegas.wallet.core.dto.response;

import java.util.List;

import com.leovegas.wallet.core.dto.WalletTransactionDto;
import com.leovegas.wallet.core.dto.request.TransactionHistoryRequest;

public class TransactionHistoryResponse extends BaseServiceResponse {
	private Long totalCount;
	private Integer pageCount;
	private List<WalletTransactionDto> transactionList;
	private TransactionHistoryRequest request;	
	
	public TransactionHistoryResponse(Long totalCount, Integer pageCount, List<WalletTransactionDto> transactionList,
			TransactionHistoryRequest request) {
		this.totalCount = totalCount;
		this.pageCount = pageCount;
		this.transactionList = transactionList;
		this.request = request;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<WalletTransactionDto> getTransactionList() {
		return transactionList;
	}
	
	public void setTransactionList(List<WalletTransactionDto> transactionList) {
		this.transactionList = transactionList;
	}
	
	public TransactionHistoryRequest getRequest() {
		return request;
	}
	
	public void setRequest(TransactionHistoryRequest request) {
		this.request = request;
	}

}
