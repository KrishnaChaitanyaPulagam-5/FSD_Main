package com.springboot.carrental.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.carrental.enums.PaymentStatus;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.Lender;
import com.springboot.carrental.model.PayCheck;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.repository.PaycheckRepository;
@Service
public class PaycheckService {
	
	private PaycheckRepository paycheckRepository;
	private RentalService rentalService;
	private CompanyAccountService cas;
	@Autowired
	private CustomerAccountService cuas;

	public PaycheckService(PaycheckRepository paycheckRepository, RentalService rentalService,
			CompanyAccountService cas) {
		super();
		this.paycheckRepository = paycheckRepository;
		this.rentalService = rentalService;
		this.cas = cas;
	}


	public PayCheck addNewPaycheck(int rentalId) {
		// TODO Auto-generated method stub
		Rental rental=rentalService.getByRentalID(rentalId);
		PayCheck paycheck=new PayCheck();
		paycheck.setLender(rental.getReservation().getCar().getLender());
		paycheck.setCar(rental.getReservation().getCar());
		paycheck.setPaymentstatus(PaymentStatus.PENDING);
		paycheck.setAmount(rental.getRentalcost()*0.90);
		paycheck.setRental(rental);
		return paycheckRepository.save(paycheck);
	}
	
	
	public PayCheck payToLender(int rentalId) throws ResourceNotFoundException,InsufficientBalanceException {
		// TODO Auto-generated method stub
		Rental rental=rentalService.getByRentalID(rentalId);
		Lender lender=rental.getReservation().getCar().getLender();
		PayCheck paycheck=getByRentalId(rentalId);
		double companybalance=cas.checkBalance();
		double amount=rental.getRentalcost()*0.90;
		if (lender == null) {
			paycheck.setPaymentdate(LocalDate.now());
			paycheck.setPaymentstatus(PaymentStatus.SUCCESS);
		}
		else {
		if(amount<=companybalance) {
			double balance=lender.getCustomerAccount().getAmount();
			double newbalance=balance+amount;
			lender.getCustomerAccount().setAmount(newbalance);
			cuas.updateLenderAmount(lender, amount);
			cas.paySalary(amount);
			paycheck.setAmount(amount);
			paycheck.setPaymentdate(LocalDate.now());
			paycheck.setPaymentstatus(PaymentStatus.SUCCESS);
		}else {
			throw new InsufficientBalanceException("Comapany Funds Insufficient");
		}}
		
		
		
		return paycheckRepository.save(paycheck);
	}

	public PayCheck getByRentalId(int rentalId) {
		PayCheck paycheck=paycheckRepository.findByRentalID(rentalId);
		return paycheck;
	}


	public List<PayCheck> getAll() {
		// TODO Auto-generated method stub
		return paycheckRepository.findAll();
	}

	

}
