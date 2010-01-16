package com.appspot.demo.client.paypal.dto.js;

import com.google.gwt.core.client.JavaScriptObject;

public class ProductPackageJS extends JavaScriptObject{
	protected ProductPackageJS(){}
	
	public static native final ProductPackageJS fromJson(String json)/*-{
		return eval('('+json+')').productPackageDto;
	}-*/;
	
	public final native String getKey()/*-{
		return this.key;
	}-*/;
	
	public final native String getPackageImageURL()/*-{
		return this.packageImageURL;
	}-*/;
	
	public final native String getPackageName()/*-{
		return this.packageName;
	}-*/;
	
	public final native String getPackageDescription()/*-{
		return this.packageDescription;
	}-*/;
	
	public final native String getMoreInformation()/*-{
		return this.moreInformation;
	}-*/;
	
	public final native String getPackageCost()/*-{
		return this.packageCost;
	}-*/;
	
	public final native String getBillingPeriod()/*-{
		return this.billingPeriod;
	}-*/;
	
	public final native String getBillingFrequency()/*-{
		return this.billingFrequency;
	}-*/;
	
	

}
