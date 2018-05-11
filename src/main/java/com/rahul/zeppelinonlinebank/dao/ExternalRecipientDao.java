package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import com.rahul.zeppelinonlinebank.pojo.ExternalRecipient;

public interface ExternalRecipientDao {
	
	List<ExternalRecipient> findall();
	
	ExternalRecipient findByName(String theName);
	
	void deleteByName(String theName);
	
	void save(ExternalRecipient recipient);

}
