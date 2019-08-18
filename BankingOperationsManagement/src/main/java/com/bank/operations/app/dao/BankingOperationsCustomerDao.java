package com.bank.operations.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bank.operations.app.entities.Customer;

@Repository
public interface BankingOperationsCustomerDao extends CrudRepository<Customer, Integer>{
	
}	