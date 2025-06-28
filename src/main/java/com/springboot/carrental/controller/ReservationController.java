package com.springboot.carrental.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.dto.CarStatsDto;
import com.springboot.carrental.dto.CustomerBookingDto;
import com.springboot.carrental.dto.Top5CarsDto;
import com.springboot.carrental.exception.CarNotAvailableException;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.service.CustomerService;
import com.springboot.carrental.service.PaycheckService;
import com.springboot.carrental.service.RentalService;
import com.springboot.carrental.service.ReservationService;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private RentalService rentalService;
	@Autowired
	private PaycheckService paycheckService;
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add/{customerId}/{carId}")
	public ResponseEntity<Map<String, Integer>> registerNewReservation(@PathVariable int customerId,@PathVariable int carId,@RequestBody ReservationLog reservation) 
			throws InsufficientBalanceException, CarNotAvailableException, ResourceNotFoundException{	
		reservationService.registerNewReservation(customerId,carId,reservation);
		Rental rental=rentalService.getByReservation(reservation);
		Map<String, Integer> rental1=new HashMap<>();
		rental1.put("rental",rental.getId());
		paycheckService.addNewPaycheck(rental.getId());
		return ResponseEntity.ok(rental1);
	}
	
	@GetMapping("/getall")
	public List<ReservationLog> getall(){
		return reservationService.getall();
	}
	
	@GetMapping("/getForCustomer/{customerId}")
	public List<ReservationLog> getForCustomer(@PathVariable int customerId){
		return reservationService.getForCustomer(customerId);
	}
	
	@GetMapping("/getById/{Id}")
	public ReservationLog getById(@PathVariable int id) {
		return reservationService.getById(id);
	}
	
	@GetMapping("/getByLogin")
	public List<ReservationLog> getByLogin(Principal principal){
		return reservationService.getByLogin(principal);
	}
	
	@GetMapping("/getCarStats")
	public CarStatsDto getGlobalCarStats(CarStatsDto dto) { 
		return reservationService.getGlobalCarStats(dto);
	}
	
	@GetMapping("/getByLenderId")
	public List<ReservationLog> getByLenderId(Principal principal){
		return reservationService.getByLenderId(principal.getName());
	}
	
	@GetMapping("/getTopCars")
	public Top5CarsDto getTopCars(Top5CarsDto dto) { 
		return reservationService.getTopCars(dto);
	}
	
	@GetMapping("getTopCarsForCustomer")
	public CustomerBookingDto getTopCustomerCars(Principal principal,CustomerBookingDto dto) {
		String name=principal.getName();
		Customer customer=customerService.getByLogin(name);
		return reservationService.getTopCustomerCars(customer.getId(),dto);
	}
	
}
