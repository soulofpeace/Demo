package com.appspot.demo.server.paypal.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class Package {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String packageImageURL;
	
	@Persistent
	private String packageName;
	
	@Persistent
	private String packageDescription;
	
	@Persistent 
	private double packageCost;
	
	@Persistent
	private int type;
	
	@Persistent(mappedBy="package")
	@Element(dependent="true")
	private List payPalCustomers = new ArrayList();

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

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setPayPalCustomers(List payPalCustomers) {
		this.payPalCustomers = payPalCustomers;
	}

	public List getPayPalCustomers() {
		return payPalCustomers;
	}
	
	
}
