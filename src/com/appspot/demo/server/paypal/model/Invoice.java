package com.appspot.demo.server.paypal.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class Invoice {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;
	
	@Persistent
	private Key customerId;
	
	@Persistent
	private Key productPackageId;
	
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

	public void setCustomerId(Key customerId) {
		this.customerId = customerId;
	}

	public Key getCustomerId() {
		return customerId;
	}

	public void setProductPackageId(Key productPackageId) {
		this.productPackageId = productPackageId;
	}

	public Key getProductPackageId() {
		return this.productPackageId;
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
