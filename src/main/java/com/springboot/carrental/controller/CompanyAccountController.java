package com.springboot.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.service.CompanyAccountService;

@RestController
@RequestMapping("/api/companyAccount")
public class CompanyAccountController {
	@Autowired
	private CompanyAccountService companyAccountService;
	
	@GetMapping("/checkBalance")
	public double checkBalance() throws ResourceNotFoundException {
		return companyAccountService.checkBalance();
	}

}
