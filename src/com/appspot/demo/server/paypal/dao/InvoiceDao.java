package com.appspot.demo.server.paypal.dao;

import java.util.List;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.google.appengine.api.datastore.Key;

public interface InvoiceDao {
	public Invoice getInvoiceById(String id);
	public String saveInvoice(Invoice invoice);
	public void addTransactionToInvoice(Invoice invoice, PaypalTransaction transaction);
 	public Invoice getInvoiceByProfileId(String profileId);
 	public List<PaypalTransaction> getPaypalTransactions(Invoice invoice);
}
