package com.springboot.carrental;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.carrental.exception.BadRequestException;
import com.springboot.carrental.exception.CarNotAvailableException;
import com.springboot.carrental.exception.InsufficientBalanceException;
import com.springboot.carrental.exception.PaymentFailedException;
import com.springboot.carrental.exception.ResourceNotFoundException;



@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger=LoggerFactory.getLogger("GlobalExceptionHandler");
	
	@ExceptionHandler(exception = ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e){
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		logger.error(e.getMessage(),e.getClass());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		
	}
	@ExceptionHandler(exception = PaymentFailedException.class)
	public ResponseEntity<?> handlePaymentFailedException(PaymentFailedException e){
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		logger.error(e.getMessage(),e.getClass());
		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(map);
		
	}
	
	@ExceptionHandler(exception = InsufficientBalanceException.class)
	public ResponseEntity<?> handleInsufficientBalanceException(InsufficientBalanceException e){
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(map);
		
	}

	@ExceptionHandler(exception = BadRequestException.class)
	public ResponseEntity<?> handleBadRequestException(BadRequestException e){
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		logger.error(e.getMessage(), e.getClass());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		
	}
	@ExceptionHandler(exception = CarNotAvailableException.class)
	public ResponseEntity<?> handleCarNotAvailableException(CarNotAvailableException e){
		Map<String,String> map=new HashMap<>();
		map.put("msg", e.getMessage());
		logger.error(e.getMessage(),e.getClass());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
		
	}
	

}
