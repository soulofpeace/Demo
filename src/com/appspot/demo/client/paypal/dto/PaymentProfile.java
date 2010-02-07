package com.appspot.demo.client.paypal.dto;

public class PaymentProfile {
	private PaypalCustomerDto customer;
	
	private InvoiceDto invoice;
	
	private PaypalTransactionDto paypalTransaction;

	public void setCustomer(PaypalCustomerDto customer) {
		this.customer = customer;
	}

	public PaypalCustomerDto getCustomer() {
		return customer;
	}

	public void setInvoice(InvoiceDto invoice) {
		this.invoice = invoice;
	}

	public InvoiceDto getInvoice() {
		return invoice;
	}

	public void setPaypalTransaction(PaypalTransactionDto paypalTransaction) {
		this.paypalTransaction = paypalTransaction;
	}

	public PaypalTransactionDto getPaypalTransaction() {
		return paypalTransaction;
	}
	
	

}
