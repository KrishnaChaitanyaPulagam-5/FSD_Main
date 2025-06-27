package com.springboot.carrental.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.dto.RentalDetailsDto;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.service.PaycheckService;
import com.springboot.carrental.service.RentalService;


@RestController
@RequestMapping("/api/rental")
@CrossOrigin(origins = "http://localhost:5173")
public class RentalController {
	
	@Autowired
	private RentalService rentalService;
		
	@PostMapping("/add")
	public Rental addNewRental(ReservationLog reservation) throws InsufficientBalanceException, ResourceNotFoundException {
		Rental rental=rentalService.addNewRental(reservation);
		return rental;
	}
	@GetMapping("/getByLogin")
	public List<Rental> getByLogin(Principal principal) {
		return rentalService.getByLogin(principal.getName());
	}
	
	@GetMapping("/getByLoginBooked")
	public List<Rental> getByLoginBooked(Principal principal) {
		return rentalService.getByLoginBooked(principal.getName());
	}

	@GetMapping("/getByRentalId/{rentalId}")
	public Rental getByRentalId(@PathVariable int rentalId) {
		return rentalService.getByRentalID(rentalId);
	}

	@GetMapping("/getByCustomerId/{customerId}")
	public RentalDetailsDto getByCustomerId(@PathVariable int customerId) {
		return rentalService.getByCustomerId(customerId);
	}
	
}
