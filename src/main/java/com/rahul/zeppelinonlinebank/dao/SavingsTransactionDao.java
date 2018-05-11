package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsTransaction;

public interface SavingsTransactionDao {

	List<SavingsTransaction> findAll();
	
	void save(SavingsTransaction savingsTransaction);
	
	SavingsTransaction findTransaction(long ID);
}
