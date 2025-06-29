package com.springboot.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Branch;
import com.springboot.carrental.model.Manager;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.BranchRepository;
import com.springboot.carrental.repository.ManagerRepository;
import com.springboot.carrental.service.CompanyAccountService;
import com.springboot.carrental.service.ManagerService;
import com.springboot.carrental.service.UserService;


@SpringBootTest
public class ManagerServiceTest {
	
	@InjectMocks
	private ManagerService managerService;
	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private BranchRepository branchRepository;
	@Mock
	private UserService userService;
	@Mock
	private CompanyAccountService cas;
	
	private Manager manager;
	@BeforeEach
	public void init() {
		Branch branch=new Branch();
		branch.setId(1);
		branch.setName("chennai");
		
		User user=new User();
		user.setId(1);
		user.setUsername("krishna@gmail.com");
		
		manager=new Manager();
		manager.setId(1);
		manager.setName("Krishna");
		manager.setBranch(branch);
			
	}
	@Test
	public void insertManager() throws ResourceNotFoundException {
		manager=new Manager();
		Branch branch=new Branch();
		branch.setId(1);
		when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
		manager.setBranch(branch);
		User user=new User();
		user.setRole("MANAGER");
		manager.setUser(user);
		when(managerRepository.save(any(Manager.class))).thenReturn(manager);
		Manager res=managerService.insertManager(1, manager);
		assertEquals(manager, res);
	}
	

}
