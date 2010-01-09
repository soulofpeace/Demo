package com.appspot.demo.server.paypal.service.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.appspot.demo.server.paypal.service.exception.PaypalException;

public class PaypalCallerService {
	
	private PaypalCaller payPalCaller;
	
	private APICredential apiCredential;
	
	
	public void setAPICredential(APICredential apiCredential){
		this.setApiCredential(apiCredential);
	}


	public void setPayPalCaller(PaypalCaller payPalCaller) {
		this.payPalCaller = payPalCaller;
	}


	public PaypalCaller getPayPalCaller() {
		return payPalCaller;
	}


	public void setApiCredential(APICredential apiCredential) {
		this.apiCredential = apiCredential;
	}


	public APICredential getApiCredential() {
		return apiCredential;
	}
	
	public String call(String requestParam) throws PaypalException{
		this.payPalCaller.setupConnection(apiCredential);
		return this.payPalCaller.call(requestParam);
	}

}
