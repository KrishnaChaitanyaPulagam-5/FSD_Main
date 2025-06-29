package com.springboot.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.PaymentStatus;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.CustomerAccount;
import com.springboot.carrental.model.Payment;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.PaymentRepository;
import com.springboot.carrental.service.CarService;
import com.springboot.carrental.service.CompanyAccountService;
import com.springboot.carrental.service.CustomerAccountService;
import com.springboot.carrental.service.PaymentService;
import com.springboot.carrental.service.RentalService;
import com.springboot.carrental.service.ReservationService;

@SpringBootTest
public class PaymentServiceTest {
	@InjectMocks
	private PaymentService paymentService;
	@Mock
	private CustomerAccountService cas;
	@Mock
	private CompanyAccountService coas;
	@Mock
	private RentalService rentalService;
	@Mock
	private ReservationService reservationService;
	@Mock
	private CarService carService;
	@Mock
	private PaymentRepository paymentRepository;
	
	private Customer customer;
	private Car car;
	private ReservationLog reservation;
	private Rental rental;
	private Payment payment;
	
	@BeforeEach
	public void init() {
		User user=new User();
		user.setId(1);
		user.setUsername("krishna@gmail.com");
		
		CustomerAccount account=new CustomerAccount();
		account.setAccountNumber("dsfaf452353");
		account.setId(1);
		account.setAmount(50000);

		customer=new Customer();
		customer.setId(1);
		customer.setName("krishna");
		customer.setUser(user);
		customer.setCustomerAccount(account);
		
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
		rental.setLatefees(0);
		rental.setRentalcost(1000);
	
		
		payment=new Payment();
		payment.setId(1);
		payment.setRental(rental);
		payment.setAmount(1000);
		payment.setPaymentstatus(PaymentStatus.SUCCESS);
	}
	@Test
	public void addPayment() throws InsufficientBalanceException, ResourceNotFoundException {
		when(rentalService.getByRentalID(1)).thenReturn(rental);
		when(rentalService.getReservationByRentalId(1)).thenReturn(reservation);
		when(cas.getAmount(customer)).thenReturn(50000.00);
		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
		
		Payment pay=paymentService.addPayment(1);
		assertEquals(1000, pay.getAmount());
		
	}
	@Test
	public void InsufficientBalanceTest() throws ResourceNotFoundException, InsufficientBalanceException {
		when(rentalService.getByRentalID(1)).thenReturn(rental);
		when(rentalService.getReservationByRentalId(1)).thenReturn(reservation);
		when(cas.getAmount(customer)).thenReturn(0.00);

		InsufficientBalanceException e=assertThrows(InsufficientBalanceException.class, ()->{paymentService.addPayment(1);});
		assertEquals("Insufficient Balance.",e.getMessage());
	}

}


