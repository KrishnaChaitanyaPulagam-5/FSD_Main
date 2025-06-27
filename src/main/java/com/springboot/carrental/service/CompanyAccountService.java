package com.springboot.carrental.service;

import org.springframework.stereotype.Service;

import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.CompanyAccount;
import com.springboot.carrental.repository.CompanyAccountRepository;

@Service
public class CompanyAccountService {
	
	
	private CompanyAccountRepository companyAccountRepository;

	public CompanyAccountService(CompanyAccountRepository companyAccountRepository) {
		super();
		this.companyAccountRepository = companyAccountRepository;
	}
	public CompanyAccount getById(int id) throws ResourceNotFoundException {
		return companyAccountRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("CompanyAccount not Reachable"));
	}
	public void payToCompanyAccount(double amount) {
		try {
			
		CompanyAccount companyAccount=companyAccountRepository.findById(1).orElseThrow(()->new ResourceNotFoundException("SORRY FOR THE INCONVINIENCE.STAY PUT OUR TEAM IS WORKING ON IT."));
		double bal=companyAccount.getAmount();
		amount+=bal;
		
		companyAccount.setAmount(amount);
		companyAccountRepository.save(companyAccount);}
		catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public double paySalary(double amount) {
		try {
		CompanyAccount companyAccount=companyAccountRepository.findById(1).orElseThrow(()->new ResourceNotFoundException("SORRY FOR THE INCONVINIENCE.STAY PUT OUR TEAM IS WORKING ON IT."));
		double bal=companyAccount.getAmount();
		if(bal<amount) {
			throw new InsufficientBalanceException("Insufficient Funds");
		}else {
			bal-=amount;
			companyAccount.setAmount(bal);
			companyAccountRepository.save(companyAccount);
			return amount;
		}
		
		}catch(ResourceNotFoundException | InsufficientBalanceException e) {
			System.out.println(e.getMessage());
			return 0.0;
		}
	}

	public double checkBalance() throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		CompanyAccount companyAccount=companyAccountRepository.findById(1).orElseThrow(()->new ResourceNotFoundException("SORRY FOR THE INCONVINIENCE.STAY PUT OUR TEAM IS WORKING ON IT."));
		double balance=companyAccount.getAmount();
		return balance;
	}

}
