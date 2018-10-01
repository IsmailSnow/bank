package com.userfront.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.PrimaryTransactionDao;
import com.userfront.dao.RecipientDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.dao.SavingsTransactionDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
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

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private RecipientDao recipientDao;

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

	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
		if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			Date date = new Date();
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
					"Between account transfer from " + transferFrom + " to " + transferTo, "Account", "Finished",
					Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
			primaryAccountDao.save(primaryAccount);
			savingsAccountDao.save(savingsAccount);
			primaryTransactionDao.save(primaryTransaction);
		} else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			Date date = new Date();
			SavingsTransaction savingsTransaction = new SavingsTransaction(date,
					"Between account transfer from " + transferFrom + " to " + transferTo, "Transfer", "Finished",
					Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
			primaryAccountDao.save(primaryAccount);
			savingsAccountDao.save(savingsAccount);
			savingsTransactionDao.save(savingsTransaction);
		} else {
			throw new Exception("Invalid Transfer");
		}
	}

	@Override
	public List<Recipient> findRecipientList(Principal principal) {
		return recipientDao.findAllrecipientByUsername(principal.getName());
	}

	@Override
	public void saveRecipient(Recipient recipient) {
		recipientDao.save(recipient);
	}

	@Override
	public Recipient findRecipientByName(String recipientName) {
		return recipientDao.findByName(recipientName);
	}

	@Override
	public void deleteRecipientByName(String recipientName) {

		recipientDao.deleteByName(recipientName);
	}

	@Override
	public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAcount) {

		if (accountType.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			Date date = new Date();
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
					"Transfer to recipient " + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount),
					primaryAccount.getAccountBalance(), primaryAccount);
			primaryTransactionDao.save(primaryTransaction);
		} else if (accountType.equalsIgnoreCase("Savings")) {
			savingsAcount.setAccountBalance(savingsAcount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAcount);

			Date date = new Date();

			SavingsTransaction savingsTransaction = new SavingsTransaction(date,
					"Transfer to recipient " + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount),
					savingsAcount.getAccountBalance(), savingsAcount);
			savingsTransactionDao.save(savingsTransaction);
		}

	}

}
