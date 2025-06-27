package com.springboot.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.carrental.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer>{

	@Query("select m from Manager m where m.user.id=?1")
	Manager getByLogin(int id);

}
