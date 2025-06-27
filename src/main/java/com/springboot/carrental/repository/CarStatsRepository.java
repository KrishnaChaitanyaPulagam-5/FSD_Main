package com.springboot.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.carrental.model.Carstats;

public interface CarStatsRepository extends JpaRepository<Carstats, Integer>{

}
