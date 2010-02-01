package com.appspot.demo.server.paypal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType= IdentityType.APPLICATION, detachable="true")
public class InvoiceTransaction {
	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private List<Key>transactions = new ArrayList<Key>();
	
	@Persistent
	private Date createdDate;
	
	@Persistent
	private Date modifiedDate;
	
	@Persistent (mappedBy="invoiceTransaction")
	private  Invoice invoice;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setTransactions(List<Key> transactions) {
		this.transactions = transactions;
	}

	public List<Key> getTransactions() {
		return transactions;
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

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Invoice getInvoice() {
		return invoice;
	}
	
	

}
