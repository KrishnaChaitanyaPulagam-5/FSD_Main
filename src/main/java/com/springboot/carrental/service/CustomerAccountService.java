package com.springboot.carrental.service;


import org.springframework.stereotype.Service;

import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.CustomerAccount;
import com.springboot.carrental.model.Lender;

import com.springboot.carrental.repository.CustomerAccountRepository;

@Service
public class CustomerAccountService {

	private CustomerAccountRepository customerAccountRepository;
	


	public CustomerAccountService(CustomerAccountRepository customerAccountRepository) {
		super();
		this.customerAccountRepository = customerAccountRepository;
		
	}
	
	public CustomerAccount addAccount(CustomerAccount customerAccount) {
		return customerAccountRepository.save(customerAccount);
	}
	
	public CustomerAccount findById(int id) throws ResourceNotFoundException {
		return customerAccountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer doesn't have an Account"));
	}


	public double getAmount(Customer customer) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		int id=customer.getId();
		if(customerAccountRepository.findById(id).isPresent()) {
		return customerAccountRepository.getAmount(id);}
		else {
			throw new ResourceNotFoundException("Customer doesn't Have an Account");
		}
	}


	public void updateAmount(Customer customer,double amount) throws InsufficientBalanceException, ResourceNotFoundException {
	
		int id=customer.getCustomerAccount().getId();
		CustomerAccount customerAccount=findById(id);

		double cost=customerAccount.getAmount()-amount;
		if(cost<0)
		{
			throw new InsufficientBalanceException("Insufficient Balance. Available Balance: "+customerAccount.getAmount());
		}
		
		else {
		customerAccount.setAmount(cost);
		customerAccountRepository.save(customerAccount);
		}
		
		
	}

	public void updateLenderAmount(Lender lender, double amount) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		int id=lender.getId();
		CustomerAccount customerAccount=findById(id);

		double cost=customerAccount.getAmount()+amount;
		customerAccount.setAmount(cost);
		customerAccountRepository.save(customerAccount);
		
		
	}

}
