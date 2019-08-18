package com.bank.operations.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bank.operations.app.entities.Accounts;


public interface BankingOperationsAccountsDao extends CrudRepository<Accounts, Integer>{

	public List<Accounts> findByCustomerCustomerId(Integer customerId);
}
