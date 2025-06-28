package com.springboot.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.PayCheck;

public interface PaycheckRepository extends JpaRepository<PayCheck, Integer> {

	@Query("select p from PayCheck p where p.rental.id=?1")
	PayCheck findByRentalID(int rentalId);

	@Query("select p from PayCheck p where p.lender.id=?1")
	List<PayCheck> getByLender(int id);

}
