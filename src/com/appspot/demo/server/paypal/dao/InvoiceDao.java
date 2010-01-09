package com.appspot.demo.server.paypal.dao;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;

public interface InvoiceDao {
	public Invoice getInvoiceById(String id);
	public String saveInvoice(PaypalCustomer customer, RecurringProductPackage productPackage, Invoice invoice);
}
