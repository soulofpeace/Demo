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
public class PaypalTransaction {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private double mcGross;
	
	@Persistent
	private double mcFee;
	
	@Persistent
	private double mcHandling;
	
	@Persistent
	private String mcCurrency;
	
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
	private double taxAmount;
	
	@Persistent
	private double exchangeRate;
	
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
	private double salestax;

	@Persistent 
	private Date paymentDate;
	
	@Persistent
	private double paymentFee;
	
	@Persistent
	private String subject;
	
	@Persistent
	private ArrayList<String> paymentItemDescription;
	
	@Persistent
	private ArrayList<String> paymentItemNumber;
	
	@Persistent
	private ArrayList<Integer> paymentItemquantity;
	
	@Persistent
	private ArrayList<Double> paymentItemAmount;
	
	@Persistent 
	private ArrayList<String> paymentItemOptionName;
	
	@Persistent
	private ArrayList<String> paymentItemOptionValue;
	
	@Persistent 
	private double shipping;
	
	@Persistent
	private double paymentGross;
	
	
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

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getExchangeRate() {
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

	public void setSalestax(double salestax) {
		this.salestax = salestax;
	}

	public double getSalestax() {
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

	public void setPaymentItemquantity(ArrayList<Integer> paymentItemquantity) {
		this.paymentItemquantity = paymentItemquantity;
	}

	public ArrayList<Integer> getPaymentItemquantity() {
		return paymentItemquantity;
	}

	public void setPaymentItemAmount(ArrayList<Double> paymentItemAmount) {
		this.paymentItemAmount = paymentItemAmount;
	}

	public ArrayList<Double> getPaymentItemAmount() {
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

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setMcGross(double mcGross) {
		this.mcGross = mcGross;
	}

	public double getMcGross() {
		return mcGross;
	}

	public void setMcFee(double mcFee) {
		this.mcFee = mcFee;
	}

	public double getMcFee() {
		return mcFee;
	}

	public void setMcHandling(double mcHandling) {
		this.mcHandling = mcHandling;
	}

	public double getMcHandling() {
		return mcHandling;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public double getShipping() {
		return shipping;
	}

	public void setPaymentGross(double paymentGross) {
		this.paymentGross = paymentGross;
	}

	public double getPaymentGross() {
		return paymentGross;
	}

	public void setMcCurrency(String mcCurrency) {
		this.mcCurrency = mcCurrency;
	}

	public String getMcCurrency() {
		return mcCurrency;
	}

	public void setPaymentFee(double paymentFee) {
		this.paymentFee = paymentFee;
	}

	public double getPaymentFee() {
		return paymentFee;
	}
	
	
	
	

}
