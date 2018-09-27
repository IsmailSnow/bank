package com.userfront.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.userfront.domain.PrimaryTransaction;

public interface PrimaryTransactionDao extends JpaRepository<PrimaryTransaction, Long>{

       List<PrimaryTransaction> findAll();	
}
