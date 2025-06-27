package com.springboot.carrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.carrental.exception.BadRequestException;
import com.springboot.carrental.model.Branch;
import com.springboot.carrental.repository.BranchRepository;
@Service
public class BranchService {

	
	private BranchRepository branchRepository;
	
	
	
	public BranchService(BranchRepository branchRepository) {
		super();
		this.branchRepository = branchRepository;
	}



	public Branch insertBranch(Branch branch) {
		// TODO Auto-generated method stub
		return branchRepository.save(branch);
	}



	public Branch findById(int branchId) throws BadRequestException {
		// TODO Auto-generated method stub
		return branchRepository.findById(branchId).orElseThrow(() -> new BadRequestException("Invalid ID"));
	}



	public List<Branch> getall() {
		// TODO Auto-generated method stub
		return branchRepository.findAll();
	}

}
