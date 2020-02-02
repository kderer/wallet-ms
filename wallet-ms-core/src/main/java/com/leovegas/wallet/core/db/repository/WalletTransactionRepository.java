package com.leovegas.wallet.core.db.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leovegas.wallet.core.db.model.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
	
	@Query("SELECT t FROM WalletTransaction t "
			+ "WHERE t.userId = :userId and t.createTime >= :startTime and t.createTime <= :endTime")
	Page<WalletTransaction> findByUserIdPaged(String userId, Long startTime, Long endTime, Pageable pageRequest);
	
	@Query("SELECT t FROM WalletTransaction t "
			+ "WHERE t.userId = :userId and t.createTime >= :startTime and t.createTime <= :endTime")
	List<WalletTransaction> findByUserId(String userId, Long startTime, Long endTime, Sort sort);
	
	WalletTransaction findByExternalId(String externalId);

}
