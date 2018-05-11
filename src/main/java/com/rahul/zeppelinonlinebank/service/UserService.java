package com.rahul.zeppelinonlinebank.service;



import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.rahul.zeppelinonlinebank.pojo.User;
import com.rahul.zeppelinonlinebank.users.Customer;
import com.rahul.zeppelinonlinebank.users.Employee;
import com.rahul.zeppelinonlinebank.users.Manager;

public interface UserService extends UserDetailsService {

		User findByUserName(String userName);
		
		List<User> findAllCustomers();
		
		void save(Customer customer);
		void saveEmployee(Employee emp);
		void saveManager(Manager manager);
}
