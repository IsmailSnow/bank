package com.userfront.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryTransactionDao;
import com.userfront.dao.SavingsTransactionDao;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PrimaryTransactionDao primaryTransactionDao;

	@Autowired
	private SavingsTransactionDao savingsTransactionDao;

	@Override
	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		Optional<User> user = userService.findByUsername(username);
		if (user.isPresent()) {
			List<PrimaryTransaction> primaryTransactionList = user.get().getPrimaryAccount()
					.getPrimaryTransactionList();
			return primaryTransactionList;
		} else {
			logger.warn("user not found , operation failed");
			return new ArrayList<>();
		}
	}

	@Override
	public List<SavingsTransaction> findSavingsTransactionList(String username) {

		Optional<User> user = userService.findByUsername(username);
		if (user.isPresent()) {
			List<SavingsTransaction> primaryTransactionList = user.get().getSavingsAcount().getSavingsTransactionList();
			return primaryTransactionList;
		} else {
			logger.warn("user not found , operation failed");
			return new ArrayList<>();
		}
	}

	@Override
	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}

	@Override
	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	@Override
	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}

	@Override
	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

}
