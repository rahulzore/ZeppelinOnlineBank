package com.rahul.zeppelinonlinebank.dao;

import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;

public interface PrimaryAccountDao {

	PrimaryAccount findByAccountNumber (int accountNumber);
	
	void save( PrimaryAccount primaryAccount);
}
