package com.leovegas.wallet.core.dto.request;

public class TransactionHistoryRequest extends BaseServiceRequest {
	private Long startTime;
	private Long endTime;	
	private Integer pageSize;
	private Integer pageNumber;
	private boolean ascendingByTime;
	
	public Long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	
	public Long getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public boolean isAscendingByTime() {
		return ascendingByTime;
	}
	
	public void setAscendingByTime(boolean ascendingByTime) {
		this.ascendingByTime = ascendingByTime;
	}
		
}
