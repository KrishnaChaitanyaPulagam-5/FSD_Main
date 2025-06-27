package com.springboot.carrental.exception;

public class CarNotAvailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CarNotAvailableException(String msg) {
		super(msg);
	}

}
