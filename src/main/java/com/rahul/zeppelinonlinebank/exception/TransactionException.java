package com.rahul.zeppelinonlinebank.exception;

public class TransactionException extends Exception {
	
	public TransactionException(String message) {
		super("TransactionException: "+message);
	}
	
	public TransactionException(String message, Throwable cause) {
		super("TransactionException: "+message, cause);
	}

}
