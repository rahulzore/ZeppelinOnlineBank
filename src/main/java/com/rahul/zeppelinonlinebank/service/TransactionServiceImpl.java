package com.rahul.zeppelinonlinebank.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rahul.zeppelinonlinebank.dao.ExternalRecipientDao;
import com.rahul.zeppelinonlinebank.dao.InternalRecipientDao;
import com.rahul.zeppelinonlinebank.dao.PrimaryAccountDao;
import com.rahul.zeppelinonlinebank.dao.PrimaryTransactionDao;
import com.rahul.zeppelinonlinebank.dao.SavingsAccountDao;
import com.rahul.zeppelinonlinebank.dao.SavingsTransactionDao;
import com.rahul.zeppelinonlinebank.exception.AccountNotFoundException;
import com.rahul.zeppelinonlinebank.exception.TransactionException;
import com.rahul.zeppelinonlinebank.pojo.ExternalRecipient;
import com.rahul.zeppelinonlinebank.pojo.InternalRecipient;
import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;
import com.rahul.zeppelinonlinebank.pojo.PrimaryTransaction;
import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsTransaction;
import com.rahul.zeppelinonlinebank.pojo.User;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserService userService;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsTransactionDao savingsTransactionDao;

	@Autowired
	private PrimaryTransactionDao primaryTransactionDao;

	@Autowired
	private ExternalRecipientDao externalRecipientDao;
	
	@Autowired
	private InternalRecipientDao internalRecipientDao;

	@Override
	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		User user = userService.findByUserName(username);
		List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();
		return primaryTransactionList;
	}

	@Override
	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUserName(username);
		List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();
		return savingsTransactionList;
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

	@Override
	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws TransactionException {

		if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
			if (primaryAccount.getAccountBalance().doubleValue() < (Double.parseDouble(amount))) {
				throw new TransactionException("Not enough Balance in Primary Account!!");
			} else {
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
				primaryAccountDao.save(primaryAccount);
				savingsAccountDao.save(savingsAccount);

				Date date = new Date();

				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
						"Between accounts, Transfer From: " + transferFrom + " Transfer To: " + transferTo, "Account",
						"Finished", Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount, "Between Accounts Transfer","");
				primaryTransactionDao.save(primaryTransaction);
			}

		} else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
			if (savingsAccount.getAccountBalance().doubleValue() < (Double.parseDouble(amount))) {
				throw new TransactionException("Not enough Balance in Savings Account!!");
			} else {
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				primaryAccountDao.save(primaryAccount);
				savingsAccountDao.save(savingsAccount);

				Date date = new Date();

				SavingsTransaction savingsTransaction = new SavingsTransaction(date,
						"Between accounts, Tranfer From: " + transferFrom + " Transfer To: " + transferTo, "Account",
						"Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount, "Between Accounts Transfer","");
				savingsTransactionDao.save(savingsTransaction);
			}
		}

	}



	@Override
	public void toSomeoneElseTransfer(ExternalRecipient recipient, String accountType, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws TransactionException {
		if (accountType.equalsIgnoreCase("Primary")) {
			// primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new
			// BigDecimal(amount)));
			// primaryAccountDao.save(primaryAccount);
			if (primaryAccount.getAccountBalance().doubleValue() < (Double.parseDouble(amount))) {
				throw new TransactionException("Not enough Balance in Primary account!!");
			} else {

				Date date = new Date();

				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
						"Transfer to recipient " + recipient.getName(), "Transfer", "Pending",
						Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount, "External", recipient.getAccountNumber());
				primaryTransactionDao.save(primaryTransaction);
			}
		} else if (accountType.equalsIgnoreCase("Savings")) {
			// savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new
			// BigDecimal(amount)));
			// savingsAccountDao.save(savingsAccount);
			if (savingsAccount.getAccountBalance().doubleValue() < (Double.parseDouble(amount))) {
				throw new TransactionException("Not enough Balance in Savings account!!");
			} else {

				Date date = new Date();

				SavingsTransaction savingsTransaction = new SavingsTransaction(date,
						"Transfer to recipient " + recipient.getName(), "Transfer", "Pending",
						Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount, "External", recipient.getAccountNumber());
				savingsTransactionDao.save(savingsTransaction);
			}
		}

	}

	@Override
	public void approveToSomeoneElseTransfer(String description, String accountType, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount, long ID) {
		if (accountType.equalsIgnoreCase("Primary")) {

			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);

			Date date = new Date();

			// PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
			// description, "Transfer", "Finished", Double.parseDouble(amount),
			// primaryAccount.getAccountBalance(), primaryAccount);
			PrimaryTransaction primaryTransaction = primaryTransactionDao.findTransaction(ID);
			primaryTransaction.setStatus("Finished");
			primaryTransaction.setDate(date);
			primaryTransaction.setAvailableBalance(primaryAccount.getAccountBalance());
			primaryTransactionDao.save(primaryTransaction);
		} else if (accountType.equalsIgnoreCase("Savings")) {
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			// SavingsTransaction savingsTransaction = new SavingsTransaction(date,
			// description, "Transfer", "Finished", Double.parseDouble(amount),
			// savingsAccount.getAccountBalance(), savingsAccount);
			SavingsTransaction savingsTransaction = savingsTransactionDao.findTransaction(ID);
			savingsTransaction.setStatus("Finished");
			savingsTransaction.setDate(date);
			savingsTransactionDao.save(savingsTransaction);
		}

	}

	@Override
	public List<ExternalRecipient> findExternalRecipientList(Principal principal) {
		String username = principal.getName();
		List<ExternalRecipient> recipientList = externalRecipientDao.findall().stream()
				.filter(recipient -> username.equals(recipient.getUser().getUserName())).collect(Collectors.toList());
		return recipientList;
	}

	@Override
	public ExternalRecipient saveExternalRecipient(ExternalRecipient recipient) {
		externalRecipientDao.save(recipient);
		return recipient;
	}

	@Override
	public ExternalRecipient findExternalRecipientByName(String recipientName) {

		return externalRecipientDao.findByName(recipientName);
	}

	@Override
	public void deleteExternalRecipientByName(String recipientName) {
		externalRecipientDao.deleteByName(recipientName);

	}

	@Override
	public List<InternalRecipient> findInternalRecipientList(Principal principal) {
		String username = principal.getName();
		List<InternalRecipient> recipientList = internalRecipientDao.findall().stream()
				.filter(recipient -> username.equals(recipient.getUser().getUserName())).collect(Collectors.toList());
		return recipientList;
	}

	@Override
	public InternalRecipient saveInternalRecipient(InternalRecipient recipient) {
		internalRecipientDao.save(recipient);
		return recipient;
	}

	@Override
	public InternalRecipient findInternalRecipientByName(String recipientName) {
		
		return internalRecipientDao.findByName(recipientName);
	}
	
	@Override
	public void findInternalRecipientByAccountNumber(String recipientName, String accountNumber) throws AccountNotFoundException{
		//InternalRecipient recipient = null;
		PrimaryAccount account = null;
		
		
		
		account = primaryAccountDao.findByAccountNumber(Integer.parseInt(accountNumber));
		if(account==null) {
			throw new AccountNotFoundException("Account not found. Please enter the primary account number of the recipient");
		}
		
		//recipient=internalRecipientDao.findByName(recipientName);
		
		
		
		//return recipient;
	}

	@Override
	public void deleteInternalRecipientByName(String recipientName) {
		internalRecipientDao.deleteByName(recipientName);
		
	}

	@Override
	public void toSomeoneElseTransferWithin(InternalRecipient recipient, String accountType, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws TransactionException {
		
		if (accountType.equalsIgnoreCase("Primary")) {
			
			if (primaryAccount.getAccountBalance().doubleValue() < (Double.parseDouble(amount))) {
				throw new TransactionException("Not enough Balance in Primary account!!");
			} else {

				Date date = new Date();

				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
						"Transfer to recipient " + recipient.getName(), "Transfer", "Pending",
						Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount, "Internal", recipient.getAccountNumber());
				primaryTransactionDao.save(primaryTransaction);
			}
		} else if (accountType.equalsIgnoreCase("Savings")) {
			
			if (savingsAccount.getAccountBalance().doubleValue() < (Double.parseDouble(amount))) {
				throw new TransactionException("Not enough Balance in Savings account!!");
			} else {

				Date date = new Date();

				SavingsTransaction savingsTransaction = new SavingsTransaction(date,
						"Transfer to recipient " + recipient.getName(), "Transfer", "Pending",
						Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount, "Internal", recipient.getAccountNumber());
				savingsTransactionDao.save(savingsTransaction);
			}
		}
		
	}

	@Override
	public void approveToSomeoneElseTransferWithin(PrimaryAccount receiversPrimaryAccount, String description,
			String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount, long ID)
			throws TransactionException {
		
		if (accountType.equalsIgnoreCase("Primary")) {

			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			receiversPrimaryAccount.setAccountBalance(receiversPrimaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			
			primaryAccountDao.save(primaryAccount);
			primaryAccountDao.save(receiversPrimaryAccount);

			Date date = new Date();

			
			PrimaryTransaction primaryTransaction = primaryTransactionDao.findTransaction(ID);
			primaryTransaction.setStatus("Finished");
			primaryTransaction.setDate(date);
			primaryTransaction.setAvailableBalance(primaryAccount.getAccountBalance());
			primaryTransactionDao.save(primaryTransaction);
		} else if (accountType.equalsIgnoreCase("Savings")) {
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			receiversPrimaryAccount.setAccountBalance(receiversPrimaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			primaryAccountDao.save(receiversPrimaryAccount);

			Date date = new Date();

		
			SavingsTransaction savingsTransaction = savingsTransactionDao.findTransaction(ID);
			savingsTransaction.setStatus("Finished");
			savingsTransaction.setDate(date);
			savingsTransactionDao.save(savingsTransaction);
		}

	
		
	}

	@Override
	public PrimaryAccount findPrimaryAccountByAccountNumber(String accountNumber) {
		PrimaryAccount primaryAccount = primaryAccountDao.findByAccountNumber(Integer.parseInt(accountNumber));
		return primaryAccount;
	}

	




}
