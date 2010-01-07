package com.appspot.demo.client.paypal.dao;

import com.appspot.demo.server.paypal.model.Invoice;
import com.google.appengine.api.datastore.Key;

public interface InvoiceDao {
	public Invoice getInvoiceById(Key id);
	public void saveInvoice(Invoice invoice);
}
