package com.springboot.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.PaymentStatus;
import com.springboot.carrental.model.Car;
import com.springboot.carrental.model.Rental;
import com.springboot.carrental.model.ReservationLog;
import com.springboot.carrental.service.CarService;
import com.springboot.carrental.service.RentalService;
import com.springboot.carrental.service.ReservationService;
import com.springboot.carrental.service.ReturnService;

@SpringBootTest
public class ReturnServiceTest {
	
	@InjectMocks
	private ReturnService returnService;
	@Mock
	private ReservationService reservationService;
	@Mock
	private RentalService rentalService;
	@Mock
	private CarService carService;
	
	private Rental rental;
	private ReservationLog reservation;
	private Car car;
	@BeforeEach
	public void init() {
		
		car=new Car();
		car.setBrand("Toyota");
		car.setDailyrate(900);
		
		reservation=new ReservationLog();
		reservation.setId(1);
		reservation.setStatus(CarStatus.BOOKED);
		reservation.setStartdate(LocalDate.of(2025, 6, 29));
		reservation.setEnddate(LocalDate.of(2025, 6, 30));
		
		rental=new Rental();
		rental.setId(1);
		rental.setReservation(reservation);
		rental.setStatus(PaymentStatus.SUCCESS);
		
		
	}
	@Test
	public void ReturnTest() {
		Rental rental=new Rental();
		rental.setEnd_date(reservation.getEnddate());
		rental.setReservation(reservation);
		rental.setRentalcost(1000);
		when(rentalService.getByRentalID(1)).thenReturn(rental);
		
		Rental result = returnService.returnCar(1);
		assertEquals(1000,result.getRentalcost());
	}

}




