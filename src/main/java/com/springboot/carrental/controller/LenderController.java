package com.springboot.carrental.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Lender;
import com.springboot.carrental.service.LenderService;

@RestController
@RequestMapping("/api/lender")
@CrossOrigin(origins = "http://localhost:5173")
public class LenderController {
	@Autowired
	private LenderService lenderService;
	
	@PostMapping("/add")
	public Lender insertLender(@RequestBody Lender lender) {
		return lenderService.insertLender(lender);
		
	}
	
	@GetMapping("/get_all")
	public List<Lender> getall(){
		return lenderService.getall();
		}
	
	
	@GetMapping("/getById/{lenderId}")
	public Lender getById(@PathVariable int lenderId) throws ResourceNotFoundException {
		return lenderService.getByID(lenderId);
	}
	
	@GetMapping("/getByLogin")
	public Lender getByLogin(Principal principal) {
		return lenderService.getByLogin(principal.getName());
	}
	

}
