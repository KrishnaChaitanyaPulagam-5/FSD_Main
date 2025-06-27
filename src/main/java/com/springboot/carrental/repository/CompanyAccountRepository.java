package com.springboot.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.carrental.model.CompanyAccount;

public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, Integer>{

}
