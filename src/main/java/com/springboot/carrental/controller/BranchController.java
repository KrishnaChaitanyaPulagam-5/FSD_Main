package com.springboot.carrental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.exception.BadRequestException;
import com.springboot.carrental.model.Branch;
import com.springboot.carrental.service.BranchService;

@RestController
@RequestMapping("/api/branch")
@CrossOrigin(origins="http://localhost:5173")
public class BranchController {

	
	@Autowired
	private BranchService branchservice;

	
	@PostMapping("/add")
	public Branch insertBranch(@RequestBody Branch branch) {
		return branchservice.insertBranch(branch);
	}
	
	@GetMapping("/findbyId/{branchId}")
	public Branch findById(@PathVariable int branchId) throws BadRequestException{
		return branchservice.findById(branchId);
	}

	@GetMapping("/getall")
	public List<Branch> getall(){
		return branchservice.getall();
	}
	
}
