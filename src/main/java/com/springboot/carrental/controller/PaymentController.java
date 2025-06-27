package com.springboot.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Payment;
import com.springboot.carrental.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/addPayment/{rentalId}")
	public Payment addPayment(@PathVariable int rentalId) throws InsufficientBalanceException, ResourceNotFoundException {
		return paymentService.addPayment(rentalId);
	}
	
	@GetMapping("/getByRentalId")
	public Payment getByRentalId(@RequestParam int id) {
		return paymentService.getPayment(id);
	}
	
}
