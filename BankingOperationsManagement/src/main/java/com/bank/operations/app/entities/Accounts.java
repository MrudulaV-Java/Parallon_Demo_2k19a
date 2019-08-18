package com.bank.operations.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="accounts")
public class Accounts {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="account_id")
	private Integer accountId;

	@Column(name="account_type",nullable=false)
	@NotBlank(message = "Type of account is mandatory")
	private String accountType;

	@Column(name="balance")
	private Integer balance;

	@ManyToOne
	private Customer customer;
	
	
	public Accounts() {
		
	}
	
	public Accounts(String accountType, Integer balance, Integer customerId) {
		
		this.accountType = accountType;
		this.balance = balance;
		this.customer = new Customer(customerId, "","","","");
		
	}

	public Integer getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
		
	public void setCustomer(Customer cust) {
		this.customer = cust;
		
	}
	
	@Override
	public String toString() {
		return "Account [AccountId=" + accountId + ", accountType=" + accountType + ", balance=" + balance
				+ ", customer=" + customer + "]";
	}

	
}
