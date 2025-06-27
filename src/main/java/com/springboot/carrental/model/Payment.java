package com.springboot.carrental.model;

import java.time.LocalDate;

import com.springboot.carrental.enums.PaymentMode;
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
@Table(name="payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Rental rental;
	
	private LocalDate paymentdate;
	
	private double amount;
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentmode;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentstatus;
	
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rental getRental() {
		return rental;
	}

	public void setRental(Rental rental) {
		this.rental = rental;
	}

	public LocalDate getPaymentdate() {
		return paymentdate;
	}

	public void setPaymentdate(LocalDate localDate) {
		this.paymentdate = localDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PaymentMode getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(PaymentMode paymentmode) {
		this.paymentmode = paymentmode;
	}

	public PaymentStatus getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(PaymentStatus paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	



}
