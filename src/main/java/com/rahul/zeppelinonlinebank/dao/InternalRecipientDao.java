package com.rahul.zeppelinonlinebank.dao;

import java.util.List;
import com.rahul.zeppelinonlinebank.pojo.InternalRecipient;

public interface InternalRecipientDao {
	
	List<InternalRecipient> findall();
	
	InternalRecipient findByName(String theName);
	
	
	
	void deleteByName(String theName);
	
	void save(InternalRecipient recipient);

}
