package com.springboot.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.CustomerAccount;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Integer>{

	@Query("select amount from CustomerAccount c where id=?1")
	double getAmount(int id);

}
