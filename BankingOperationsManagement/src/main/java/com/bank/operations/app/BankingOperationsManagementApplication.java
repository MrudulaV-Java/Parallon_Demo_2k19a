package com.bank.operations.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bank.operations.app.entities.Accounts;
import com.bank.operations.app.entities.Customer;
import com.bank.operations.app.service.BankingOperationsService;

@SpringBootApplication
public class BankingOperationsManagementApplication implements CommandLineRunner{	
	
	@Autowired
	private BankingOperationsService bankingOperationsService;
	
	public static void main(String[] args) {
		SpringApplication.run(BankingOperationsManagementApplication.class, args);		
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customer = new Customer();
		customer.setCustomerName("Customer1");
		customer.setContactNumber("615-965-3571");
		customer.setEmail("customer.1@gmail.com");
		customer.setAddress("Franklin");
		
		bankingOperationsService.createCustomer(customer);
		
		Integer custId = customer.getCustomerId();
		Accounts account = new Accounts("Savings", 4000, custId );
				
		bankingOperationsService.createAccount(account);
		
	}
}
	