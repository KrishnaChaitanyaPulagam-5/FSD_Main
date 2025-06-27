package com.springboot.carrental.model;
import java.time.LocalDate;

import com.springboot.carrental.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Paycheck")
public class PayCheck {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Car car;
	@ManyToOne
	private Lender lender;
	@ManyToOne
	private Rental rental;
	
	private double amount;
	
	private LocalDate paymentdate;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentstatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Lender getLender() {
		return lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
	}

	public Rental getRental() {
		return rental;
	}

	public void setRental(Rental rental) {
		this.rental = rental;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getPaymentdate() {
		return paymentdate;
	}

	public void setPaymentdate(LocalDate paymentdate) {
		this.paymentdate = paymentdate;
	}

	public PaymentStatus getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(PaymentStatus paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	
	
	

}
