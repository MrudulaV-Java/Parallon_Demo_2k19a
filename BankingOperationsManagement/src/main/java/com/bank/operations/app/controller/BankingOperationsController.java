package com.bank.operations.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.operations.app.entities.Accounts;
import com.bank.operations.app.entities.Customer;
import com.bank.operations.app.service.BankingOperationsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value="/api")
public class BankingOperationsController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BankingOperationsService bankingOperationsService;
	
	// Create Customer
	@PostMapping(value="/customer/createcustomer")
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {	
		log.info("Created new customer.");
		bankingOperationsService.createCustomer(customer);
		return new ResponseEntity<String> ("Customer created successfully", HttpStatus.OK);
	}
	
	//Read Customer by ID
	@GetMapping(value="/customer/read/{customerId}")
	public Customer getCustomerById(@PathVariable("customerId") Integer customerId) {
		return bankingOperationsService.getCustomerById(customerId);
	}
	
	//Read All Customers
	@GetMapping(value="/customer/readallcustomers")
	public Iterable<Customer> getAllCustomers(){
		return bankingOperationsService.getAllCustomers();		
	}
	
	//Update Customer
	@PutMapping(value="/customer/{customerId}/{newEmail:.+}")
	public ResponseEntity<String> updateCustomerEmail(@PathVariable("customerId") Integer customerId,@PathVariable("newEmail") String newEmail) {
		bankingOperationsService.updateCustomerEmail(customerId,newEmail);
		return new ResponseEntity<String> ("Customer email updated successfully", HttpStatus.OK);
	}

	//Delete Customer
	@DeleteMapping(value="/customer/delete/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Integer customerId) {
		bankingOperationsService.deleteCustomerById(customerId);
		log.info("Customer deleted.");
		return new ResponseEntity<String> ("Customer deleted successfully", HttpStatus.OK);
	}
	
	//Create Account
	@PostMapping(value="/customer/{customerId}/accounts")
	public ResponseEntity<String> addAccount(@RequestBody Accounts account, @PathVariable Integer customerId) {
		account.setCustomer(new Customer(customerId,"","","",""));
		bankingOperationsService.createAccount(account);
		return new ResponseEntity<String> ("Account created successfully", HttpStatus.OK);
	}
	
	//Read Account associated to an accountid
	@GetMapping(value="/customer/{customerId}/accounts/read/{accountId}")
	public Accounts getAccount(@PathVariable Integer accountId) {
		return bankingOperationsService.getAccount(accountId);
	}
	
	//Read All Accounts associated to an customerid
	@GetMapping(value="/customer/{customerId}/accounts/readallaccounts")
	public List<Accounts> getAllAccounts(@PathVariable Integer customerId){
		return bankingOperationsService.getAllAccounts(customerId);
	}

	//Read All Accounts
	@GetMapping(value="accounts/readallaccounts")
	public Iterable<Accounts> readAllAccounts(){
		return bankingOperationsService.readAllAccounts();		
	}
	
	//Update AccountType of an accountid 
	@PutMapping(value = "/accounts/{accountId}/update/{newAcctType}")
	public ResponseEntity<String> updateAccount(@PathVariable Integer accountId, @PathVariable("newAcctType") String newAcctType) {
		bankingOperationsService.updateAccountType(accountId, newAcctType);
		return new ResponseEntity<String> ("Updated account type successfully", HttpStatus.OK);
	}
	
	// Delete Account associated to an accountid 
	@DeleteMapping(value = "/customer/{customerId}/accounts/delete/{accountId}")
	public ResponseEntity<String> deleteAccount(@PathVariable Integer accountId) {
		bankingOperationsService.deleteAccountById(accountId);
		return new ResponseEntity<String> ("Account deleted successfully", HttpStatus.OK);
	}
	
	//Deposit funds on accounts using accountid
	@PutMapping(value="/customer/{customerId}/accounts/{accountId}/deposit/{depositAmount}")
	public Accounts depositFunds(@PathVariable Integer accountId, @PathVariable Integer depositAmount) {
		return bankingOperationsService.depositFunds(accountId, depositAmount);
	}
	
	//Withdraw funds on accounts using accountid
	@PutMapping(value="/accounts/{accountId}/withdraw/{withdrawAmount}")
	public Accounts withdrawFunds(@PathVariable Integer accountId, @PathVariable Integer withdrawAmount) {
		return bankingOperationsService.withdrawFunds(accountId, withdrawAmount);
	}
	
	//Transfer funds within internal accounts using customerid and accountids
	@PutMapping(value="/customer/{customerId}/transfer/accounts/from/{accountId1}/to/{accountId2}/amount/{transferAmt}")
	public Accounts transferFunds(@PathVariable Integer customerId, @PathVariable Integer accountId1, 
			@PathVariable Integer accountId2, @PathVariable Integer transferAmt) {
		return bankingOperationsService.transferFunds(customerId, accountId1, accountId2, transferAmt);
	}
}



















