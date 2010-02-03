package com.appspot.demo.server.paypal.dao;

import java.util.Collection;
import java.util.List;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalTransaction;

public interface PaypalTransactionDao {
	public PaypalTransaction getPaypalTransactionId(String id);
	public String savePaypalTransaction(PaypalTransaction paypalTransaction);
	public Collection<PaypalTransaction> getTransactions(Invoice invoice);
	
}
