package com.springboot.carrental;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.springboot.carrental.enums.CarStatus;
import com.springboot.carrental.enums.SourceType;
import com.springboot.carrental.exception.BadRequestException;
import com.springboot.carrental.exception.ResourceNotFoundException;
import com.springboot.carrental.model.*;
import com.springboot.carrental.repository.*;
import com.springboot.carrental.service.CarService;
import com.springboot.carrental.service.CarStatsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;
    @Mock
    private LenderRepository lenderRepository;
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private CarStatsService carStatsService;

    private Car car;
    private Branch branch;
    private Lender lender;
    private Carstats carstats;

    @BeforeEach
    public void setup() {
        branch = new Branch();
        branch.setId(1);
        branch.setName("Chennai");

        lender = new Lender();
        lender.setId(1);
        lender.setName("John");

        carstats = new Carstats();
        carstats.setId(1);
        carstats.setHorsepower(300);
        carstats.setTopSpeed(220);

        car = new Car();
        car.setId(10);
        car.setModel("Tesla Model S");
        car.setStatus(CarStatus.AVAILABLE);
        car.setSource(SourceType.LENDER);
        car.setCarStats(carstats);
    }

    @Test
    public void insertCarSuccess() throws Exception {
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        when(lenderRepository.findById(1)).thenReturn(Optional.of(lender));
        when(carStatsService.addCarStats(any(Carstats.class))).thenReturn(carstats);
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car savedCar = carService.insertCar(1, 1, car);
        assertEquals(car, savedCar);
        assertEquals(branch, savedCar.getBranch());
        assertEquals(carstats, savedCar.getCarStats());
    }

    @Test
    public void insertCarInvalidBranch() {
    	
        when(branchRepository.findById(99)).thenReturn(Optional.empty());
        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            carService.insertCar(1, 99, car);
        });
        assertEquals("Branch not found".toLowerCase(), e.getMessage().toLowerCase());
    }

    @Test
    public void insertCarInvalidLender() {
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        when(lenderRepository.findById(99)).thenReturn(Optional.empty());

        car.setSource(SourceType.LENDER);

        ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            carService.insertCar(99, 1, car);
        });

        assertEquals("Lender not found".toLowerCase(), e.getMessage().toLowerCase());
    }

    @Test
    public void insertCarInvalidSource() {
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        car.setSource(null); // simulate invalid source

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            carService.insertCar(1, 1, car);
        });

        assertEquals("Invalid SourceType".toLowerCase(), e.getMessage().toLowerCase());
    }

    @Test
    public void updateCarStatusByManager_Success() throws Exception {
    	 when(carRepository.findById(10)).thenReturn(Optional.of(car));
    	    when(carRepository.save(any(Car.class))).thenReturn(car); // <--- THIS IS THE FIX

    	    Car updatedCar = carService.updateCarStatusByManager(10, "UNAVAILABLE");

    	    assertNotNull(updatedCar); // sanity check
    	    assertEquals(CarStatus.UNAVAILABLE, updatedCar.getStatus());
    }

    @Test
    public void getByLender_Success() {
        Principal mockPrincipal = () -> "john@example.com";
        when(carRepository.getByLender("john@example.com")).thenReturn(List.of(car));

        List<Car> result = carService.getByLender(mockPrincipal);
        assertEquals(1, result.size());
    }
}


