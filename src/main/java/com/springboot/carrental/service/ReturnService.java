package com.springboot.carrental.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;

@Service
public class ReturnService {
	
	private ReservationService reservationService;
	private RentalService rentalService;
	private CarService carService;
	Logger logger=LoggerFactory.getLogger("ReturnService");
	
	public ReturnService(ReservationService reservationService, RentalService rentalService, CarService carService) {
		super();
		this.reservationService = reservationService;
		this.rentalService = rentalService;
		this.carService = carService;
	}



	public Rental returnCar(int rentalId) {
		// TODO Auto-generated method stub
		logger.info("Return Process for Car is initiated with rental Id: {}",rentalId);
		Rental rental=rentalService.getByRentalID(rentalId);
		LocalDate curr_date=LocalDate.now();
		ReservationLog reservation=rental.getReservation();
		Car car=reservation.getCar();
		if (ChronoUnit.DAYS.between(rental.getEnd_date(),curr_date)<=0){
			reservationService.updateReservation(CarStatus.COMPLETED, reservation);
			carService.updateCarStatus(CarStatus.AVAILABLE, car);
		}else if(ChronoUnit.DAYS.between(rental.getEnd_date(),curr_date)>0) {
			long days=ChronoUnit.DAYS.between(rental.getEnd_date(),curr_date);
			double amount=car.getDailyrate()*days+car.getDailyrate()*days*0.15;
			rental.setLatefees(amount);
			reservationService.updateReservation(CarStatus.COMPLETED, reservation);
			rentalService.updateLateFees(rental.getLatefees(), rental);
		
		}
		logger.info("Car returned Successfully");
		return rental;
	}

}
