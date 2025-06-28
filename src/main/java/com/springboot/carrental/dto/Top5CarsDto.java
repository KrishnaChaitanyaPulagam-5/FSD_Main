package com.springboot.carrental.dto;

import java.util.List;

public class Top5CarsDto {

	public List<String> cars;
	
	public List<Integer> bookings;

	public List<String> getCars() {
		return cars;
	}

	public void setCars(List<String> cars) {
		this.cars = cars;
	}

	public List<Integer> getBookings() {
		return bookings;
	}

	public void setBookings(List<Integer> bookings) {
		this.bookings = bookings;
	}
	
	
}
