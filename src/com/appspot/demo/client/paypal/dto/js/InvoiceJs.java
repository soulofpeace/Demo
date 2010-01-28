package com.appspot.demo.client.paypal.dto.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

public class InvoiceJs extends JavaScriptObject{
	
	protected InvoiceJs(){}
	
	public static final native InvoiceJs fromJson(String json)/*-{
		return eval('('+json+')').invoiceDto;
	}-*/;
	
	public final native String getKey()/*-{
		return this.key;
	}-*/;
	
	public final native String customerId()/*-{
		return this.customerName;
	}-*/;
	
	public final native String productPackageId()/*-{
		return this.productPackageId;
	}-*/;
	
	public final native String getCustomerName()/*-{
		return this.customerName;
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
		return this.outstandingBalance();
	}-*/;
	
	public final native String nextPaymentDate()/*-{
		return this.nextPaymentDate;
	}-*/;
	
	public final native String currencyCode()/*-{
		return this.currencyCode;
	}-*/;
	
	public final native  String initialPaymentAmount()/*-{
		return this.initialPaymentAmount;
	}-*/;
	
	public final native String tax()/*-{
		return this.tax;
	}-*/;
	
	public final native String shipping()/*-{
		return this.shipping;
	}-*/;
	
	public final native JsArrayString getTransactions()/*-{
		return this.transactions;
	}-*/;
	
	public final native String getAppUserId()/*-{
		return this.appUserId;
	}-*/;
	
	public final native String getAppUserName()/*-{
		return this.appUserName;
	}-*/;	

}
