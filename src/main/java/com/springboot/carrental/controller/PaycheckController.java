package com.springboot.carrental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.PayCheck;
import com.springboot.carrental.service.PaycheckService;

@RestController
@RequestMapping("/api/paycheck")
@CrossOrigin(origins = "http://localhost:5173")
public class PaycheckController {
	@Autowired
	private PaycheckService paycheckService;
	
	@PostMapping("/addNewPaycheck/{rentalId}")
	public PayCheck addNewPaycheck(@PathVariable int rentalId) {
		return paycheckService.addNewPaycheck(rentalId);
	}
	
	@PutMapping("/payToLender/{rentalId}")
	public PayCheck payToLender(@PathVariable int rentalId) throws ResourceNotFoundException, InsufficientBalanceException {
		return paycheckService.payToLender(rentalId);
	}
	@GetMapping("/getAll")
	public List<PayCheck> getAll(){
		return paycheckService.getAll();
	}
	@GetMapping("/getByRentalId/{rentalId}")
	public PayCheck getByRentalId(@PathVariable int rentalId) {
		return paycheckService.getByRentalId(rentalId);
	}

}
