package com.springboot.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.service.ReturnService;

@RestController
@RequestMapping("/api/return")
@CrossOrigin(origins = "http://localhost:5173")
public class ReturnController {
	
	@Autowired
	private ReturnService returnService;
	
	
	@PutMapping("/process/{rentalId}")
	public Object returnCar(@PathVariable int rentalId){
		return returnService.returnCar(rentalId);
		
	}

}
