package com.springboot.carrental.model;


import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.SourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="car")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String model;
	
	private String brand;
	
	private String category;
	
	private String year;
	
	private String image;
	
	@Column(unique = true)
	private String licenseNumber;
	
	@Enumerated(EnumType.STRING)
	private CarStatus status;
	
	@Enumerated(EnumType.STRING)
	private SourceType source;
	
	private double dailyrate;
	
	@ManyToOne
	private Branch branch;
	
	@ManyToOne
	private Lender lender;
	@OneToOne
	private Carstats carStats;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}

	public SourceType getSource() {
		return source;
	}

	public void setSource(SourceType source) {
		this.source = source;
	}

	public double getDailyrate() {
		return dailyrate;
	}

	public void setDailyrate(double dailyrate) {
		this.dailyrate = dailyrate;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Lender getLender() {
		return lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Carstats getCarStats() {
		return carStats;
	}

	public void setCarStats(Carstats carStats) {
		this.carStats = carStats;
	}

	

	

}
