package com.appspot.demo.server.paypal.dao;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.ProductPackage;

public interface InvoiceDao {
	public Invoice getInvoiceById(String id);
	public void saveInvoice(PaypalCustomer customer, ProductPackage productPackage, Invoice invoice);
}
