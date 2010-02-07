package com.appspot.demo.server.paypal.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class AppUserCustomer {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent(mappedBy="appUserCustomer")
	private PaypalApplicationUser appUser;
	
	@Persistent
	private Set<Key> customers;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	

	public void setCustomers(Set<Key> customers) {
		this.customers= customers;
	}

	public Set<Key> getCustomers() {
		return customers;
	}

	public void setAppUser(PaypalApplicationUser appUser) {
		this.appUser = appUser;
	}

	public PaypalApplicationUser getAppUser() {
		return appUser;
	}
	
	
}
