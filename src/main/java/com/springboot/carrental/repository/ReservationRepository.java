package com.springboot.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.ReservationLog;

public interface ReservationRepository extends JpaRepository<ReservationLog, Integer>{

	@Query("Select r from ReservationLog r where customer.id=?1")
	List<ReservationLog> getForCustomer(int customerId);

	@Query("select r from ReservationLog r where car.id=?1")
	ReservationLog getForCar(int carId);

	@Query("select r from ReservationLog r where customer.user.username=?1")
	List<ReservationLog> getByLogin(String username);

	@Query("select r from ReservationLog r where car.lender.id=?1")
	List<ReservationLog> getBylenderId(int lenderId);



}
