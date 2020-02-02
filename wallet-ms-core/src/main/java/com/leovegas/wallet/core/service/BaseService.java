package com.leovegas.wallet.core.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class BaseService {

	protected Pageable generatePageRequest(Integer pageSize, Integer pageNumber, Sort sort) {
		if (pageSize != null && pageSize == -1) {
			return null;
		}
		
		if (pageSize == null || pageSize < 1) {
			pageSize = 25;
		}
		
		if (pageNumber == null || pageNumber < 0) {
			pageNumber = 0;
		}
		
		return PageRequest.of(pageNumber, pageSize, sort);
	}
}
