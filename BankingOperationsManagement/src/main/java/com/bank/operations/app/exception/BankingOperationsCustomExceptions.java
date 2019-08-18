package com.bank.operations.app.exception;

public class BankingOperationsCustomExceptions extends RuntimeException{

	public BankingOperationsCustomExceptions (String message) {
		super(message);
	}
	
	public BankingOperationsCustomExceptions (Integer Id) {
		super("ID " + Id + " does not exist");
	}

	public BankingOperationsCustomExceptions(String message, Throwable cause) {
		super(message, cause);
	}
	
}
