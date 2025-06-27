package com.springboot.carrental.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Manager;
import com.springboot.carrental.service.ManagerService;
@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	@Autowired
	private ManagerService managerService;
	
	
	@PostMapping("/add/{branchId}")
	public Manager insertManager(@PathVariable int branchId,@RequestBody Manager manager) throws ResourceNotFoundException {
		
		return managerService.insertManager(branchId,manager);
		
	}
	@GetMapping("/getall")
	public List<Manager> getall(){
		return managerService.getall();
	}
	
	@GetMapping("/getByLogin")
	public Manager getByLogin(Principal principal) {
		return managerService.getByLogin(principal.getName());
	}
	
	
	
	

}
