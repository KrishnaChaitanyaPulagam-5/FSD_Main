package com.springboot.carrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Branch;
import com.springboot.carrental.model.CompanyAccount;
import com.springboot.carrental.model.Manager;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.BranchRepository;
import com.springboot.carrental.repository.ManagerRepository;
@Service
public class ManagerService {
	
	private ManagerRepository managerRepository;
	private BranchRepository branchRepository;
	private UserService userService;
	private CompanyAccountService cas;
	

	public ManagerService(ManagerRepository managerRepository, BranchRepository branchRepository,UserService userService,
			CompanyAccountService cas) {
		super();
		this.managerRepository = managerRepository;
		this.branchRepository = branchRepository;
		this.userService=userService;
		this.cas=cas;
	}


	public Manager insertManager(int branchId,Manager manager) throws ResourceNotFoundException {
		Branch branch=branchRepository.findById(branchId).orElseThrow(()->new ResourceNotFoundException("Branch Not Found"));
		manager.setBranch(branch);
		User user=manager.getUser();
		user.setRole("MANAGER");
		userService.registerUser(user);
		manager.setUser(user);
		CompanyAccount acc=cas.getById(1);
		manager.setCompanyAccount(acc);
		return managerRepository.save(manager);
	}

	public List<Manager> getall() {
		return managerRepository.findAll();
	}

	public Manager getByLogin(String name) {
		User user=(User) userService.getUserInfo(name);
		return managerRepository.getByLogin(user.getId());
	}

}
