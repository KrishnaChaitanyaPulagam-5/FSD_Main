package com.springboot.carrental.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add")
	public Customer insertcustomer(@RequestBody Customer customer) {
		return customerService.insertCustomer(customer);
	}
	
	@GetMapping("/get-all")
	public List<Customer> get_all(){
		return customerService.get_all();
	}
	
	
	@GetMapping("getByLogin")
	public Customer getByLogin(Principal principal) {
		
		String username=principal.getName();
		
		return customerService.getByLogin(username);
	}
	
	@PutMapping("/update/status/{customerId}")
	public Customer updateCustomerStatus(@PathVariable int customerId,@RequestParam String status) throws ResourceNotFoundException {
		return customerService.updateCustomerStatus(customerId,status);
	}
	
	@PutMapping("/updateByLogin")
	public Customer updateByLogin(@RequestBody Customer customer,Principal principal) throws IOException {
		return customerService.updateByLogin(customer,principal);
	}
	
	@PostMapping("/upload/profile_pic")
    public Customer uploadProfilePic(Principal principal,@RequestParam MultipartFile file)throws IOException {
    	 return customerService.uploadProfilePic(file, principal.getName());
    }
}
