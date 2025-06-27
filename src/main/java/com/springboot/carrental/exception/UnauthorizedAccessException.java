package com.springboot.carrental.exception;

public class UnauthorizedAccessException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedAccessException(String msg) {
		super(msg);
	}

}
