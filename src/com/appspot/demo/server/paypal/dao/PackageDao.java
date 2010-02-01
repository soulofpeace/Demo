package com.appspot.demo.server.paypal.dao;

import java.util.List;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;



public interface PackageDao {
	public RecurringProductPackage getPackageById(String id);
	public void addInvoice(RecurringProductPackage productPackage, Invoice invoice);
	public List<Invoice> getInvoices(RecurringProductPackage productPackage);
	public String savePackage(RecurringProductPackage productPackage);
	public List<RecurringProductPackage> getAllPackages();
}
