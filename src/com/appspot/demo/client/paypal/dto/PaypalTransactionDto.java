package com.appspot.demo.client.paypal.dto;

import java.util.Date;
import java.util.List;

public class PaypalTransactionDto {
	
	private String key;
	
	private Date dateCreated;
	
	private String mcGross;
	
	private String mcFee;
	
	private String mcHandling;
	
	private String mcCurrency;
	
	private String invoiceId;
	
	private String transactionId;
	
	private String parentTransactionId;
	
	private String receiptId;
	
	private String transactionType;
	
	private String paymentType;
	
	private Date orderTime;
	
	private String amount;
	
	private String currencyCode;
	
	private String feeAmount;
	
	private String settleAmount;
	
	private String taxAmount;
	
	private String exchangeRate;
	
	private String pendingReason;
	
	private String reasonCode;
	
	private String protectionEligibility;
	
	private String invnum;
	
	private String custom;
	
	private String note;
	
	private String salesTax;
	
	private Date paymentDate;
	
	private String subject;
	
	private List<String> paymentItemDescription;
	
	private List<String> paymentItemNumber;
	
	private List<Integer> paymentItemQuantity;
	
	private List<String> paymentItemAmount;
	
	private List<String> paymentItemOptionName;
	
	private List<String> paymentItemOptionValue;
	
	private String shipping;
	
	private String paymentGross;

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setMcGross(String mcGross) {
		this.mcGross = mcGross;
	}

	public String getMcGross() {
		return mcGross;
	}

	public void setMcFee(String mcFee) {
		this.mcFee = mcFee;
	}

	public String getMcFee() {
		return mcFee;
	}

	public void setMcHandling(String mcHandling) {
		this.mcHandling = mcHandling;
	}

	public String getMcHandling() {
		return mcHandling;
	}

	public void setMcCurrency(String mcCurrency) {
		this.mcCurrency = mcCurrency;
	}

	public String getMcCurrency() {
		return mcCurrency;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceId() {
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

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleAmount() {
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

	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}

	public String getSalesTax() {
		return salesTax;
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

	public void setPaymentItemDescription(List<String> paymentItemDescription) {
		this.paymentItemDescription = paymentItemDescription;
	}

	public List<String> getPaymentItemDescription() {
		return paymentItemDescription;
	}

	public void setPaymentItemNumber(List<String> paymentItemNumber) {
		this.paymentItemNumber = paymentItemNumber;
	}

	public List<String> getPaymentItemNumber() {
		return paymentItemNumber;
	}

	public void setPaymentItemQuantity(List<Integer> paymentItemQuantity) {
		this.paymentItemQuantity = paymentItemQuantity;
	}

	public List<Integer> getPaymentItemQuantity() {
		return paymentItemQuantity;
	}

	public void setPaymentItemAmount(List<String> paymentItemAmount) {
		this.paymentItemAmount = paymentItemAmount;
	}

	public List<String> getPaymentItemAmount() {
		return paymentItemAmount;
	}

	public void setPaymentItemOptionName(List<String> paymentItemOptionName) {
		this.paymentItemOptionName = paymentItemOptionName;
	}

	public List<String> getPaymentItemOptionName() {
		return paymentItemOptionName;
	}

	public void setPaymentItemOptionValue(List<String> paymentItemOptionValue) {
		this.paymentItemOptionValue = paymentItemOptionValue;
	}

	public List<String> getPaymentItemOptionValue() {
		return paymentItemOptionValue;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getShipping() {
		return shipping;
	}

	public void setPaymentGross(String paymentGross) {
		this.paymentGross = paymentGross;
	}

	public String getPaymentGross() {
		return paymentGross;
	}
	
	

}
