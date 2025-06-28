package com.springboot.carrental.dto;

import java.util.List;

public class CustomerBookingDto {
	
	private List<String> cars;
	
	private List<Integer> reservations;

	public List<String> getCars() {
		return cars;
	}

	public void setCars(List<String> carTitles) {
		this.cars = carTitles;
	}

	public List<Integer> getReservations() {
		return reservations;
	}

	public void setReservations(List<Integer> reservations) {
		this.reservations = reservations;
	}
	
	

}
