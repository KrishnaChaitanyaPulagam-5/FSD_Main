package com.springboot.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.Lender;
import com.springboot.carrental.service.CustomerAccountService;

@RestController
@RequestMapping("/api/customeraccount")
public class CustomerAccountController{

	@Autowired
	private CustomerAccountService customerAccountService;
	
	
	@GetMapping("/getAmount")
	public double getAmount(Customer customer) throws ResourceNotFoundException {
		return customerAccountService.getAmount(customer);
	}
	
	@PutMapping("/updateAmount")
	public void updateAmount(Customer customer,double amount) throws InsufficientBalanceException, ResourceNotFoundException {
		customerAccountService.updateAmount(customer,amount);
	}
	
	@PutMapping("/updateLenderAmount")
	public void updateAmount(Lender lender,double amount) throws InsufficientBalanceException, ResourceNotFoundException {
		customerAccountService.updateLenderAmount(lender,amount);
	}
	
}
