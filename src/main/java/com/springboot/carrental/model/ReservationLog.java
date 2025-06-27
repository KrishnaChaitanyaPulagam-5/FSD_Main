package com.springboot.carrental.model;

import java.time.LocalDate;

import com.springboot.carrental.enums.CarStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="reservation_log")
public class ReservationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Car car;
	@ManyToOne
	private Customer customer;
	
	private LocalDate startdate;
	
	private LocalDate enddate;
	@Enumerated(EnumType.STRING)
	private CarStatus status;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public LocalDate getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}
	public LocalDate getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}
	public CarStatus getStatus() {
		return status;
	}
	public void setStatus(CarStatus status) {
		this.status = status;
	}
	



	
	
	
}
