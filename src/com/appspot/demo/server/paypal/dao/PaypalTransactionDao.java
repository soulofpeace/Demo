package com.appspot.demo.server.paypal.dao;

import com.appspot.demo.server.paypal.model.PaypalTransaction;

public interface PaypalTransactionDao {
	public PaypalTransaction getPaypalTransactionId(String id);
	public String savePaypalTransaction(PaypalTransaction paypalTransaction);
	
}
