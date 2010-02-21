package com.appspot.demo.client.paypal.dto;

import java.util.Date;
import java.util.List;

public class InvoiceDto {
	
	private String key;
	
	private String customerId;
	
	private String customerEmail;
	
	private String productPackageId;
	
	private String productPackageName;
	
	private String paypalRecurringPaymentProfileId;
	
	private String createdDate;
	
	private String modifiedDate;
	
	private String status;
	
	private String outstandingBalance;
	
	private String nextPaymentDate;
	
	private String currencyCode;
	
	private String initalPaymentAmount;
	
	private String tax;
	
	private String shipping;
	
	private String appUserId;
	
	

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setProductPackageId(String productPackageId) {
		this.productPackageId = productPackageId;
	}

	public String getProductPackageId() {
		return productPackageId;
	}

	public void setPaypalRecurringPaymentProfileId(
			String paypalRecurringPaymentProfileId) {
		this.paypalRecurringPaymentProfileId = paypalRecurringPaymentProfileId;
	}

	public String getPaypalRecurringPaymentProfileId() {
		return paypalRecurringPaymentProfileId;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setOutstandingBalance(String outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public String getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setNextPaymentDate(String nextPaymentDate) {
		this.nextPaymentDate = nextPaymentDate;
	}

	public String getNextPaymentDate() {
		return nextPaymentDate;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setInitalPaymentAmount(String initalPaymentAmount) {
		this.initalPaymentAmount = initalPaymentAmount;
	}

	public String getInitalPaymentAmount() {
		return initalPaymentAmount;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTax() {
		return tax;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getShipping() {
		return shipping;
	}


	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getAppUserId() {
		return appUserId;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setProductPackageName(String productPackageName) {
		this.productPackageName = productPackageName;
	}

	public String getProductPackageName() {
		return productPackageName;
	}

	

}
