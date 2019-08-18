package com.bank.operations.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class BankingOperationsExceptionHandler {

	@ExceptionHandler(value = {BankingOperationsCustomExceptions.class})
	public ResponseEntity<Object> handleBankingOperationsCustomExceptions(BankingOperationsCustomExceptions e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		BankingOperationsException exception = new BankingOperationsException(e.getMessage(),badRequest, ZonedDateTime.now(ZoneId.of("Z")));
		return new ResponseEntity<>(exception, badRequest);
	}
}
