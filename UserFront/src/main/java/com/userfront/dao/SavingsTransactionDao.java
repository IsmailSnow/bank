package com.userfront.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userfront.domain.SavingsTransaction;

public interface SavingsTransactionDao extends JpaRepository<SavingsTransaction, Long> {

	List<SavingsTransaction> findAll();

}
