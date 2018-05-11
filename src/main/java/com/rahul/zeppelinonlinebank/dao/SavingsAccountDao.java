package com.rahul.zeppelinonlinebank.dao;

import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;

public interface SavingsAccountDao {

	SavingsAccount findByAccountNumber(int accountNumber);
	
	void save(SavingsAccount savingsAccount);
	
	
}
