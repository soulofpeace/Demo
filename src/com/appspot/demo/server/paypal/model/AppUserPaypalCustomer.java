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

@PersistenceCapable(identityType= IdentityType.APPLICATION, detachable="true")
public class AppUserPaypalCustomer {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Set<Key> paypalCustomers;
	
	@Persistent(mappedBy="appUserPaypalCustomer")
	private PaypalApplicationUser appUser ;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setPaypalCustomers(Set<Key> paypalCustomers) {
		this.paypalCustomers = paypalCustomers;
	}

	public Set<Key> getPaypalCustomers() {
		return paypalCustomers;
	}

	public void setAppUser(PaypalApplicationUser appUser) {
		this.appUser = appUser;
	}

	public PaypalApplicationUser getAppUser() {
		return appUser;
	}
	

}
