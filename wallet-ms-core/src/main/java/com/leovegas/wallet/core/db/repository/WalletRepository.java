package com.leovegas.wallet.core.db.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.leovegas.wallet.core.db.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, String> {
	
	Wallet findByUserId(String userId);
	
	@Modifying
	@Query("Update Wallet w set w.balance = w.balance + :amount where w.userId = :userId")
	int addBalance(String userId, BigDecimal amount);

}
