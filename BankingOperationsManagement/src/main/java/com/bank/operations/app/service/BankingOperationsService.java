package com.bank.operations.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.operations.app.dao.BankingOperationsAccountsDao;
import com.bank.operations.app.dao.BankingOperationsCustomerDao;
import com.bank.operations.app.entities.Accounts;
import com.bank.operations.app.entities.Customer;
import com.bank.operations.app.exception.BankingOperationsCustomExceptions;

@Service
public class BankingOperationsService {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BankingOperationsCustomerDao bankingOperationsCustomerDao;
	@Autowired
	private BankingOperationsAccountsDao bankingOperationsAccountsDao;
	
	//Create customer
	public Customer createCustomer(Customer customer) {
		Customer cust;
		if(null != customer.getCustomerName() && null != customer.getEmail()) {
			cust = bankingOperationsCustomerDao.save(customer);
		}else {
			log.error("Customer name and email are mandatory");
			throw new BankingOperationsCustomExceptions("Customer name and email are mandatory.");
		}
		return cust;			
		
	}

	// Read customer by id
	public Customer getCustomerById(Integer customerId) {
		return bankingOperationsCustomerDao.findById(customerId).orElseThrow(() -> new BankingOperationsCustomExceptions(customerId));
	}

	//Read all customers
	public Iterable<Customer> getAllCustomers() {
		return bankingOperationsCustomerDao.findAll();
	}

	//Delete customer by id
	public void deleteCustomerById(Integer customerId) {
		List<Accounts> accounts = getAllAccounts(customerId);
		if (accounts.size() > 0) {
			for (Accounts acc : accounts) {
				if (!(acc.getBalance() > 0)) {
					deleteAccountById(acc.getAccountId());
				}else {
					log.error("Customer cant be deleted. Has active accounts.");
					throw new BankingOperationsCustomExceptions("Customer account has balance. Please clear the accounts.");										
				}
			}
		}
		bankingOperationsCustomerDao.deleteById(customerId);
		log.info("Customer Deleted.");
	}

	//Update customer email by id
	public Customer updateCustomerEmail(Integer customerId, String newEmail) {
		Customer cust = bankingOperationsCustomerDao.findById(customerId).orElseThrow(() -> new BankingOperationsCustomExceptions(customerId));
		cust.setEmail(newEmail);
		Customer updatedCust = bankingOperationsCustomerDao.save(cust);
		return updatedCust;
	}
	
	//Create account
	public Accounts createAccount(Accounts account) {
		Accounts acc;
		if(null != account.getAccountType()) {
			acc = bankingOperationsAccountsDao.save(account);
		}else {
			log.error("Account type is mandatory");
			throw new BankingOperationsCustomExceptions("Account Type field is mandatory.");
		}
		return acc;
	}	
	
	//Read account by accountid
	public Accounts getAccount(Integer accountId) {
		return bankingOperationsAccountsDao.findById(accountId).orElseThrow(() -> new BankingOperationsCustomExceptions(accountId));
	}

	// Read all accounts corresponding to customer id
	public List<Accounts> getAllAccounts(Integer customerId){
		List<Accounts> accounts = new ArrayList<>();
		bankingOperationsAccountsDao.findByCustomerCustomerId(customerId).forEach(accounts :: add);
		return accounts;
	}
	
	//Read all accounts
	public Iterable<Accounts> readAllAccounts() {
		return bankingOperationsAccountsDao.findAll();
	}
	
	//Update accountType an accountid
	public Accounts updateAccountType(Integer accountId, String newAcctType) {
		Accounts acc = bankingOperationsAccountsDao.findById(accountId).orElseThrow(() -> new BankingOperationsCustomExceptions(accountId));
		acc.setAccountType(newAcctType);
		Accounts updatedAcc = bankingOperationsAccountsDao.save(acc);
		return updatedAcc;
	}
	
	//Delete account associated to an accountid
	public void deleteAccountById(Integer accountId) {
		Accounts acc = getAccount(accountId);
		bankingOperationsAccountsDao.deleteById(acc.getAccountId());
		log.info("Deleted account.");
	}

	//Deposit funds to accounts using accountid
	public Accounts depositFunds(Integer accountId, Integer depositAmount) {
		Accounts acc = bankingOperationsAccountsDao.findById(accountId).orElseThrow(() -> new BankingOperationsCustomExceptions(accountId));
		Integer newBalance = acc.getBalance() + depositAmount;
		acc.setBalance(newBalance);		
		Accounts updatedAcc = bankingOperationsAccountsDao.save(acc);
		return updatedAcc;
	}

	//Withdraw funds from accounts using accountid
	public Accounts withdrawFunds(Integer accountId, Integer withdrawAmount) {
		Accounts acc = bankingOperationsAccountsDao.findById(accountId).orElseThrow(() -> new BankingOperationsCustomExceptions(accountId));
		if(acc.getBalance() > 0 && withdrawAmount <= acc.getBalance()) {
			Integer updateBalance = acc.getBalance() - withdrawAmount;
			acc.setBalance(updateBalance);
		}
		Accounts updatedAcc = bankingOperationsAccountsDao.save(acc);
		return updatedAcc;
	}

	//Transfer funds between internal accounts of customer
	public Accounts transferFunds(Integer customerId, Integer accountId1, Integer accountId2, Integer transferAmt) {
		List<Accounts> accounts = getAllAccounts(customerId);
		Integer fromAccount = null;
		Integer toAccount = null;
		Integer fromBalance = null;
		Integer toBalance = null;
		if (accounts.size() > 1) {
			for (Accounts acc : accounts) {
				if (!(acc.getBalance() < 0)
						&& ((acc.getAccountId().equals(accountId1)) || (acc.getAccountId().equals(accountId2)))) {
					if (acc.getAccountId().equals(accountId1)) {
						fromAccount = acc.getAccountId();
						fromBalance = acc.getBalance();
					} else {
						toAccount = acc.getAccountId();
						toBalance = acc.getBalance();
					}
				}
			}
		}
		if (!(null == fromAccount) && !(null == toAccount) && !(null == fromBalance) && !(null == toBalance)) {
			Accounts acct = withdrawFunds(fromAccount, transferAmt);
			if (!(transferAmt > fromBalance)) {
				acct = depositFunds(toAccount, transferAmt);				
			}return acct;
		}
		return null;
	}

}
