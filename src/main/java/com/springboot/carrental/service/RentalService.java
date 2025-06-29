package com.springboot.carrental.service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.springboot.carrental.dto.RentalDetailsDto;
import com.springboot.carrental.dto.RentalDto;
import com.springboot.carrental.enums.PaymentStatus;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.repository.RentalRepository;
@Service
public class RentalService {

	private RentalRepository rentalRepository;
	
	

	public RentalService(RentalRepository rentalRepository) {
		super();
		this.rentalRepository = rentalRepository;
	}


	public Rental addNewRental(ReservationLog reservation) {
		// TODO Auto-generated method stub
		Rental rental=new Rental();
		rental.setReservation(reservation);
		rental.setStart_date(reservation.getStartdate());
		rental.setEnd_date(reservation.getEnddate());
		rental.setBranch_pickup(reservation.getCar().getBranch());
		rental.setBranch_dropoff(reservation.getCar().getBranch());
		rental.setRentalcost((reservation.getCar().getDailyrate())*
				ChronoUnit.DAYS.between(reservation.getStartdate(), reservation.getEnddate()));
		rental.setStatus(PaymentStatus.PENDING);
		rental.setLatefees(0);
		rental=rentalRepository.save(rental);
		
		
		return rental;
		
	}
	
	
	public Rental getByReservation(ReservationLog reservation) {
		return rentalRepository.getByReservation(reservation);
	}
	
	public void updateStatus(PaymentStatus status,Rental rental) {
		rental.setStatus(status);
		rentalRepository.save(rental);
	}

	public void updateLateFees(double cost,Rental rental) {
		rental.setLatefees(cost);
		rentalRepository.save(rental);
	}

	public List<Rental> getByLogin(String principal) {
		// TODO Auto-generated method stub
		return rentalRepository.getByLogin(principal);
	}


	public Rental getByRentalID(int rentalId) {
		// TODO Auto-generated method stub
		return rentalRepository.getByRentalID(rentalId);
	}
	
	public ReservationLog getReservationByRentalId(int id) {
		return rentalRepository.getByReservationByRentalId(id);
	}

	public List<Rental> getRentalByCustomerId(int id) {
		return rentalRepository.getRentalByCustomerId(id);
	}

	public RentalDetailsDto getByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		List<Rental> rentals=getRentalByCustomerId(customerId);
		List<RentalDto> dtos=new ArrayList<>();
		double totalspent=0;
		for(Rental rental:rentals) {
			
			RentalDto dto=new RentalDto();
			dto.setRentalId(rental.getId());
			dto.setStart_date(rental.getStart_date());
			dto.setEnd_date(rental.getEnd_date());
			dto.setCarName(rental.getReservation().getCar().getBrand());
			dto.setImage(rental.getReservation().getCar().getImage());
			dto.setAmount(rental.getRentalcost());
			totalspent+=rental.getRentalcost();
			dtos.add(dto);
		}
		
		return new RentalDetailsDto(dtos,totalspent);
	}


	public List<Rental> getByLoginBooked(String name) {
		// TODO Auto-generated method stub
		return rentalRepository.getByLoginBooked(name);
	}


	public List<Rental> getByLenderId(String name) {
		// TODO Auto-generated method stub
		return rentalRepository.getByLenderId(name);
	}

}
