package com.springboot.carrental.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private CustomerAccountService cuas;
	private LenderService lenderService;
	Logger logger=LoggerFactory.getLogger("PaycheckService");

	public PaycheckService(PaycheckRepository paycheckRepository, RentalService rentalService,
			CompanyAccountService cas,CustomerAccountService cuas,LenderService lenderService) {
		super();
		this.paycheckRepository = paycheckRepository;
		this.rentalService = rentalService;
		this.cas = cas;
		this.cuas=cuas;
		this.lenderService=lenderService;
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
		logger.info("New Paycheck Added fro RentalID:{}",rentalId);
		return paycheckRepository.save(paycheck);
	}
	
	
	public PayCheck payToLender(int rentalId) throws ResourceNotFoundException,InsufficientBalanceException {
		logger.info("Payment to Lender Initialted for rentalId: {}",rentalId);
		Rental rental=rentalService.getByRentalID(rentalId);
		Lender lender=rental.getReservation().getCar().getLender();
		PayCheck paycheck=getByRentalId(rentalId);
		double companybalance=cas.checkBalance();
		double amount=rental.getRentalcost()*0.90;
		if (lender == null) {
			logger.info("Company Car");
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
			logger.info("Payment Successful to the Lender: {}",lender.getName());
		}else {
			logger.error("Company Funds are Insufficient Available: {}",companybalance);
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


	public List<PayCheck> getByLender(String name) {
		// TODO Auto-generated method stub
		Lender lender=lenderService.getByLogin(name);
		logger.info("All paychecks for the Lender with ID: {}",lender.getId());
		return paycheckRepository.getByLender(lender.getId());
	}

	

}
