package com.springboot.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.PaymentStatus;
import com.springboot.carrental.exception.CarNotAvailableException;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.repository.CarRepository;
import com.springboot.carrental.repository.CustomerRepository;
import com.springboot.carrental.repository.ReservationRepository;
import com.springboot.carrental.service.RentalService;
import com.springboot.carrental.service.ReservationService;

@SpringBootTest
public class ReservationServiceTest {
	
	@InjectMocks
	private ReservationService reservationService;
	@Mock
	private ReservationRepository reservationRepository;
	@Mock
	private RentalService rentalService;
	@Mock
	private CarRepository carRepository;
	@Mock
	private CustomerRepository customerRepository;
	

	private ReservationLog reservation;
	private Car car;
	private Customer customer;
	private Rental rental;
	@BeforeEach
	public void init() {
		customer=new Customer();
		customer.setId(1);
		customer.setName("ash");
		customer.setEmail("ash@gmail.com");
		customer.setContactinfo("9876543210");
		customer.setAddress("guntur");
		
		
		car=new Car();
		car.setId(1);
		car.setModel("GTR R34 Nismo");
		car.setBrand("Nissan");
		car.setCategory("Coupe");
		car.setStatus(CarStatus.AVAILABLE);
		
		
		reservation=new ReservationLog();
		reservation.setId(1);
		reservation.setCustomer(customer);
		reservation.setCar(car);
		reservation.setStatus(CarStatus.INBOOKING);
		
		rental=new Rental();
		rental.setStatus(PaymentStatus.SUCCESS);
	}
	
	@Test
	public void addReservation() throws InsufficientBalanceException, CarNotAvailableException, ResourceNotFoundException {
		ReservationLog rl=new ReservationLog();
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
		when(carRepository.findById(1)).thenReturn(Optional.of(car));
		when(rentalService.getByReservation(any(ReservationLog.class))).thenReturn(rental);
		when(reservationRepository.save(rl)).thenReturn(reservation);
		
		assertEquals(reservation,reservationService.registerNewReservation(1, 1, rl));
		
		}
	@Test
	public void TestCar() {
		ReservationLog rl=new ReservationLog();
		car.setStatus(CarStatus.BOOKED);
		rl.setCar(car);
		rl.setCustomer(customer);
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
		when(carRepository.findById(1)).thenReturn(Optional.of(car));
		CarNotAvailableException e=assertThrows(CarNotAvailableException.class,()->reservationService.registerNewReservation(1, 1, rl));
		    assertEquals("Car You are Seeking is already Booked".toLowerCase(), e.getMessage().toLowerCase());
	}
	}







