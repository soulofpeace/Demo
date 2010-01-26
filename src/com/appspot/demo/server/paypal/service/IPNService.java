package com.appspot.demo.server.paypal.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.ws.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appspot.demo.server.paypal.dao.InvoiceDao;
import com.appspot.demo.server.paypal.dao.PackageDao;
import com.appspot.demo.server.paypal.dao.PaypalCustomerDao;
import com.appspot.demo.server.paypal.dao.PaypalTransactionDao;
import com.appspot.demo.server.paypal.model.ActionSource;
import com.appspot.demo.server.paypal.model.ActionType;
import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.appspot.demo.server.paypal.service.exception.PaypalException;
import com.appspot.demo.server.paypal.service.util.PaypalIPNVerifier;
import com.appspot.demo.server.paypal.service.util.PaypalResponseDecoder;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Service
public class IPNService {
	private static final Logger logger = Logger.getLogger(IPNService.class.getName());
	
	@Autowired
	private PaypalIPNVerifier paypalIPNVerifier;
	
	@Autowired
	private ActionLoggerService actionLoggerService;
	
	@Autowired 
	private PaypalCustomerDao paypalCustomerDao;
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	private PackageDao packageDao;
	
	@Autowired
	private PaypalTransactionDao paypalTransactionDao;
	
	@Autowired
	private PaypalService paypalService;
	
	public void handleIPN(String responseString){
		if(this.paypalIPNVerifier.verifyResponse(responseString)){
			PaypalResponseDecoder decoder = new PaypalResponseDecoder();
			try {
				decoder.decode(responseString);
				String txnType = decoder.get("txn_type");
				logger.info("txn type is "+txnType);
				if(txnType.equalsIgnoreCase("recurring_payment_profile_created")){
					logger.info("Recurring payment profile parsing");
					this.parseRecurringProfileMsg(decoder, responseString);
					
				}
				else if(txnType.equalsIgnoreCase("recurring_payment")){
					logger.info("Recurring payment");
					this.parseRecurringPaymentMsg(decoder, responseString);
				}
				else{
					logger.warning("No such transaction type supported yet "+txnType);
				}
				
			} catch (PaypalException e) {
				// TODO Auto-generated catch block
				logger.warning("Unable to decode message "+responseString);
				logger.warning(e.getMessage());
			}
		}
		else{
			logger.warning("Unable to verify IPN Message "+responseString);
			this.actionLoggerService.log(ActionType.NOTVERIFIEDIPN, ActionSource.PAYPAL, responseString, null);
		}
	}
	
	private void parseRecurringProfileMsg(PaypalResponseDecoder decoder, String responseString){
		String paymentCycle = decoder.get("payment_cycle");
		String timeCreated = decoder.get("time_created");
		String verifySignature= decoder.get("verify_sign");
		String periodType= decoder.get("period_type");
		String productType = decoder.get("product_type");
		String amountPerCycle= decoder.get("amount_per_cycle");
		String amount = decoder.get("amount");
		String profileId = decoder.get("recurring_payment_id");
		String payerId = decoder.get("payer_id");
		String product_name= decoder.get("product_name");
		
		Invoice invoice =this.invoiceDao.getInvoiceByProfileId(profileId);
		RecurringProductPackage productPackage = this.packageDao.getPackageById(KeyFactory.keyToString(invoice.getProductPackageId()));
		PaypalCustomer customer = this.paypalCustomerDao.getPaypalCustomerById(KeyFactory.keyToString(invoice.getCustomerId()));
		if (!productPackage.getPackageDescription().equalsIgnoreCase(product_name)){
			logger.warning("packageName "+productPackage.getPackageDescription()+" does not match product name"+product_name);
			this.actionLoggerService.log(ActionType.NOTVERIFIEDIPN, ActionSource.PAYPAL, responseString, null);
			return;
		}
		if(!customer.getPayerId().equalsIgnoreCase(payerId)){
			logger.warning("payer id does not equal to our payerid in db");
			this.actionLoggerService.log(ActionType.NOTVERIFIEDIPN, ActionSource.PAYPAL, responseString, null);
			return;
		}
		
		boolean invoiceStatus=this.updateInvoice(decoder, invoice);
		boolean customerStatus=this.updatePaypalCustomer(decoder, customer);
		if(!(invoiceStatus && customerStatus)){
			this.actionLoggerService.log(ActionType.FAILEDINVOICE, ActionSource.PAYPAL, responseString, null);
		}
		
		
	}
	
