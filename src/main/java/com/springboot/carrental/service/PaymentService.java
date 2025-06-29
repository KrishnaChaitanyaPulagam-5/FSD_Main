package com.springboot.carrental.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.PaymentMode;
import com.springboot.carrental.enums.PaymentStatus;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Customer;
import com.springboot.carrental.model.Payment;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.repository.PaymentRepository;
@Service
public class PaymentService {
	
	private PaymentRepository paymentRepository;
	private CustomerAccountService cas;
	private CompanyAccountService coas;
	private RentalService rentalService;
	private ReservationService reservationService;
	private CarService carService;
	Logger logger=LoggerFactory.getLogger("PaymentService");

	public PaymentService(PaymentRepository paymentRepository,
			CustomerAccountService cas,CompanyAccountService coas,RentalService rentalService,
			ReservationService reservationService,CarService carService) {
		super();
		this.paymentRepository = paymentRepository;
		this.cas = cas;
		this.coas=coas;
		this.rentalService=rentalService;
		this.reservationService=reservationService;
		this.carService=carService;
	}


	public Payment addPayment(int rentalId) throws InsufficientBalanceException, ResourceNotFoundException {
		// TODO Auto-generated method stub
		logger.info("Payment initiated for rentalId {}",rentalId);
		Rental rental=rentalService.getByRentalID(rentalId);
		ReservationLog reservation=rentalService.getReservationByRentalId(rentalId);
		Car car=reservation.getCar();
		Customer customer=rental.getReservation().getCustomer();
		Payment payment=new Payment();
		payment.setRental(rental);
		if(rental.getLatefees()==0) {
			logger.info("Paying the Rental fees");
		payment.setAmount(rental.getRentalcost());}
		else {
			logger.info("Paying the LateFees");
			payment.setAmount(rental.getLatefees());
		}
		payment.setPaymentdate(LocalDate.now());
		if(cas.getAmount(customer)>payment.getAmount()) {
			logger.info("Payment Success.Car with Id:{} is booked",car.getId());
			payment.setPaymentstatus(PaymentStatus.SUCCESS);
			cas.updateAmount(customer,payment.getAmount());
			coas.payToCompanyAccount(payment.getAmount());
			rentalService.updateStatus(PaymentStatus.SUCCESS, rental);
			if (rental.getLatefees() == 0) {
			    reservationService.updateReservation(CarStatus.BOOKED, reservation);
			    carService.updateCarStatus(CarStatus.BOOKED, car);
			} else {
			    reservationService.updateReservation(CarStatus.COMPLETED, reservation);
			    carService.updateCarStatus(CarStatus.AVAILABLE, car);
			}
		}else {
			logger.warn("Payment Failed");
			payment.setPaymentstatus(PaymentStatus.FAILED);
			rentalService.updateStatus(PaymentStatus.FAILED, rental);
			reservationService.updateReservation(CarStatus.CANCELLED,reservation);
			carService.updateCarStatus(CarStatus.AVAILABLE, car);
			logger.error("Customer {} doesnt have Sufficient Balance",customer.getName());
			throw new InsufficientBalanceException("Insufficient Balance.");
			
		}
		payment.setPaymentmode(PaymentMode.PAYPAL);
		
		
		
		return paymentRepository.save(payment);
	}


	public Payment getPayment(int id) {
		// TODO Auto-generated method stub
		return paymentRepository.getByRentalID(id);
	}

}
