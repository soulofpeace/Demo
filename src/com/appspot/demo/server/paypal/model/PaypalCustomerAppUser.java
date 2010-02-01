package com.appspot.demo.server.paypal.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class PaypalCustomerAppUser {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Set<Key> paypalApplicationUser;
	
	@Persistent(mappedBy="paypalCustomerAppUser")
	private PaypalCustomer customer;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setPaypalApplicationUser(Set<Key> paypalApplicationUser) {
		this.paypalApplicationUser = paypalApplicationUser;
	}

	public Set<Key> getPaypalApplicationUser() {
		return paypalApplicationUser;
	}

	public void setCustomer(PaypalCustomer customer) {
		this.customer = customer;
	}

	public PaypalCustomer getCustomer() {
		return customer;
	}

}
