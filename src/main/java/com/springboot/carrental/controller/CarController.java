package com.springboot.carrental.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.carrental.exception.BadRequestException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.service.CarService;

@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins="http://localhost:5173")
public class CarController {
	@Autowired
	private CarService carService;
	
	
	@PostMapping("/add/{lenderId}/{branchId}")
	public Car insertCar(@PathVariable int lenderId,@PathVariable int branchId  ,@RequestBody Car car) throws ResourceNotFoundException, BadRequestException {
		return carService.insertCar(lenderId,branchId,car);
	}
	
	@GetMapping("/getall")
	public List<Car> getAll() {
		return carService.getAll();
	}
	
	@GetMapping("/getAllAvailable")
	public List<Car> getAllAvaialble(){
		return carService.getAllAvailable();
	}
	
	@GetMapping("/searchByCategory/{category}")
	public List<Car> searchByCategory(@PathVariable String category){
		
		return carService.searchByCategory(category);
	}

	@GetMapping("/searchByBranch/{branchName}")
	public List<Car> searchByBranch(@PathVariable String branchName){
		return carService.searchByBranch(branchName);
	}
	
	@GetMapping("/searchByBrand/{brandName}")
	public List<Car> searchByBrand(@PathVariable String brandName){
		return carService.searchByBrand(brandName);
	}
	
	
	@GetMapping("/searchByKeyword/{keyword}")
	public List<Car> searchByKeyword(@PathVariable String keyword){
		return carService.searchByKeyword(keyword);
	}
	
	
	@PutMapping("/update/{id}")
	public Car updateCar(@PathVariable int id,@RequestBody Car car) {
		return carService.updateCar(id,car);
		
	}
	@PutMapping("/updateByManager/{carId}")
	public Car updateCarStatusByManager(@PathVariable int carId,@RequestParam String status) throws ResourceNotFoundException {
		return carService.updateCarStatusByManager(carId,status);
	}
	
	@GetMapping("/stats/{carId}")
	public Car getStats(@PathVariable int carId) throws ResourceNotFoundException {
		return carService.getStats(carId);
	}
	
	@GetMapping("/getCarsByLender")
	public List<Car> getByLender(Principal principal){ 
		return carService.getByLender(principal);
	}
	
}
