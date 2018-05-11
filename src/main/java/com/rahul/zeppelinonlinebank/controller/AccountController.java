package com.rahul.zeppelinonlinebank.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rahul.zeppelinonlinebank.exception.TransactionException;
import com.rahul.zeppelinonlinebank.pojo.PrimaryAccount;
import com.rahul.zeppelinonlinebank.pojo.PrimaryTransaction;
import com.rahul.zeppelinonlinebank.pojo.SavingsAccount;
import com.rahul.zeppelinonlinebank.pojo.SavingsTransaction;
import com.rahul.zeppelinonlinebank.pojo.User;
import com.rahul.zeppelinonlinebank.service.AccountService;
import com.rahul.zeppelinonlinebank.service.TransactionService;
import com.rahul.zeppelinonlinebank.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transacionService;

	@Autowired
	private UserService userService;

	@GetMapping("/primaryAccountDetails")
	public String showPrimaryAccountDetails(Model model, Principal principal) {

		User user = userService.findByUserName(principal.getName());

		PrimaryAccount primaryAccount = user.getPrimaryAccount();

		List<PrimaryTransaction> primaryTransactionList = transacionService
				.findPrimaryTransactionList(principal.getName());

		model.addAttribute("primaryAccount", primaryAccount);
		model.addAttribute("primaryTransactionList", primaryTransactionList);

		return "primaryAccount";

	}
	
	@GetMapping("/savingsAccountDetails")
	public String showSavingsAccountDetails(Model model, Principal principal) {
		
		User user = userService.findByUserName(principal.getName());
		
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		List<SavingsTransaction> savingsTransactionList = transacionService.findSavingsTransactionList(principal.getName());
		
		model.addAttribute("savingsAccount", savingsAccount);
		model.addAttribute("savingsTransactionList", savingsTransactionList);
		
		return "savingsAccount";
		
	}
	
	@GetMapping("/depositAmount")
	public String showDepositPage(Model model) {
		model.addAttribute("accountType", "");
		model.addAttribute("amount","");
		return "deposit";
	}
	
	@GetMapping("/withdrawAmount")
	public String showWithdrawPage(Model model) {
		model.addAttribute("accountType", "");
		model.addAttribute("amount","");
		return "withdraw";
	}
	
	@PostMapping("/depositAmount")
	public String depositPost(@ModelAttribute("amount")String amount, @ModelAttribute("accountType")String accountType, Principal principal) {
		
		accountService.deposit(accountType, Double.parseDouble(amount), principal);
		return "redirect:/customer-home";
	}
	
	@PostMapping("/withdrawAmount")
	public ModelAndView withdrawPost(@ModelAttribute("amount")String amount, @ModelAttribute("accountType")String accountType, Principal principal) {
		try {
		accountService.withdraw(accountType, Double.parseDouble(amount), principal);
		} catch (TransactionException e) {
			return new ModelAndView("error-transaction", "errorMessage", e.getMessage());
		}
		return new ModelAndView("redirect:/customer-home");
	}

}