	private void parseRecurringPaymentMsg(PaypalResponseDecoder decoder, String responseString){
		String receiverId = decoder.get("receiver_id");
		String business = decoder.get("business");
		String verifySign= decoder.get("verify_sign");
		String receiverEmail = decoder.get("receiver_email");
		String timeCreated = decoder.get("time_created");
		String productType =decoder.get("product_type");
		String paymentCycle = decoder.get("payment_cycle");
		String periodType = decoder.get("period_type");
		String amountPerCycle= decoder.get("amount_per_cycle");

		String profileId = decoder.get("recurring_payment_id");
		String product_name= decoder.get("product_name");
		String payerId = decoder.get("payer_id");
		
		Invoice invoice =this.invoiceDao.getInvoiceByProfileId(profileId);
		RecurringProductPackage productPackage = this.packageDao.getPackageById(KeyFactory.keyToString(invoice.getProductPackageId()));
		PaypalCustomer customer = this.paypalCustomerDao.getPaypalCustomerById(KeyFactory.keyToString(invoice.getCustomerId()));
		if (!productPackage.getPackageDescription().equalsIgnoreCase(product_name)){
			this.actionLoggerService.log(ActionType.NOTVERIFIEDIPN, ActionSource.PAYPAL, responseString, null);
			return;
		}
		if(!customer.getPayerId().equalsIgnoreCase(payerId)){
			this.actionLoggerService.log(ActionType.NOTVERIFIEDIPN, ActionSource.PAYPAL, responseString, null);
			return;
		}
		
		boolean invoiceStatus = this.updateInvoice(decoder, invoice);
		boolean customerStatus =this.updatePaypalCustomer(decoder, customer);
		
		String txnId= decoder.get("txn_id");
		PaypalTransaction paypalTransaction;
		try {
			paypalTransaction = this.paypalService.getTransactionDetails(txnId);
			boolean transactionStatus =this.updateTransaction(decoder, paypalTransaction, invoice);
			if(!(invoiceStatus && customerStatus && transactionStatus)){
				this.actionLoggerService.log(ActionType.FAILEDTRANSACTION, ActionSource.PAYPAL, responseString, null);
			}
		} catch (PaypalException e) {
			// TODO Auto-generated catch block
			logger.warning("Unable to get transaction id "+txnId+" details from paypal");
			logger.warning(e.getMessage());
			this.actionLoggerService.log(ActionType.FAILEDTRANSACTION, ActionSource.PAYPAL, responseString, null);
			return;
		}
		
	}
	
	
	private Date parseDate(String dateString){
		logger.info("Parsing date "+dateString);
		SimpleDateFormat pstDateFormat = new SimpleDateFormat("HH:mm:ss MMM d, yyyy z");
		try {
			return pstDateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.warning("Unable to parse Date "+dateString);
			return new Date();
		}
	}
	
	
	private boolean updateTransaction(PaypalResponseDecoder decoder, PaypalTransaction paypalTransaction, Invoice invoice){
		String mcGross= decoder.get("mc_gross");
		String currencyCode= decoder.get("currency_code");
		String mcFee=decoder.get("mc_fee");
		String paymentGross = decoder.get("payment_gross");
		String shipping = decoder.get("shipping");
		String txnId= decoder.get("txn_id");
		String paymentDate = decoder.get("payment_date");
		String protectionEligibility = decoder.get("protection_eligibility");
		String mcCurrency= decoder.get("mc_currency");
		String transactionSubject = decoder.get("transaction_subject");
		String paymentStatus = decoder.get("payment_status");
		String paymentFee = decoder.get("payment_fee");
		String paymentType = decoder.get("payment_type");
		String reasonCode = decoder.get("reason_code");
		//check for null before setting
		
		if(mcGross!=null){
			paypalTransaction.setMcGross(Double.parseDouble(mcGross));
		}
		if(currencyCode!=null){
			paypalTransaction.setCurrencyCode(currencyCode);
		}
		if(mcFee!=null){
			paypalTransaction.setMcFee(Double.parseDouble(mcFee));
		}
		if(shipping!=null){
			paypalTransaction.setShipping(Double.parseDouble(shipping));
		}
		if(paymentGross!=null){
			paypalTransaction.setPaymentGross(Double.parseDouble(paymentGross));
		}
		if(txnId!=null){
			paypalTransaction.setTransactionId(txnId);
		}
		if(paymentDate!=null){
			paypalTransaction.setPaymentDate(this.parseDate(paymentDate));
		}
		
		if(protectionEligibility!=null){
			paypalTransaction.setProtectionEligibility(protectionEligibility);
		}
		if(mcCurrency!=null){
			paypalTransaction.setMcCurrency(mcCurrency);
		}
		
		if(paymentStatus!=null){
			paypalTransaction.setPaymentStatus(paymentStatus);
		}
		if(transactionSubject!=null){
			paypalTransaction.setSubject(transactionSubject);
		}
		if(paymentFee!=null){
			paypalTransaction.setPaymentFee(Double.parseDouble(paymentFee));
		}
		if(paymentType!=null){
			paypalTransaction.setTransactionType(paymentType);
		}
		
		if(reasonCode!=null){
			paypalTransaction.setReasonCode(reasonCode);
		}
		
		paypalTransaction.setDateCreated(new Date());
		paypalTransaction.setInvoiceId(invoice.getId());
		String transactionId= this.paypalTransactionDao.savePaypalTransaction(paypalTransaction);
		if(transactionId!=null){
			invoice.getTransactions().add(KeyFactory.stringToKey(transactionId));
			String invoiceId = this.invoiceDao.saveInvoice(invoice);
			if(invoiceId!=null){
				List<Key> keys = new ArrayList<Key>();
				keys.add(invoice.getId());
				keys.add(paypalTransaction.getId());
				this.actionLoggerService.log(ActionType.NEWTRANSACTION, ActionSource.PAYPAL, null, keys);
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	
	private boolean updatePaypalCustomer(PaypalResponseDecoder decoder, PaypalCustomer customer){
		String lastName = decoder.get("last_name");
		String residenceCountry = decoder.get("residence_country");
		String payerEmail = decoder.get("payer_email");
		String firstName = decoder.get("first_name");
		String payerStatus= decoder.get("payer_status");
		
		customer.setPayerStatus(payerStatus);
		customer.setPayerEmail(payerEmail);
		customer.setLastname(lastName);
		customer.setCountryCode(residenceCountry);
		customer.setFirstname(firstName);
		
		String customerId =this.paypalCustomerDao.savePaypalCustomer(customer);
		if(customerId!=null){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	private boolean updateInvoice(PaypalResponseDecoder decoder, Invoice invoice){
		String status = decoder.get("profile_status");
		String outstandingBalance = decoder.get("outstanding_balance");
		String currencyCode = decoder.get("currency_code");
		String nextPaymentDate = decoder.get("next_payment_date");
		String initialPaymentAmount = decoder.get("initial_payment_amount");
		String tax = decoder.get("tax");
		String shipping = decoder.get("shipping");
		
		invoice.setCurrencyCode(currencyCode);
		invoice.setStatus(status);
		invoice.setOutstandingBalance(Double.parseDouble(outstandingBalance));
		invoice.setNextPaymentDate(this.parseDate(nextPaymentDate));
		invoice.setInitialPaymentAmount(Double.parseDouble(initialPaymentAmount));
		invoice.setTax(Double.parseDouble(tax));
		invoice.setShipping(Double.parseDouble(shipping));
		
		String invoiceId =this.invoiceDao.saveInvoice(invoice);
		if(invoiceId!=null){
			return true;
		}
		else{
			return false;
		}
	}
}
