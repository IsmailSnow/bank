package com.userfront.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private static int nextAccountNumber = 11223345;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Override
	public PrimaryAccount createPrimaryAccount() {
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAccountNumber(accountGen());
		primaryAccountDao.save(primaryAccount);
		// the return here will be use when we save this information into the user
		// entity
		return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber()).orElse(null);
	}

	@Override
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber()).orElse(null);
	}

	private int accountGen() {
		return ++nextAccountNumber;
	}

}
