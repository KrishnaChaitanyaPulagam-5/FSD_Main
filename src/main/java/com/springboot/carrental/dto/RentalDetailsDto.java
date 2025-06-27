package com.springboot.carrental.dto;

import java.util.List;

public class RentalDetailsDto {

	private List<RentalDto> rentals;
	
	private double amount;
	

	public RentalDetailsDto(List<RentalDto> rentals, double amount) {
		super();
		this.rentals = rentals;
		this.amount = amount;
	}

	public List<RentalDto> getRentals() {
		return rentals;
	}

	public void setRentals(List<RentalDto> rentals) {
		this.rentals = rentals;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
