package com.appspot.demo.client.paypal.dto.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

public class InvoiceJs extends JavaScriptObject{
	
	protected InvoiceJs(){}
	
	public static final native InvoiceJs fromJson(String json)/*-{
		return eval('('+json+')').invoiceDto
	}-*/;
	
	public final native String getKey()/*-{
		return this.key;
	}-*/;
	
	
	public final native String getCustomerEmail()/*-{
		return this.customerEmail;
	}-*/;
	
	public final native String getProductPackageId()/*-{
		return this.productPackageId;
	}-*/;
	
	
	public final native String getProductPackageName()/*-{
		return this.productPackageName;
	}-*/;
	
	public final native String getPaypalRecurringPaymentProfileId()/*-{
		return this.paypalRecurringPaymentProfileId;
	}-*/;
	
	public final native String getCreatedDate()/*-{
		return this.createdDate;
	}-*/;
	
	public final native String getModifiedDate()/*-{
		return this.modifiedDate;
	}-*/;
	
	public final native String getStatus()/*-{
		return this.status;
	}-*/;
	
	public final native String getOutstandingBalance()/*-{
		return this.outstandingBalance;
	}-*/;
	
	public final native String getNextPaymentDate()/*-{
		return this.nextPaymentDate;
	}-*/;
	
	public final native String getCurrencyCode()/*-{
		return this.currencyCode;
	}-*/;
	
	public final native  String getInitialPaymentAmount()/*-{
		return this.initialPaymentAmount;
	}-*/;
	
	public final native String getTax()/*-{
		return this.tax;
	}-*/;
	
	public final native String getShipping()/*-{
		return this.shipping;
	}-*/;
		
	public final native String getAppUserId()/*-{
		return this.appUserId;
	}-*/;
	
	

}
