package com.appspot.demo.server.paypal.model;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable ="true")
public class Transaction {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent 
	private String type;
	
	//paypal transaction fields
	@Persistent
	private Key invoiceId;
	
	@Persistent
	private String transactionId;
	
	@Persistent
	private String parentTransactionId;
	
	@Persistent
	private String receiptId;
	
	@Persistent
	private String transactionType;
	
	@Persistent
	private String paymentType;
	
	@Persistent
	private Date orderTime;
	
	@Persistent
	private  double amount;
	
	@Persistent
	private String currencyCode;
	
	@Persistent
	private double feeAmount;
	
	@Persistent
	private double settleAmount;
	
	@Persistent
	private String taxAmount;
	
	@Persistent
	private String exchangeRate;
	
	@Persistent
	private String paymentStatus;
	
	@Persistent
	private String pendingReason;
	
	@Persistent
	private String reasonCode;
	
	@Persistent 
	private String protectionEligibility;
	
	@Persistent
	private String invnum;
	
	@Persistent
	private String custom;
	
	@Persistent
	private String note;
	
	@Persistent
	private String salestax;
	
	@Persistent
	private ArrayList<String> paymentItemDescription;
	
	@Persistent
	private ArrayList<String> paymentItemNumber;
	
	@Persistent
	private ArrayList<String> paymentItemquantity;
	
	@Persistent
	private ArrayList<String> paymentItemAmount;
	
	@Persistent 
	private ArrayList<String> paymentItemOptionName;
	
	@Persistent
	private ArrayList<String> paymentItemOptionValue;
	
	
	//Paypal CancelledTransation & Paypal Profile Created
	@Persistent
	private String comment;
	
	//Paypal CancelledTransaction
	private Key paypalApplicationUser;
	
	private Key paypalProductPackageKey;

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setInvoiceId(Key invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Key getInvoiceId() {
		return invoiceId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setParentTransactionId(String parentTransactionId) {
		this.parentTransactionId = parentTransactionId;
	}

	public String getParentTransactionId() {
		return parentTransactionId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getReceiptId() {
		return receiptId;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public double getFeeAmount() {
		return feeAmount;
	}

	public void setSettleAmount(double settleAmount) {
		this.settleAmount = settleAmount;
	}

	public double getSettleAmount() {
		return settleAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setPendingReason(String pendingReason) {
		this.pendingReason = pendingReason;
	}

	public String getPendingReason() {
		return pendingReason;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setProtectionEligibility(String protectionEligibility) {
		this.protectionEligibility = protectionEligibility;
	}

	public String getProtectionEligibility() {
		return protectionEligibility;
	}

	public void setInvnum(String invnum) {
		this.invnum = invnum;
	}

	public String getInvnum() {
		return invnum;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getCustom() {
		return custom;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setSalestax(String salestax) {
		this.salestax = salestax;
	}

	public String getSalestax() {
		return salestax;
	}

	public void setPaymentItemDescription(ArrayList<String> paymentItemDescription) {
		this.paymentItemDescription = paymentItemDescription;
	}

	public ArrayList<String> getPaymentItemDescription() {
		return paymentItemDescription;
	}

	public void setPaymentItemNumber(ArrayList<String> paymentItemNumber) {
		this.paymentItemNumber = paymentItemNumber;
	}

	public ArrayList<String> getPaymentItemNumber() {
		return paymentItemNumber;
	}

	public void setPaymentItemquantity(ArrayList<String> paymentItemquantity) {
		this.paymentItemquantity = paymentItemquantity;
	}

	public ArrayList<String> getPaymentItemquantity() {
		return paymentItemquantity;
	}

	public void setPaymentItemAmount(ArrayList<String> paymentItemAmount) {
		this.paymentItemAmount = paymentItemAmount;
	}

	public ArrayList<String> getPaymentItemAmount() {
		return paymentItemAmount;
	}

	public void setPaymentItemOptionName(ArrayList<String> paymentItemOptionName) {
		this.paymentItemOptionName = paymentItemOptionName;
	}

	public ArrayList<String> getPaymentItemOptionName() {
		return paymentItemOptionName;
	}

	public void setPaymentItemOptionValue(ArrayList<String> paymentItemOptionValue) {
		this.paymentItemOptionValue = paymentItemOptionValue;
	}

	public ArrayList<String> getPaymentItemOptionValue() {
		return paymentItemOptionValue;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setPaypalApplicationUser(Key paypalApplicationUser) {
		this.paypalApplicationUser = paypalApplicationUser;
	}

	public Key getPaypalApplicationUser() {
		return paypalApplicationUser;
	}

	public void setPaypalProductPackageKey(Key paypalProductPackageKey) {
		this.paypalProductPackageKey = paypalProductPackageKey;
	}

	public Key getPaypalProductPackageKey() {
		return paypalProductPackageKey;
	}
	
	
	
	

}
