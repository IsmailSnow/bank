package com.userfront.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {

	private static final String SAVINGS = "Savings";

	private static final String PRIMARY = "Primary";

	private final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	private static int nextAccountNumber = 11223345;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private UserService userService;

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
		savingsAccountDao.save(savingsAccount);
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber()).orElse(null);
	}

	@Override
	public void deposit(String accountType, double amount, Principal principal) {

		userService.findByUsername(principal.getName()).ifPresent(user -> {
			logger.debug("user {} is found , start operation saving", user.getUsername());
			if (accountType.equalsIgnoreCase(PRIMARY)) {
				PrimaryAccount primaryAccount = user.getPrimaryAccount();
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
				primaryAccountDao.save(primaryAccount);
				Date date = new Date();
				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account",
						"Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);

			} else if (accountType.equalsIgnoreCase(SAVINGS)) {
				SavingsAccount savingsAccount = user.getSavingsAcount();
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
				savingsAccountDao.save(savingsAccount);
				Date date = new Date();
				SavingsTransaction savingsTransation = new SavingsTransaction(date, "Deposit to Savings Account",
						"Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
			}

		});

	}

	private int accountGen() {
		return ++nextAccountNumber;
	}

}
