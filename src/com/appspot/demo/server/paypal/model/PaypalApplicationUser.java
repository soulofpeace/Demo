package com.appspot.demo.server.paypal.model;

import java.util.Date;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class PaypalApplicationUser {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String email;
	
	@Persistent
	private String userName;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private Role role;
	
	@Persistent
	private AppUserCustomer appUserCustomer;
	
	/**
	@Persistent
	private Set<Key> paypalCustomers;
	**/

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setAppUserCustomer(AppUserCustomer appUserCustomer) {
		this.appUserCustomer = appUserCustomer;
	}

	public AppUserCustomer getAppUserCustomer() {
		return appUserCustomer;
	}

	/**
	public void setPaypalCustomers(Set<Key> paypalCustomers) {
		this.paypalCustomers = paypalCustomers;
	}

	public Set<Key> getPaypalCustomers() {
		return paypalCustomers;
	}
	**/
	
	

}
