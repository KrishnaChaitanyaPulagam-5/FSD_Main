package com.springboot.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.Lender;

public interface LenderRepository extends JpaRepository<Lender, Integer>{

	@Query("select l from Lender l where l.user.username=?1")
	Lender getByLogin(String name);

}
