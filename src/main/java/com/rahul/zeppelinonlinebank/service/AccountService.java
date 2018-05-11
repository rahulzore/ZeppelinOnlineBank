package com.rahul.zeppelinonlinebank.service;

import java.security.Principal;

import com.rahul.zeppelinonlinebank.exception.TransactionException;
import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;



public interface AccountService {
	
	PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal) throws TransactionException;

}
