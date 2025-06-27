package com.springboot.carrental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.carrental.model.Carstats;
import com.springboot.carrental.repository.CarStatsRepository;

@Service
public class CarStatsService {
	
	@Autowired
	private CarStatsRepository carStatsRepository;
	
	public Carstats addCarStats(Carstats carStats) {
		return carStatsRepository.save(carStats);
	}

}
