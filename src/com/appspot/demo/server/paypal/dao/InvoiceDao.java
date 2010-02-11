package com.appspot.demo.server.paypal.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.google.appengine.api.datastore.Key;

public interface InvoiceDao {
	public Invoice getInvoiceById(String id);
	public String saveInvoice(Invoice invoice);
	//public String saveInvoice(PaypalCustomer customer, RecurringProductPackage productPackage, Invoice invoice, PaypalApplicationUser appUser);
	public Invoice getInvoiceByProfileId(String profileId);
	public Collection<Invoice> getInvoiceByCustomer(PaypalCustomer customer);
	public Collection<Invoice> getInvoiceByPackage(RecurringProductPackage productPackage);
	public Collection<Invoice> getInvoiceByAppUser(PaypalApplicationUser appUser);
	public Collection<Invoice> getAllInvoice();
	public Collection<Invoice> getInvoiceByDate(Date startDate, Date endDate);
	public Collection<Invoice> getInvoiceByDate(Date startDate, Date endDate, PaypalApplicationUser appUser);
	public Collection<Invoice> getInvoiceWithFailedTransaction();
	public Collection<Invoice> getInvoiceWithFailedTransaction(Date startDate, Date endDate);
	public Collection<Invoice> getInvoiceWithFailedTransaction(PaypalApplicationUser paypalApplicationUser);
	public Collection<Invoice> getInvoiceWithFailedTransaction(Date startDate, Date endDate, PaypalApplicationUser paypalApplicationUser);
}
