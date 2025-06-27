package com.springboot.carrental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "car_stats")
public class Carstats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int horsepower;
    private int torque;
    private double mileage;
    private double topSpeed;
    private double acceleration;
    private String engineType;
    private double engineDisplacement;
    private String fuelType;
    private String transmission;
    private int seatingCapacity;
    private int doors;
    private double weight;
    private String drivetrain;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHorsepower() {
		return horsepower;
	}
	public void setHorsepower(int horsepower) {
		this.horsepower = horsepower;
	}
	public int getTorque() {
		return torque;
	}
	public void setTorque(int torque) {
		this.torque = torque;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public double getTopSpeed() {
		return topSpeed;
	}
	public void setTopSpeed(double topSpeed) {
		this.topSpeed = topSpeed;
	}
	public double getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public double getEngineDisplacement() {
		return engineDisplacement;
	}
	public void setEngineDisplacement(double engineDisplacement) {
		this.engineDisplacement = engineDisplacement;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public int getSeatingCapacity() {
		return seatingCapacity;
	}
	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}
	public int getDoors() {
		return doors;
	}
	public void setDoors(int doors) {
		this.doors = doors;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getDrivetrain() {
		return drivetrain;
	}
	public void setDrivetrain(String drivetrain) {
		this.drivetrain = drivetrain;
	}

    
    
}
