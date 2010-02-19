package com.appspot.demo.server.paypal.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable ="true")
public class PaypalTransactionDateCreatedIndex {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private PaypalTransaction paypalTransaction;
	
	@Persistent
	private Date dateCreated;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setPaypalTransactionId(PaypalTransaction paypalTransaction) {
		this.paypalTransaction = paypalTransaction;
	}

	public PaypalTransaction getPaypalTransactionId() {
		return paypalTransaction;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
	
}
