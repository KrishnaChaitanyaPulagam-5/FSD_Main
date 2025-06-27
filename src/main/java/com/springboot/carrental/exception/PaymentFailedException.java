package com.springboot.carrental.exception;

public class PaymentFailedException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PaymentFailedException(String msg) {
		super(msg);
	}

}
