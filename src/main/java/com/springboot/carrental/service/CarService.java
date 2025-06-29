package com.springboot.carrental.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.SourceType;
import com.springboot.carrental.exception.BadRequestException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Branch;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Carstats;
import com.springboot.carrental.model.Lender;
import com.springboot.carrental.repository.BranchRepository;
import com.springboot.carrental.repository.CarRepository;
import com.springboot.carrental.repository.LenderRepository;

@Service
public class CarService {
	
	private CarRepository carRepository;
	private LenderRepository lenderRepository;
	private BranchRepository branchRepository;
	private CarStatsService carStatsService;


	public CarService(CarRepository carRepository, LenderRepository lenderRepository, BranchRepository branchRepository,CarStatsService carStatsService) {
		super();
		this.carRepository = carRepository;
		this.lenderRepository = lenderRepository;
		this.branchRepository = branchRepository;
		this.carStatsService=carStatsService;
	}


	public Car insertCar(int lenderId, int branchId, Car car) throws ResourceNotFoundException, BadRequestException {
	    Branch branch = branchRepository.findById(branchId)
	        .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
	    car.setBranch(branch);

	    if (car.getSource() == SourceType.COMPANY) {
	        car.setLender(null);
	    } else if (car.getSource() == SourceType.LENDER) {
	        Lender lender = lenderRepository.findById(lenderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Lender not found"));
	        car.setLender(lender);
	    } else {
	        throw new BadRequestException("Invalid SourceType");
	    }
	    Carstats carstats=car.getCarStats();
	    Carstats savedCarStats=carStatsService.addCarStats(carstats);
	    car.setCarStats(savedCarStats);

	    return carRepository.save(car);
	}



	public List<Car> getAll() {
		// TODO Auto-generated method stub
		return carRepository.findAll();
	}


	public List<Car> getAllAvailable() {
		// TODO Auto-generated method stub
		return carRepository.getAllAvailable();
	}


	public List<Car> searchByCategory(String category) {
		// TODO Auto-generated method stub
		return carRepository.searchByCategory(category);
	}
	
	
	public List<Car> searchByBranch(String branchName){
		return carRepository.searchByBranch(branchName);
	}


	public List<Car> searchByBrand(String brandName) {
		// TODO Auto-generated method stub
		return carRepository.searchByBrand(brandName);
	}


	public List<Car> searchByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return carRepository.searchByKeyword(keyword);
	}


	public Car updateCar(int id, Car car) {
		// TODO Auto-generated method stub
		Car dbcar=carRepository.findById(id).orElseThrow(()->new RuntimeException());
		if(car.getModel()!=null) 
			dbcar.setModel(car.getModel());
		if(car.getStatus()!=null)
			dbcar.setStatus(car.getStatus());
		if(car.getDailyrate()!=0)
			dbcar.setDailyrate(car.getDailyrate());
		if(car.getImage()!=null)
			dbcar.setImage(car.getImage());

		
		return carRepository.save(dbcar);
		
	}


	public void updateCarStatus(CarStatus status,Car car) {
		// TODO Auto-generated method stub
		car.setStatus(status);
		carRepository.save(car);
	}


	public Car updateCarStatusByManager(int carId,String status) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Car car=carRepository.findById(carId).orElseThrow(()->new ResourceNotFoundException("Car Not found"));
		car.setStatus(CarStatus.valueOf(status));
		return carRepository.save(car);
	}


	public Car getStats(int carId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Car car=carRepository.findById(carId).orElseThrow(()->new ResourceNotFoundException("Car Not Found"));
		return car;
	}


	public List<Car> getByLender(Principal principal) {
		// TODO Auto-generated method stub
		return carRepository.getByLender(principal.getName());
	}



	

}
