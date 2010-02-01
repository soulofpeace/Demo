package com.appspot.demo.server.paypal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class PackageInvoice {
	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Date createdDate;
	
	@Persistent 
	private Date modifiedDate;
	
	@Persistent(mappedBy="packageInvoice")
	private RecurringProductPackage productPackage;
	
	@Persistent
	private List<Key> invoices = new ArrayList<Key>();

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
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

	public void setProductPackage(RecurringProductPackage productPackage) {
		this.productPackage = productPackage;
	}

	public RecurringProductPackage getProductPackage() {
		return productPackage;
	}

	public void setInvoices(List<Key> invoices) {
		this.invoices = invoices;
	}

	public List<Key> getInvoices() {
		return invoices;
	}
	
	
	
}
