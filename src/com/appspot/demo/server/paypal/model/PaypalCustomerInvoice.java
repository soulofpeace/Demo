package com.appspot.demo.server.paypal.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class PaypalCustomerInvoice {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private List<Key> invoices = new ArrayList<Key>();
	
	@Persistent(mappedBy="paypalCustomerInvoice")
	private PaypalCustomer customer;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setInvoices(List<Key> invoices) {
		this.invoices = invoices;
	}

	public List<Key> getInvoices() {
		return invoices;
	}

	public void setCustomer(PaypalCustomer customer) {
		this.customer = customer;
	}

	public PaypalCustomer getCustomer() {
		return customer;
	}

}
