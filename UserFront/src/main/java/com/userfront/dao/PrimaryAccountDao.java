package com.userfront.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userfront.domain.PrimaryAccount;

public interface PrimaryAccountDao extends JpaRepository<PrimaryAccount, Long> {

	Optional<PrimaryAccount> findByAccountNumber(int accountNumber);
}
