package com.appspot.demo.client.paypal.dto.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayString;

public class PaypalTransactionJs extends JavaScriptObject{
	
	protected  PaypalTransactionJs(){}
	
	public static native final PaypalTransactionJs fromJson(String json)/*-{
		return eval('('+json+')').paypalTransactionDto;
	}-*/;
	
	public final native String getKey()/*-{
		return this.key;
	}-*/;
	
	public final native String getDateCreated()/*-{
		return this.dateCreated;
	}-*/;
	
	public final native String getMcGross()/*-{
		return this.mcGross;
	}-*/;
	
	public final native String getMcFee()/*-{
		return this.mcFee;
	}-*/;
	
	public final native String getMcHanding()/*-{
		return this.mcHandling;
	}-*/;
	
	public final native String getMcCurrency()/*-{
		return this.mcCurrency;
	}-*/;
	
	public final native String getInvoiceId()/*-{
		return this.invoiceId;
	}-*/;
	
	public final native String getTransactionId()/*-{
		return this.transactionId;
	}-*/;
	
	public final native String getParentTransactionId()/*-{
		return this.parentTransactionId;
	}-*/;
	
	public final native String getReceiptId()/*-{
		return this.receiptId;
	}-*/;
	
	public final native String getTransactionType()/*-{
		return this.transactionType;
	}-*/;
	
	public final native String getPaymentType()/*-{
		return this.paymentType;
	}-*/;
	
	public final native String getOrderTime()/*-{
		return this.orderTime;
	}-*/;
	
	public final native String getAmount()/*-{
		return this.amount;
	}-*/;
	
	public final native String getCurrencyCode()/*-{
		return this.currencyCode;
	}-*/;
	
	public final native String getFeeAmount()/*-{
		return this.feeAmount;
	}-*/;
	
	public final native String getSettleAmount()/*-{
		return this.settleAmount;
	}-*/;
	
	public final native String getTaxAmount()/*-{
		return this.taxAmount;
	}-*/;
	
	public final native String getExchangeRate()/*-{
		return this.exchangeRate;
	}-*/;
	
	public final native String getPendingReason()/*-{
		return this.pendingReason;
	}-*/;
	
	public final native String getReasonCode()/*-{
		return this.reasonCode;
	}-*/;
	
	public final native String getProtectionEligibility()/*-{
		return this.protectionEligibility;
	}-*/;
	
	public final native String getInvnum()/*-{
		return this.invnum;
	}-*/;
	
	public final native String getCustom()/*-{
		return this.custom;
	}-*/;
	
	public final native String getNote()/*-{
		return this.note;
	}-*/;
	
	public final native String getSalesTax()/*-{
		return this.salesTax;
	}-*/;
	
	public final native String getPaymentDate()/*-{
		return this.paymentDate;
	}-*/;
	
	public final native String getSubject()/*-{
		return this.subject;
	}-*/;
	
	public final native String getShipping()/*-{
		return this.shipping;
	}-*/;
	
	public final native String getPaymentGross()/*-{
		return this.paymentGross;
	}-*/;
	
	public final native String getPaymentStatus()/*-{
		return this.paymentStatus;
	}-*/;
	
	public final native JsArrayString getpaymentItemDescription()/*-{
		return this.paymentItemDescription;
	}-*/;
	
	public final native JsArrayString getpaymentItemNumber()/*-{
		return this.paymentItemNumber;
	}-*/;
	
	public final native JsArrayInteger getPaymentItemQuantity()/*-{
		return this.paymentItemQuantity;
	}-*/;
	
	public final native JsArrayString getPaymentItemAmount()/*-{
		return this.paymentItemAmount;
	}-*/;
	
	public final native JsArrayString getPaymentOptionName()/*-{
		return this.paymentOptionName;
	}-*/;
	
	public final native JsArrayString getPaymentOptionValue()/*-{
		return this.paymentOptionValue;
	}-*/;
	
}
