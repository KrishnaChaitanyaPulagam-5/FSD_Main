package com.springboot.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	@Query("select p from Payment p where p.rental.id=?1")
	Payment getByRentalID(int id);

}
