package com.springboot.carrental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.carrental.enums.CustomerStatus;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.CustomerAccount;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.CustomerRepository;
@Service
public class CustomerService {

	private CustomerRepository customerRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerAccountService cas;
	
	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}


	public Customer insertCustomer(Customer customer) {
		// TODO Auto-generated method stub
		User user=customer.getUser();
		user.setRole("CUSTOMER");
		userService.registerUser(user);
		customer.setUser(user);
		CustomerAccount acc=customer.getCustomerAccount();
		cas.addAccount(acc);
		customer.setCustomerAccount(acc);
		return customerRepository.save(customer);
	}


	public List<Customer> get_all() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}


	public Customer getByLogin(String username) {
		// TODO Auto-generated method stub
		return customerRepository.getByLogin(username);
	}


	public Customer updateCustomerStatus(int customerId, String status) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Customer customer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer Not Found"));
		customer.setStatus(CustomerStatus.valueOf(status));
		return customerRepository.save(customer);
	}

}
