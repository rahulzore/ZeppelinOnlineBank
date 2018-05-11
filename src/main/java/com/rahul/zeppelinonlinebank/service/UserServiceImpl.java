package com.rahul.zeppelinonlinebank.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rahul.zeppelinonlinebank.dao.RoleDao;
import com.rahul.zeppelinonlinebank.dao.UserDao;
import com.rahul.zeppelinonlinebank.pojo.Role;
import com.rahul.zeppelinonlinebank.pojo.User;
import com.rahul.zeppelinonlinebank.users.Customer;
import com.rahul.zeppelinonlinebank.users.Employee;
import com.rahul.zeppelinonlinebank.users.Manager;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleDao roleDao;
	


	@Override
	@Transactional
	public User findByUserName(String userName) {
		
		return userDao.findByUserName(userName);
	}
	
	

	@Override
	@Transactional
	public void save(Customer customer) {
		User user = new User();
		
		user.setUserName(customer.getUserName());
		user.setPassword(passwordEncoder.encode(customer.getPassword()));
		user.setFirstName(customer.getFirstName());
		user.setLastName(customer.getLastName());
		user.setEmail(customer.getEmail());
		
		user.setPrimaryAccount(accountService.createPrimaryAccount());
		user.setSavingsAccount(accountService.createSavingsAccount());
		
		user.setRoles(Arrays.asList(new Role("ROLE_CUSTOMER")));
		//user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_CUSTOMER")));
		
		userDao.save(user);
		
	}
	
	@Transactional
	public void saveEmployee(Employee emp) {
		User user = new User();
		
		user.setUserName(emp.getUserName());
		user.setPassword(passwordEncoder.encode(emp.getPassword()));
		user.setFirstName(emp.getFirstName());
		user.setLastName(emp.getLastName());
		user.setEmail(emp.getEmail());
		user.setRoles(Arrays.asList(new Role("ROLE_EMPLOYEE")));
		//user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		
		userDao.save(user);
		
	}
	
	@Transactional
	public void saveManager(Manager manager) {
		User user = new User();
		
		user.setUserName(manager.getUserName());
		user.setPassword(passwordEncoder.encode(manager.getPassword()));
		user.setFirstName(manager.getFirstName());
		user.setLastName(manager.getLastName());
		user.setEmail(manager.getEmail());
		user.setRoles(Arrays.asList(new Role("ROLE_MANAGER")));
		//user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_MANAGER")));
		
		userDao.save(user);
		
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
		
		//return null;
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}



	@Override
	public List<User> findAllCustomers() {
		List<User> results = userDao.findAllCustomers();
		return results;
	}

	

}
