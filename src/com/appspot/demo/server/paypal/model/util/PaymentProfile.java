package com.appspot.demo.server.paypal.model.util;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;

public class PaymentProfile {
	
	private Invoice invoice;
	
	private RecurringProductPackage productPackage;
	
	private PaypalApplicationUser appUser;
	
	private PaypalCustomer customers;

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setProductPackage(RecurringProductPackage productPackage) {
		this.productPackage = productPackage;
	}

	public RecurringProductPackage getProductPackage() {
		return productPackage;
	}

	public void setAppUser(PaypalApplicationUser appUser) {
		this.appUser = appUser;
	}

	public PaypalApplicationUser getAppUser() {
		return appUser;
	}

	public void setCustomers(PaypalCustomer customers) {
		this.customers = customers;
	}

	public PaypalCustomer getCustomers() {
		return customers;
	}

}
