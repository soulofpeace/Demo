package com.appspot.demo.server.paypal.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

public class Invoice {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
	
	@Persistent
	private PaypalCustomer customer;
	
	@Persistent
	private Package packageName;
	
	@Persistent
	private String paypalRecurringPaymentProfileId;
	
	@Persistent
	private Date createdDate;
	
	@Persistent
	private Date modifiedDate;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setCustomer(PaypalCustomer customer) {
		this.customer = customer;
	}

	public PaypalCustomer getCustomer() {
		return customer;
	}

	public void setPackageName(Package packageName) {
		this.packageName = packageName;
	}

	public Package getPackageName() {
		return packageName;
	}

	public void setPaypalRecurringPaymentProfileId(
			String paypalRecurringPaymentProfileId) {
		this.paypalRecurringPaymentProfileId = paypalRecurringPaymentProfileId;
	}

	public String getPaypalRecurringPaymentProfileId() {
		return paypalRecurringPaymentProfileId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

}
