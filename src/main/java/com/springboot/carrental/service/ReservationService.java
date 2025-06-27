package com.springboot.carrental.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.carrental.dto.CarStatsDto;
import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.exception.CarNotAvailableException;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.repository.CarRepository;
import com.springboot.carrental.repository.CustomerRepository;
import com.springboot.carrental.repository.ReservationRepository;

@Service
public class ReservationService {

	private ReservationRepository reservationRepository;
	private CarRepository carRepository;
	private CustomerRepository customerRepository;
	private RentalService rentalService;
	@Autowired
	private CarService carService;

	public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository,
			CustomerRepository customerRepository, RentalService rentalService) {
		super();
		this.reservationRepository = reservationRepository;
		this.carRepository = carRepository;
		this.customerRepository = customerRepository;
		this.rentalService = rentalService;
	}

	public ReservationLog registerNewReservation(int customerId, int carId, ReservationLog reservation) throws InsufficientBalanceException, CarNotAvailableException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		Car car = carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException("Car Not Found"));
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));
		if (car.getStatus().equals(CarStatus.AVAILABLE)) {
			reservation.setCar(car);
			reservation.setCustomer(customer);
			reservation.setStatus(CarStatus.INBOOKING);
			carService.updateCarStatus(CarStatus.INBOOKING, car);
			ReservationLog savedReservation = reservationRepository.save(reservation);
			rentalService.addNewRental(savedReservation);


			return savedReservation;
		} else {
			throw new CarNotAvailableException("Car You are Seeking is already Booked");
		}

	}

	public List<ReservationLog> getall() {
		// TODO Auto-generated method stub
		return reservationRepository.findAll();
	}

	public List<ReservationLog> getForCustomer(int customerId) {
		// TODO Auto-generated method stub
		return reservationRepository.getForCustomer(customerId);
	}

	public ReservationLog getById(int id) {
		// TODO Auto-generated method stub
		return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}

	public ReservationLog getForCar(int carId) {
		return reservationRepository.getForCar(carId);
	}

	public ReservationLog updateReservation(CarStatus status, ReservationLog reservation) {
		reservation.setStatus(status);

		return reservationRepository.save(reservation);
	}

	public List<ReservationLog> getByLogin(Principal principal) {
		// TODO Auto-generated method stub
		String username=principal.getName();
		
		
		return reservationRepository.getByLogin(username);
	}

	
	public CarStatsDto getGlobalCarStats(CarStatsDto dto) {
		// TODO Auto-generated method stub
		List<Car> cars=carService.getAll();
		List<ReservationLog> list=getall();
//		List<String> courseTitles = new ArrayList<>();
//        List<Integer> enrolls = new ArrayList<>();
		List<String> carTitles=new ArrayList<>();
		List<Integer> reservations=new ArrayList<>();
//		courses.stream().forEach(c -> {
//            long num = list.stream().filter(lc -> lc.getCourse().getId() == c.getId()).count();
//            courseTitles.add(c.getTitle());
//            enrolls.add((int) num);
//        });
		cars.stream().forEach(c->{
		long num=list.stream().filter(l->l.getCar().getId() == c.getId()).count();
		carTitles.add(c.getBrand()+" "+c.getModel());
		reservations.add((int) num);
		});
		dto.setCars(carTitles);
		dto.setBookings(reservations);
		return dto;
	}

	public List<ReservationLog> getByLenderId(int lenderId) {
		// TODO Auto-generated method stub
		return reservationRepository.getBylenderId(lenderId);
	}
}
