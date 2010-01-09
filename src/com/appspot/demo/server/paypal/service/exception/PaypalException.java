package com.appspot.demo.server.paypal.service.exception;

public class PaypalException extends Exception{
	
	public PaypalException(){
		super();
	}
	
	public PaypalException(String message){
		super(message);
	}
	
	public PaypalException(String message, Throwable clause){
		super(message, clause);
	}
}
