package com.rahul.zeppelinonlinebank.dao;

import java.util.List;

import com.rahul.zeppelinonlinebank.pojo.User;

public interface UserDao {

	User findByUserName(String userName);
	
	 void save(User user);
	
	 List<User> findAllCustomers();
	 
}
