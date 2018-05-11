package com.rahul.zeppelinonlinebank.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rahul.zeppelinonlinebank.dao.PrimaryAccountDao;
import com.rahul.zeppelinonlinebank.dao.SavingsAccountDao;
import com.rahul.zeppelinonlinebank.exception.TransactionException;
import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;
import com.rahul.zeppelinonlinebank.pojo.PrimaryTransaction;
import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsTransaction;
import com.rahul.zeppelinonlinebank.pojo.User;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private static int nextAccountNumber = 1122331;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@Override
	public PrimaryAccount createPrimaryAccount() {
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAccountNumber(accountGen());

		primaryAccountDao.save(primaryAccount);

		return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
	}

	@Override
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());

		savingsAccountDao.save(savingsAccount);

		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}

	@Override
	public void deposit(String accountType, double amount, Principal principal) {
		User user = userService.findByUserName(principal.getName());

		if (accountType.equalsIgnoreCase("Primary")) {
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);

			Date date = new Date();

			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account",
					"Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount, "Between Accounts","");
			transactionService.savePrimaryDepositTransaction(primaryTransaction);

		} else if (accountType.equalsIgnoreCase("Savings")) {
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account",
					"Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount, "Between Accounts","");
			transactionService.saveSavingsDepositTransaction(savingsTransaction);
		}

	}

	@Override
	public void withdraw(String accountType, double amount, Principal principal) throws TransactionException {
		User user = userService.findByUserName(principal.getName());

		if (accountType.equalsIgnoreCase("Primary")) {
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			if (primaryAccount.getAccountBalance().doubleValue() < amount) {
				throw new TransactionException("Not Enough balance in Primary Account!!");
			} else {
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				primaryAccountDao.save(primaryAccount);

				Date date = new Date();

				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account",
						"Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount, "Between Accounts","");
				transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
			}
		} else if (accountType.equalsIgnoreCase("Savings")) {

			SavingsAccount savingsAccount = user.getSavingsAccount();
			if (savingsAccount.getAccountBalance().doubleValue() < amount) {
				throw new TransactionException("Not enough balance in Savings account!!");
			} else {
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				savingsAccountDao.save(savingsAccount);

				Date date = new Date();
				SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account",
						"Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount, "Between Accounts","");
				transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
			}
		}

	}

	private int accountGen() {
		return ++nextAccountNumber;
	}

}
