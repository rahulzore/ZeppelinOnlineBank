package com.rahul.zeppelinonlinebank.controller;


import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rahul.zeppelinonlinebank.pojo.User;
import com.rahul.zeppelinonlinebank.service.UserService;
import com.rahul.zeppelinonlinebank.users.Customer;
import com.rahul.zeppelinonlinebank.users.Employee;
import com.rahul.zeppelinonlinebank.users.Manager;


@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
		
	}
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		theModel.addAttribute("customer", new Customer());
		
		return "registration-form";
	}
	
	@GetMapping("/showEmployeeRegistrationForm")
	public String showEmployeeRegistrationPage(Model theModel) {
		theModel.addAttribute("employee", new Employee());
		
		return "employee-registration-form";
	}
	
	@GetMapping("/showManagerRegistrationForm")
	public String showManagerRegistrationForm(Model theModel) {
		theModel.addAttribute("manager", new Manager());
		
		return "manager-registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@Valid @ModelAttribute("customer") Customer customer,
			BindingResult theBindingResult, 
			Model theModel) {
		
		String userName = customer.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		if (theBindingResult.hasErrors()){
			return "registration-form";
		}
		
		// check the database if user already exists
		User existing = userService.findByUserName(userName);
		if(existing != null) {
			theModel.addAttribute("customer", new Customer());
			theModel.addAttribute("registrationError", "User name already exists.");
			
			logger.warning("User name already exists.");
        	return "registration-form";	
		}
		
		//create user account
		
		userService.save(customer);
		
		logger.info("Successfully created user: " + userName);
        
        return "registration-confirmation";	
		
	}
	
	@PostMapping("/processEmployeeRegistrationForm")
	public String processEmployeeRegistraionForm(
			@Valid @ModelAttribute("employee") Employee emp,
			BindingResult theBindingResult,
			Model theModel) {
		
		String userName = emp.getUserName();
		
		if(theBindingResult.hasErrors()) {
			return "employee-registration-form";
		}
		
		User existing = userService.findByUserName(userName);
		if(existing != null) {
			theModel.addAttribute("employee", new Employee());
			theModel.addAttribute("registrationError", "User name already exists.");
			
			logger.warning("User name already exists.");
			return "employee-registration-form";
		}
		
		userService.saveEmployee(emp);
		return "registration-confirmation";
	}
	
	@PostMapping("/processManagerRegistrationForm")
	public String processManagerRegistrationForm(
			@Valid @ModelAttribute("manager") Manager manager,
			BindingResult theBindingResult,
			Model theModel) {
		
		String userName = manager.getUserName();
		
		if(theBindingResult.hasErrors()) {
			return "manager-registration-form";
		}
		
		User existing = userService.findByUserName(userName);
		if(existing != null) {
			theModel.addAttribute("manager", new Manager());
			theModel.addAttribute("registrationError", "User name already exists");
			
			logger.warning("User name already exists");
			return "manager-registration-form";
		}
		
		userService.saveManager(manager);
		return "registration-confirmation";
		
	}
}
