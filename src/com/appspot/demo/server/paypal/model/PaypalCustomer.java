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
public class PaypalCustomer {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private String payerId;
	
	@Persistent
	private String payerEmail;
	
	@Persistent
	private String payerStatus;
	
	@Persistent
	private String countryCode;
	
	@Persistent
	private String business;
	
	@Persistent
	private String salutation;
	
	@Persistent
	private String firstname;
	
	@Persistent
	private String middlename;
	
	@Persistent
	private String lastname;
	
	@Persistent
	private String suffix;
	
	@Persistent
	private String addressStatus;
	
	@Persistent
	private String shipToName;
	
	@Persistent
	private String shipToStreet1;
	
	@Persistent
	private String shipToStreet2;
	
	@Persistent
	private String shipToCity;
	
	@Persistent
	private String shipToState;
	
	@Persistent
	private String shipToZip;
	
	@Persistent
	private String shipToCountryCode;
	
	@Persistent(mappedBy = "customer")
	@Element(dependent = "true")
	private List invoices = new ArrayList();

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerStatus(String payerStatus) {
		this.payerStatus = payerStatus;
	}

	public String getPayerStatus() {
		return payerStatus;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getBusiness() {
		return business;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setAddressStatus(String addressStatus) {
		this.addressStatus = addressStatus;
	}

	public String getAddressStatus() {
		return addressStatus;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public String getShipToName() {
		return shipToName;
	}

	public void setShipToStreet1(String shipToStreet1) {
		this.shipToStreet1 = shipToStreet1;
	}

	public String getShipToStreet1() {
		return shipToStreet1;
	}

	public void setShipToStreet2(String shipToStreet2) {
		this.shipToStreet2 = shipToStreet2;
	}

	public String getShipToStreet2() {
		return shipToStreet2;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}

	public String getShipToZip() {
		return shipToZip;
	}

	public void setShipToCountryCode(String shipToCountryCode) {
		this.shipToCountryCode = shipToCountryCode;
	}

	public String getShipToCountryCode() {
		return shipToCountryCode;
	}
	
	public List getInvoices(){
		return this.invoices;
	}
	
	public void setInvoices(List invoices){
		this.invoices = invoices;
	}

}
