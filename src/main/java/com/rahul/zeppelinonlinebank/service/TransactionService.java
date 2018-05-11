package com.rahul.zeppelinonlinebank.service;

import java.security.Principal;
import java.util.List;

import com.rahul.zeppelinonlinebank.exception.AccountNotFoundException;
import com.rahul.zeppelinonlinebank.exception.TransactionException;
import com.rahul.zeppelinonlinebank.pojo.ExternalRecipient;
import com.rahul.zeppelinonlinebank.pojo.InternalRecipient;
import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;
import com.rahul.zeppelinonlinebank.pojo.PrimaryTransaction;
import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsTransaction;
import com.rahul.zeppelinonlinebank.pojo.User;



public interface TransactionService {

	List<PrimaryTransaction> findPrimaryTransactionList(String username);

    List<SavingsTransaction> findSavingsTransactionList(String username);

    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);
    
    PrimaryAccount findPrimaryAccountByAccountNumber(String accountNumber);

    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
    
    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);
    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
    
    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws TransactionException;
    
    List<ExternalRecipient> findExternalRecipientList(Principal principal);

    ExternalRecipient saveExternalRecipient(ExternalRecipient recipient);

    ExternalRecipient findExternalRecipientByName(String recipientName);

    void deleteExternalRecipientByName(String recipientName);
    
    
    List<InternalRecipient> findInternalRecipientList(Principal principal);

    InternalRecipient saveInternalRecipient(InternalRecipient recipient);

    InternalRecipient findInternalRecipientByName(String recipientName);
    
    void findInternalRecipientByAccountNumber(String recipientName, String accountNumber) throws AccountNotFoundException;; 

    void deleteInternalRecipientByName(String recipientName);
    
    
    void toSomeoneElseTransferWithin(InternalRecipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws TransactionException;
    void approveToSomeoneElseTransferWithin(PrimaryAccount receiversPrimaryAccount, String description, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount, long ID) throws TransactionException;
    
    void toSomeoneElseTransfer(ExternalRecipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws TransactionException;
    void approveToSomeoneElseTransfer(String description, String accountType, String amount,PrimaryAccount primaryAccount, SavingsAccount savingsAccount, long ID) throws TransactionException;
    
    
}
