package com.springboot.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;

public interface RentalRepository extends JpaRepository<Rental, Integer>{

	@Query("select r from Rental r where reservation=?1")
	Rental getByReservation(ReservationLog reservation);

	@Query("SELECT r FROM Rental r WHERE r.reservation.customer.user.username = ?1 AND (r.reservation.status = 'BOOKED' OR r.reservation.status='COMPLETED')")
	List<Rental> getByLogin(String principal);


	@Query("select r from Rental r where id=?1")
	Rental getByRentalID(int rentalId);

	@Query("select r.reservation from Rental r where r.id=?1")
	ReservationLog getByReservationByRentalId(int id);

	@Query("select r from Rental r where r.reservation.customer.id=?1")
	List<Rental> getRentalByCustomerId(int id);

	@Query("SELECT r FROM Rental r WHERE r.reservation.customer.user.username = ?1 AND r.reservation.status = 'BOOKED'")
	List<Rental> getByLoginBooked(String name);

}
