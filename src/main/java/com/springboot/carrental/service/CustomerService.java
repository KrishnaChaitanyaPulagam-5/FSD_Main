package com.springboot.carrental.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.carrental.enums.CustomerStatus;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.CustomerAccount;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.CustomerRepository;
@Service
public class CustomerService {

	private CustomerRepository customerRepository;
	private UserService userService;	
	private CustomerAccountService cas;
	
	public CustomerService(CustomerRepository customerRepository,UserService userService,CustomerAccountService cas) {
		super();
		this.customerRepository = customerRepository;
		this.userService=userService;
		this.cas=cas;
	}


	public Customer insertCustomer(Customer customer) {
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
		return customerRepository.findAll();
	}


	public Customer getByLogin(String username) {
		return customerRepository.getByLogin(username);
	}


	public Customer updateCustomerStatus(int customerId, String status) throws ResourceNotFoundException {
		Customer customer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer Not Found"));
		customer.setStatus(CustomerStatus.valueOf(status));
		return customerRepository.save(customer);
	}


	public Customer updateByLogin(Customer customer,Principal principal) throws IOException {
		// TODO Auto-generated method stub
		Customer existing=customerRepository.getByLogin(principal.getName());
		if(customer.getName()!=null) {
		existing.setName(customer.getName());}
		if(customer.getAddress()!=null) {
	    existing.setAddress(customer.getAddress());
		}
		if(customer.getContactinfo()!=null) {
	    existing.setContactinfo(customer.getContactinfo());
		}
		if(customer.getDriverlicense()!=null) {
	    existing.setDriverlicense(customer.getDriverlicense());
		}
	    if(customer.getCustomerAccount()!=null && customer.getCustomerAccount().getAmount() > 0) {
	    double amount=existing.getCustomerAccount().getAmount();
	    existing.getCustomerAccount().setAmount(amount+customer.getCustomerAccount().getAmount());}
		
	    
		return customerRepository.save(existing);
	}
	
	public Customer uploadProfilePic(MultipartFile file, String username) throws IOException {
	    Customer customer = customerRepository.getByLogin(username);

	    if (customer == null) {
	        throw new RuntimeException("Customer not found with username: " + username);
	    }

	    String originalFileName = file.getOriginalFilename();

	    //validate extension
	    if (originalFileName == null || !originalFileName.contains(".")) {
	        throw new RuntimeException("Invalid file name: " + originalFileName);
	    }

	    String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();
	    if (!List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension)) {
	        throw new RuntimeException("File extension '" + extension + "' not allowed. Allowed: jpg, jpeg, png, gif, svg");
	    }

	    //validate file size
	    long kbs = file.getSize() / 1024;
	    if (kbs > 3000) {
	        throw new RuntimeException("Image too large. Max allowed size is 3000 KB. Provided: " + kbs + " KB");
	    }

	    //file save directory
	    String uploadDir = "D:\\FSD hexaware\\react-car-ui\\public\\images";
	    Files.createDirectories(Path.of(uploadDir));

	    //full path to save file
	    Path path = Paths.get(uploadDir, originalFileName);
	    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	    //save image path in customer
	    customer.setProfilepic(originalFileName);
	    return customerRepository.save(customer);
	}


}
