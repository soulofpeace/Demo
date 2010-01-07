package com.appspot.demo.client.paypal.dao;

import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.google.appengine.api.datastore.Key;

public interface PaypalCustomerDao {
	public PaypalCustomer getPaypalCustomerById(Key id);
	public void savePaypalCustomer(PaypalCustomer paypalCustomer);
}
