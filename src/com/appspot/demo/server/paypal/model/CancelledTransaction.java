package com.appspot.demo.server.paypal.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class CancelledTransaction {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Key paypalApplicationUser;
	
	@Persistent
	private String comment;
	
	@Persistent
	private Date dateCreated;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return this.id;
	}

	public void setPaypalApplicationUser(Key paypalApplicationUser) {
		this.paypalApplicationUser = paypalApplicationUser;
	}

	public Key getPaypalApplicationUser() {
		return paypalApplicationUser;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

}
