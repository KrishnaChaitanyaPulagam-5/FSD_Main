package com.springboot.carrental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.CustomerAccount;
import com.springboot.carrental.model.Lender;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.LenderRepository;
@Service
public class LenderService {
	
	private LenderRepository lenderRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerAccountService cas;
	

	public LenderService(LenderRepository lenderRepository) {
		super();
		this.lenderRepository = lenderRepository;
	}


	public Lender insertLender(Lender lender) {
		// TODO Auto-generated method stub
		User user=lender.getUser();
		user.setRole("LENDER");
		userService.registerUser(user);
		lender.setUser(user);
		CustomerAccount acc=lender.getCustomerAccount();
		cas.addAccount(acc);
		lender.setCustomerAccount(acc);
		return lenderRepository.save(lender);
	}


	public List<Lender> getall() {
		// TODO Auto-generated method stub
		return lenderRepository.findAll();
	}


	public Lender getByID(int lenderId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return lenderRepository.findById(lenderId).orElseThrow(()->new ResourceNotFoundException("Lender not found"));
	}


	public Lender getByLogin(String name) {
		// TODO Auto-generated method stub
		return lenderRepository.getByLogin(name);
	}

}
