package com.springboot.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.ReservationLog;

public interface ReservationRepository extends JpaRepository<ReservationLog, Integer>{

	@Query("Select r from ReservationLog r where customer.id=?1")
	List<ReservationLog> getForCustomer(int customerId);

	@Query("select r from ReservationLog r where car.id=?1")
	ReservationLog getForCar(int carId);

	@Query("select r from ReservationLog r where customer.user.username=?1")
	List<ReservationLog> getByLogin(String username);

	@Query("select r from ReservationLog r where car.lender.user.username=?1")
	List<ReservationLog> getBylenderId(String username);

	@Query("select r.car as BookingCount from ReservationLog r GROUP BY r.car.id order by count(r.car) desc")
	List<Car> getTopCars();

	@Query("select r from ReservationLog r where customer.id=?1")
	List<ReservationLog> getByCustomerCars(int customerId);

	@Query("select r.car from ReservationLog r where r.customer.id=?1 group by r.car.id order by count(r.car) desc")
	List<Car> getByCustomertop5(int customerId);


}
