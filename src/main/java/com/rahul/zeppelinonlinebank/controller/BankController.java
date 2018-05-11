package com.rahul.zeppelinonlinebank.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;
import com.rahul.zeppelinonlinebank.pojo.User;
import com.rahul.zeppelinonlinebank.service.UserService;

@Controller
public class BankController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String showHomePage() {
		return "home";
	}
	
	@GetMapping("/employee")
	public String showEmployeePage() {
		
		return "employee";
	}
	
	
	
	@GetMapping("/manager")
	public String showManagerPage() {
		
		return "manager";
	}
	
	@GetMapping("/customer-home")
	public String showCustomerHomePage(Model model, Principal principal) {
		
		User user = userService.findByUserName(principal.getName());
		
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		model.addAttribute("primaryAccount", primaryAccount);
		model.addAttribute("savingsAccount", savingsAccount);
		
		return "customer-home";
	}
}
