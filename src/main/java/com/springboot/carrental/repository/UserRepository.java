package com.springboot.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.User;


public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("Select u from User u where u.username=?1")
	User getByName(String username);


}
