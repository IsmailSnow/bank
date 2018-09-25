package com.userfront.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userfront.domain.SavingsAccount;

public interface SavingsAccountDao extends JpaRepository<SavingsAccount, Long> {

	Optional<SavingsAccount> findByAccountNumber(int accountNumber);
}
