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
import com.springboot.carrental.model.CustomerAccount;
import com.springboot.carrental.model.Lender;
import com.springboot.carrental.model.PayCheck;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.repository.PaycheckRepository;
import com.springboot.carrental.service.CompanyAccountService;
import com.springboot.carrental.service.CustomerAccountService;
import com.springboot.carrental.service.LenderService;
import com.springboot.carrental.service.PaycheckService;
import com.springboot.carrental.service.RentalService;

@SpringBootTest
public class PaycheckServiceTest {
	@InjectMocks
	private PaycheckService paycheckService;
	@Mock
	private PaycheckRepository paycheckRepository;
	@Mock
	private RentalService rentalService;
	@Mock
	private CompanyAccountService cas;
	@Mock
	private CustomerAccountService cuas;
	@Mock
	private LenderService lenderService;
	
	private Rental rental;
	private ReservationLog reservation;
	private Car car;
	private Lender lender;
	@BeforeEach
	public void init() {
		CustomerAccount ca=new CustomerAccount();
		ca.setId(1);
		ca.setAmount(1000);
		
		
		lender=new Lender();
		lender.setId(1);
		lender.setCustomerAccount(ca);
		
		car=new Car();
		car.setId(1);
		car.setBrand("Toyota");
		car.setLender(lender);

		reservation=new ReservationLog();
		reservation.setId(1);
		reservation.setCar(car);
		reservation.setStartdate(LocalDate.of(2025, 8, 22));
		reservation.setEnddate(LocalDate.of(2025, 8, 31));
		reservation.setStatus(CarStatus.BOOKED);

		rental=new Rental();
		rental.setId(1);
		rental.setReservation(reservation);
		rental.setRentalcost(1000);
		rental.setStatus(PaymentStatus.SUCCESS);
	}
	@Test
	public void addNewPaycheckTest() {
		when(rentalService.getByRentalID(1)).thenReturn(rental);
		PayCheck paycheck=new PayCheck();
		paycheck.setRental(rental);
		paycheck.setLender(lender);
		paycheck.setAmount(rental.getRentalcost()*.90);
		when(paycheckRepository.save(any(PayCheck.class))).thenReturn(paycheck);
		assertEquals(900, paycheck.getAmount());
		assertEquals(paycheck, paycheckService.addNewPaycheck(1));
	}
	@Test
	public void payToLenderCheck() throws ResourceNotFoundException, InsufficientBalanceException {
		when(rentalService.getByRentalID(1)).thenReturn(rental);
		PayCheck paycheck=new PayCheck();
		paycheck.setRental(rental);
		paycheck.setLender(lender);
		paycheck.setAmount(10000);
		when(paycheckRepository.findByRentalID(1)).thenReturn(paycheck);
		when(cas.checkBalance()).thenReturn(500000.00);
		when(paycheckRepository.save(paycheck)).thenReturn(paycheck);
		assertEquals(paycheck, paycheckService.payToLender(1));
		
	}
	
	@Test
	public void payToLenderCheckFailed() throws ResourceNotFoundException, InsufficientBalanceException {
		when(rentalService.getByRentalID(1)).thenReturn(rental);
		PayCheck paycheck=new PayCheck();
		paycheck.setRental(rental);
		paycheck.setLender(lender);
		paycheck.setAmount(10000);
		when(paycheckRepository.findByRentalID(1)).thenReturn(paycheck);
		when(cas.checkBalance()).thenReturn(0.00);
		InsufficientBalanceException e=assertThrows(InsufficientBalanceException.class,()->paycheckService.payToLender(1));
		assertEquals("Comapany Funds Insufficient", e.getMessage());
	}

}





