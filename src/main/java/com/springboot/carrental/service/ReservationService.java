package com.springboot.carrental.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springboot.carrental.dto.CarStatsDto;
import com.springboot.carrental.dto.CustomerBookingDto;
import com.springboot.carrental.dto.Top5CarsDto;
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
	private CarService carService;
	
	Logger logger=LoggerFactory.getLogger("ReservationService");

	public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository,
			CustomerRepository customerRepository, RentalService rentalService,CarService carService) {
		super();
		this.reservationRepository = reservationRepository;
		this.carRepository = carRepository;
		this.customerRepository = customerRepository;
		this.rentalService = rentalService;
		this.carService=carService;
	}

	public ReservationLog registerNewReservation(int customerId, int carId, ReservationLog reservation) throws InsufficientBalanceException, CarNotAvailableException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		logger.info("Attempting to register reservation: customerId={}, carId={}", customerId, carId);
		Car car = carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException("Car Not Found"));
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));
		if (car.getStatus().equals(CarStatus.AVAILABLE)) {
			reservation.setCar(car);
			reservation.setCustomer(customer);
			reservation.setStatus(CarStatus.INBOOKING);
			carService.updateCarStatus(CarStatus.INBOOKING, car);
			ReservationLog savedReservation = reservationRepository.save(reservation);
			logger.info("Reservation saved with ID: {}", savedReservation.getId());
			rentalService.addNewRental(savedReservation);


			return savedReservation;
		} else {
			logger.warn("Car you are seeking is already booked : carID={}",carId);
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
		return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid Id.check again"));
	}

	public ReservationLog getForCar(int carId) {
		return reservationRepository.getForCar(carId);
	}

	public ReservationLog updateReservation(CarStatus status, ReservationLog reservation) {
		
		reservation.setStatus(status);
		logger.info("updated car {} status to {}",reservation.getCar().getId(),status);
		return reservationRepository.save(reservation);
	}

	public List<ReservationLog> getByLogin(Principal principal) {
		// TODO Auto-generated method stub
		String username=principal.getName();
		
		
		return reservationRepository.getByLogin(username);
	}

	
	public CarStatsDto getGlobalCarStats(CarStatsDto dto) {
		logger.info("Collecting Global Car Stats..");
		List<Car> cars=carService.getAll();
		List<ReservationLog> list=getall();
		List<String> carTitles=new ArrayList<>();
		List<Integer> reservations=new ArrayList<>();
		cars.stream().forEach(c->{
		long num=list.stream().filter(l->l.getCar().getId() == c.getId()).count();
		carTitles.add(c.getBrand()+" "+c.getModel());
		reservations.add((int) num);
		});
		dto.setCars(carTitles);
		dto.setBookings(reservations);
		return dto;
	}

	public List<ReservationLog> getByLenderId(String username) {
		// TODO Auto-generated method stub
		return reservationRepository.getBylenderId(username);
	}

	public Top5CarsDto getTopCars(Top5CarsDto dto) {
		// TODO Auto-generated method stub
		logger.info("Collecting Top Cars..");
		List<Car> TopCars=reservationRepository.getTopCars();
		List<ReservationLog> reservations=getall();
		List<String> carTitles = new ArrayList<>();
	    List<Integer> bookings = new ArrayList<>();
	    logger.info("filtering the top 5 cars");
	    TopCars.stream().limit(5).forEach(c->{
			long num=reservations.stream().filter(l->l.getCar().getId()==c.getId()).count();
			logger.info("Collected cars size:{}",carTitles.size());
			carTitles.add(c.getBrand()+" "+c.getModel());
			bookings.add((int) num);
		});

	    dto.setCars(carTitles);
	    dto.setBookings(bookings);
	    return dto;
	}

	public CustomerBookingDto getTopCustomerCars(int customerId, CustomerBookingDto dto) {
		logger.info("Collecting Top cars By Customer");
		List<ReservationLog> reservations=reservationRepository.getByCustomerCars(customerId);
		List<Car> cars=reservationRepository.getByCustomertop5(customerId);
		List<String> carTitles=new ArrayList<>();
		List<Integer> bookingCount=new ArrayList<>();
		cars.stream().limit(5).forEach(c->{
			long num=reservations.stream().filter(l->l.getCar().getId()==c.getId()).count();
			carTitles.add(c.getBrand()+" "+c.getModel());
			bookingCount.add((int) num);
		});
		dto.setCars(carTitles);
		dto.setReservations(bookingCount);
		
		return dto;
	}
	
	
}
