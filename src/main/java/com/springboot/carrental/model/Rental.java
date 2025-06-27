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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Rental")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	private ReservationLog reservation;
	@ManyToOne
	private Branch branch_pickup;
	@ManyToOne
	private Branch branch_dropoff;
	
	private LocalDate start_date;
	
	private LocalDate end_date;
	
	private double rentalcost;
	
	private double latefees;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ReservationLog getReservation() {
		return reservation;
	}
	public void setReservation(ReservationLog reservation) {
		this.reservation = reservation;
	}
	public Branch getBranch_pickup() {
		return branch_pickup;
	}
	public void setBranch_pickup(Branch branch_pickup) {
		this.branch_pickup = branch_pickup;
	}
	public Branch getBranch_dropoff() {
		return branch_dropoff;
	}
	public void setBranch_dropoff(Branch branch_dropoff) {
		this.branch_dropoff = branch_dropoff;
	}
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public LocalDate getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	public double getRentalcost() {
		return rentalcost;
	}
	public void setRentalcost(double rentalcost) {
		this.rentalcost = rentalcost;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	public double getLatefees() {
		return latefees;
	}
	public void setLatefees(double latefees) {
		this.latefees = latefees;
	}

}
