package com.springboot.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.carrental.enums.CustomerStatus;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.CustomerRepository;
import com.springboot.carrental.service.CustomerAccountService;
import com.springboot.carrental.service.CustomerService;
import com.springboot.carrental.service.UserService;

@SpringBootTest
public class CustomerServiceTest {
	
	@InjectMocks
	private CustomerService customerService;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private UserService userService;
	@Mock
	private CustomerAccountService cas;
	
	private Customer customer;
	@BeforeEach
	public void init() {
		User user=new User();
		user.setId(1);
		user.setUsername("krish@gmail.com");
		
		customer=new Customer();
		customer.setId(1);
		customer.setName("Krishna");
		customer.setUser(user);
		
	}
	@Test
	public void InsertTest() {
		customer=new Customer();
		customer.setId(1);
		User user=new User();
		user.setRole("CUSTOMER");
		customer.setUser(user);
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		Customer savedCustomer=customerService.insertCustomer(customer);
		assertEquals(customer,savedCustomer);
		
	}
	@Test
	public void updateCustomerStatusTest() throws ResourceNotFoundException {
		customer=new Customer();
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		Customer result=customerService.updateCustomerStatus(1, "INACTIVE");
		assertEquals(CustomerStatus.INACTIVE,result.getStatus());
		
	}
	@Test
	public void updateCustomerStatusTestFailed() {
		customer =new Customer();
		when(customerRepository.findById(1)).thenReturn(Optional.empty());
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		ResourceNotFoundException e=assertThrows(ResourceNotFoundException.class, ()->customerService.updateCustomerStatus(1, "ACTIVE"));
		assertEquals("Customer Not Found", e.getMessage());
	}
	
	
	@Test
	public void getAllTest() {
		when(customerRepository.findAll()).thenReturn(List.of(customer));
		assertEquals(List.of(customer), customerService.get_all());
	}
	
}





