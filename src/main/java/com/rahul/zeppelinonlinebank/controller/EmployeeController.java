package com.rahul.zeppelinonlinebank.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	@GetMapping("/emp-useraccounts")
	public String showUserAccountsPage(Model model) {

		List<User> userAccountList = userService.findAllCustomers();

		model.addAttribute("userAccountList", userAccountList);

		return "emp-useraccounts";
	}

	@GetMapping("/emp-primarytransactionlist")
	public String showPrimaryTransactionList(@RequestParam(value = "primaryAccountUser") String userName,
			@RequestParam(value = "accountType") String accountType, Model model) {
		List<PrimaryTransaction> transactionList = transactionService.findPrimaryTransactionList(userName);

		model.addAttribute("transactionList", transactionList);
		model.addAttribute("accountType", accountType);
		model.addAttribute("userName", userName);
		model.addAttribute("description", "");
		model.addAttribute("amount", "");
		model.addAttribute("id", "");
		model.addAttribute("transactionType", "");
		model.addAttribute("receiversAccountNumber", "");

		return "emp-transactionlist";
	}

	@GetMapping("/emp-savingstransactionlist")
	public String showSavingsTransactionList(@RequestParam(value = "savingsAccountUser") String userName,
			@RequestParam(value = "accountType") String accountType, Model model) {
		List<SavingsTransaction> transactionList = transactionService.findSavingsTransactionList(userName);

		model.addAttribute("transactionList", transactionList);
		model.addAttribute("accountType", accountType);
		model.addAttribute("userName", userName);
		model.addAttribute("description", "");
		model.addAttribute("amount", "");
		model.addAttribute("id", "");
		model.addAttribute("transactionType", "");
		model.addAttribute("receiversAccountNumber", "");

		return "emp-transactionlist";
	}

	@PostMapping("/approve-transaction")
	public ModelAndView approveTransaction(@ModelAttribute("description") String description,
			@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType,
			@ModelAttribute("userName") String userName, @ModelAttribute("id") long id,
			@ModelAttribute("transactionType") String transactionType,
			@ModelAttribute("receiversAccountNumber") String receiversAccountNumber) {

		User user = userService.findByUserName(userName);

		System.out.println(user.getUserName());
		System.out.println(user.getPrimaryAccount().getAccountBalance());

		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		// PrimaryAccount receiversPrimaryAccount =
		// transactionService.findPrimaryAccountByAccountNumber(accountNumber)

		if (transactionType.equalsIgnoreCase("External")) {
			try {
				transactionService.approveToSomeoneElseTransfer(description, accountType, amount, primaryAccount,
						savingsAccount, id);
			} catch (TransactionException e) {
				return new ModelAndView("error-transaction", "errorMessage", e.getMessage());

			}
		} else if (transactionType.equalsIgnoreCase("Internal")) {
			PrimaryAccount receiversPrimaryAccount = transactionService
					.findPrimaryAccountByAccountNumber(receiversAccountNumber);
			try {
				transactionService.approveToSomeoneElseTransferWithin(receiversPrimaryAccount, description, accountType,
						amount, primaryAccount, savingsAccount, id);
			} catch (TransactionException e) {
				return new ModelAndView("error-transaction", "errorMessage", e.getMessage());
			}
		}

		return new ModelAndView("redirect:/employee/emp-useraccounts");
	}

	@PostMapping("/exportData")
	public ModelAndView exportData(@ModelAttribute("userName") String userName,
			@ModelAttribute("accountType") String accountType, Model model) {
		
		System.out.println(userName);
		System.out.println(accountType);
		if (accountType.equalsIgnoreCase("Primary")) {
			model.addAttribute("accountType", "Primary");
			List<PrimaryTransaction> transactionList = transactionService.findPrimaryTransactionList(userName);
			return new ModelAndView(new ExcelPrimaryStatmentReportView(), "transactionList", transactionList);
		} else if (accountType.equalsIgnoreCase("Savings")) {
			model.addAttribute("accountType", "Savings");
			List<SavingsTransaction> transactionList = transactionService.findSavingsTransactionList(userName);
			return new ModelAndView(new ExcelSavingsStatementReportView(), "transactionList", transactionList);
		}

		return new ModelAndView("error-transaction", "errorMessage", "Transaction not found");
	}

}
