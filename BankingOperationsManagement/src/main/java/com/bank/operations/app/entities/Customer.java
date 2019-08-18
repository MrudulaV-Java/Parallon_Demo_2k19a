package com.bank.operations.app.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id")
	private Integer customerId;

	@Column(name="customer_name",nullable=false)
	@NotBlank(message = "Name is mandatory")
	private String customerName;

	@Column(name="contact_number")
	private String contactNumber;

	@Column(name="email")
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@Column(name="address")
	private String address;
	
	public Customer() {
		
	}
	
	public Customer(Integer customerId, String customerName, String contactNumber, String email, String address) {
		
		this.customerId = customerId;
		this.customerName = customerName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.address = address;
	}

	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", contactNumber=" + contactNumber
				+ ", email=" + email + ", address=" + address + "]";
	}
}
