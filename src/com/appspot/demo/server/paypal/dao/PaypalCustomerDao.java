package com.appspot.demo.server.paypal.dao;

import java.util.List;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.google.appengine.api.datastore.Key;

public interface PaypalCustomerDao {
	public PaypalCustomer getPaypalCustomerById(String id);
	public String savePaypalCustomer(PaypalCustomer paypalCustomer);
	public PaypalCustomer getPaypalCustomerByPaypalId(String paypalId);
	public void addInvoice(PaypalCustomer paypalCustomer, Invoice invoice);
	public void addAppUser(PaypalCustomer paypalCustomer, PaypalApplicationUser appUser);
	public List<Invoice> getInvoices(PaypalCustomer paypalCustomer);
	public List<PaypalApplicationUser> getApplicationUser(PaypalCustomer paypalCustomer);
}
