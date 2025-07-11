package com.springboot.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.carrental.dto.RentalDetailsDto;
import com.springboot.carrental.dto.RentalDto;
import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.PaymentStatus;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.Payment;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.CarRepository;
import com.springboot.carrental.repository.CustomerRepository;
import com.springboot.carrental.repository.PaymentRepository;
import com.springboot.carrental.repository.RentalRepository;
import com.springboot.carrental.service.CarService;
import com.springboot.carrental.service.PaymentService;
import com.springboot.carrental.service.RentalService;

@SpringBootTest
public class RentalServiceTest {
	@InjectMocks
	private RentalService rentalService;
	@Mock
	private PaymentService paymentService;
	@Mock
	private RentalRepository rentalRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private CarRepository carRepository;
	@Mock
	private PaymentRepository paymentRepository;
	@Mock
	private CarService carService;

	
	private Car car;
	private ReservationLog reservation;
	private Rental rental;
	private Customer customer;
	private Payment payment;
	
	
	@BeforeEach
	public void init() {
		User user=new User();
		user.setId(1);
		user.setUsername("krishna@gmail.com");

		customer=new Customer();
		customer.setId(1);
		customer.setName("krishna");
		customer.setUser(user);
		
		car=new Car();
		car.setId(1);
		car.setBrand("NIssan");
		car.setStatus(CarStatus.BOOKED);
		
		reservation=new ReservationLog();
		reservation.setId(1);
		reservation.setCar(car);
		reservation.setCustomer(customer);
		reservation.setStatus(CarStatus.INBOOKING);
		reservation.setStartdate(LocalDate.of(2025, 06, 01));
		reservation.setEnddate(LocalDate.of(2025, 06, 05));
		
		rental=new Rental();
		rental.setId(1);
		rental.setReservation(reservation);
		rental.setStatus(PaymentStatus.SUCCESS);
		rental.setRentalcost(1000);
	
		
		payment=new Payment();
		payment.setId(1);
		payment.setRental(rental);
		payment.setPaymentstatus(PaymentStatus.SUCCESS);

	}
	
	@Test
	public void insertRental() throws InsufficientBalanceException, ResourceNotFoundException{
	    
	    when(carRepository.findById(1)).thenReturn(Optional.of(car));
	    when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
	    assertEquals(rental, rentalService.addNewRental(reservation));
	}
	@Test
	public void getByReservationTest() {
		when(rentalRepository.getByReservation(reservation)).thenReturn(rental);
		assertEquals(rental,rentalService.getByReservation(reservation));
	}
	@Test
	public void updateStatusTest() {
		Rental rental=new Rental();
		rental.setStatus(PaymentStatus.SUCCESS);
		when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
		rentalService.updateStatus(PaymentStatus.PENDING, rental);
		assertEquals(PaymentStatus.PENDING, rental.getStatus());
	}
	@Test
	public void rentaldetailsTestbyCustomerId() {
		when(rentalRepository.getRentalByCustomerId(1)).thenReturn(List.of(rental));
		List<RentalDto> dtos=new ArrayList<>();
		RentalDto dto=new RentalDto();
		dto.setRentalId(1);
		dto.setAmount(1000.00);
		dtos.add(dto);
		RentalDetailsDto result=rentalService.getByCustomerId(1);
		
		assertEquals(1000,result.getAmount());
		
	}
	
	@Test
	public void getByLoginTest() {
		Principal principal=mock(Principal.class);
		when(principal.getName()).thenReturn("krishna@gmail.com");
		when(rentalRepository.getByLogin("krishna@gmail.com")).thenReturn(List.of(rental));
		assertEquals(List.of(rental), rentalService.getByLogin(principal.getName()));
		
	}

}







