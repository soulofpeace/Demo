package com.appspot.demo.server.paypal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class RecurringProductPackage {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String packageImageURL;
	
	@Persistent
	private String packageName;
	
	@Persistent 
	private String moreInformation;
	
	@Persistent
	private String packageDescription;
	
	@Persistent 
	private double packageCost;
	
	@Persistent
	private String billingPeriod;
	
	@Persistent 
	private int billingFrequency;
	
	//@Persistent
	//private List<Key> invoiceKeys = new ArrayList<Key>();
	
	@Persistent
	private Date getDateCreated;
	

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setPackageImageURL(String packageImageURL) {
		this.packageImageURL = packageImageURL;
	}

	public String getPackageImageURL() {
		return packageImageURL;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageDescription(String packageDescription) {
		this.packageDescription = packageDescription;
	}

	public String getPackageDescription() {
		return packageDescription;
	}

	public void setPackageCost(double packageCost) {
		this.packageCost = packageCost;
	}

	public double getPackageCost() {
		return packageCost;
	}

	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod=billingPeriod;
	}

	public String getBillingPeriod() {
		return this.billingPeriod;
	}
	
/**
	public List<Key> getInvoiceKeys(){
		return this.invoiceKeys;
	}
	
	public void setInvoiceKeys(List<Key> invoiceKeys){
		this.invoiceKeys =invoiceKeys;
	}
**/
	public void setBillingFrequency(int billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	public int getBillingFrequency() {
		return billingFrequency;
	}

	public void setMoreInformation(String moreInformation) {
		this.moreInformation = moreInformation;
	}

	public String getMoreInformation() {
		return moreInformation;
	}

	public void setGetDateCreated(Date getDateCreated) {
		this.getDateCreated = getDateCreated;
	}

	public Date getGetDateCreated() {
		return getDateCreated;
	}

	
	
	
}
