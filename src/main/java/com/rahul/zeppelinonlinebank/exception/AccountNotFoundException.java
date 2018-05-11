package com.rahul.zeppelinonlinebank.exception;

public class AccountNotFoundException extends Exception {
	public AccountNotFoundException(String message) {
		super("AccountNotFoundException: "+message);
	}
	
	public AccountNotFoundException(String message, Throwable cause) {
		super("AccountNotFoundException: "+message, cause);
	}
}
