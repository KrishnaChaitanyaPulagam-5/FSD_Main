package com.springboot.carrental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Branch;
import com.springboot.carrental.model.CompanyAccount;
import com.springboot.carrental.model.CustomerAccount;
import com.springboot.carrental.model.Manager;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.BranchRepository;
import com.springboot.carrental.repository.ManagerRepository;
@Service
public class ManagerService {
	
	private ManagerRepository managerRepository;
	private BranchRepository branchRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyAccountService cas;
	

	public ManagerService(ManagerRepository managerRepository, BranchRepository branchRepository) {
		super();
		this.managerRepository = managerRepository;
		this.branchRepository = branchRepository;
	}



	public Manager insertManager(int branchId,Manager manager) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return managerRepository.findAll();
	}



	public Manager getByLogin(String name) {
		// TODO Auto-generated method stub
		User user=(User) userService.getUserInfo(name);
		
		return managerRepository.getByLogin(user.getId());
	}

}
