package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import com.rahul.zeppelinonlinebank.pojo.PrimaryTransaction;

public interface PrimaryTransactionDao {

	List<PrimaryTransaction> findAll();
	
	void save(PrimaryTransaction primaryTransaction);
	
	PrimaryTransaction findTransaction(long ID);
}
