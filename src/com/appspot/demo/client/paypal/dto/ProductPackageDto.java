package com.appspot.demo.client.paypal.dto;

public class ProductPackageDto {
	
	private String key;
	
	private String packageImageURL;
	
	private String packageName;
	
	private String packageDescription;
	
	private String moreInformation;
	
	private String packageCost;
	
	private String billingPeriod;
	
	private String billingFrequency;

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
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

	public void setPackageCost(String packageCost) {
		this.packageCost = packageCost;
	}

	public String getPackageCost() {
		return packageCost;
	}

	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public String getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingFrequency(String billingFrequency) {
		this.billingFrequency = billingFrequency;
	}

	public String getBillingFrequency() {
		return billingFrequency;
	}

	public void setMoreInformation(String moreInformation) {
		this.moreInformation = moreInformation;
	}

	public String getMoreInformation() {
		return moreInformation;
	}
	
}
