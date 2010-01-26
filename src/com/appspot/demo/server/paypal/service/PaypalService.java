package com.appspot.demo.server.paypal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.appspot.demo.server.paypal.service.exception.PaypalException;
import com.appspot.demo.server.paypal.service.util.APICredential;
import com.appspot.demo.server.paypal.service.util.PaypalCallerService;
import com.appspot.demo.server.paypal.service.util.PaypalRequestEncoder;
import com.appspot.demo.server.paypal.service.util.PaypalResponseDecoder;

@Service
public class PaypalService {
	private static final Logger logger = Logger.getLogger(PaypalService.class.getName());
	
	@Autowired
	private PaypalCallerService paypalCallerService;
	
	private String paypalExpressCheckoutURL="https://www.sandbox.paypal.com/cgi-bin/webscr";
	
	
	public String getPaypalExpressCheckoutURL(String token){
		String redirectURL=this.paypalExpressCheckoutURL+"?cmd=_express-checkout&token="+token;
		logger.info("Paypal Redirect URL is "+redirectURL);
		return redirectURL;
	}
	
	//assumption only for recurring payment
	//need to modify package based on one time payment model
	public String setExpressCheckOut(RecurringProductPackage productPackage, String returnURL, String cancelURL) throws PaypalException{
		PaypalRequestEncoder encoder = new PaypalRequestEncoder();
		PaypalResponseDecoder decoder = new PaypalResponseDecoder();
		encoder.add("METHOD", "SetExpressCheckout");
		encoder.add("RETURNURL", returnURL);
		encoder.add("CANCELURL", cancelURL);
		encoder.add("NOSHIPPING", "1");
		encoder.add("LANDINGPAGE", "Billing");
		encoder.add("HDRIMG", "https://sites.google.com/a/socialwok.com/emailmkting/socialwoklogo_small_email.jpg");
		encoder.add("AMT", "0");
		encoder.add("L_BILLINGTYPE0", "RecurringPayments");
		logger.info("Package Description is "+ productPackage.getPackageDescription());
		encoder.add("L_BILLINGAGREEMENTDESCRIPTION0", productPackage.getPackageDescription());
		String requestParam = encoder.encode();
		logger.info("Encoded Request Param is "+requestParam);
		String response = this.paypalCallerService.call(requestParam);
		logger.info("Response is "+response);
		decoder.decode(response);
		if(this.isSuccess(decoder)){
			logger.info("TOKEN is "+decoder.get("TOKEN"));
			return decoder.get("TOKEN");
		}
		else{
			logger.warning("Paypal API Call has failed");
			return null;
		}
		
		
	}
	
	public PaypalTransaction getTransactionDetails(String transactionId) throws PaypalException{
		PaypalRequestEncoder encoder = new PaypalRequestEncoder();
		PaypalResponseDecoder decoder = new PaypalResponseDecoder();
		encoder.add("METHOD", "GetTransactionDetails");
		encoder.add("TRANSACTIONID", transactionId);
		String requestParam = encoder.encode();
		String response = this.paypalCallerService.call(requestParam);
		logger.info("Response is "+response);
		decoder.decode(response);
		if(this.isSuccess(decoder)){
			PaypalTransaction paypalTransaction = new PaypalTransaction();
			String receiverEmail = decoder.get("RECEIVEREMAIL");
			String receiverId = decoder.get("RECEIVERID");
			String payerEmail = decoder.get("EMAIL");
			String payerId = decoder.get("PAYERID");
			String payserStatus = decoder.get("PAYERSTATUS");
			String shipToCountryCode = decoder.get("SHIPTOCOUNTRYCODE");
			String payerBusiness = decoder.get("PAYERBUSINESS");
			String saluation = decoder.get("SALUTATION");
			String firstName = decoder.get("FIRSTNAME");
			String middleName = decoder.get("MIDDLENAME");
			String lastName = decoder.get("LASTNAME");
			String suffix = decoder.get("SUFFIX");
			String addressOwner = decoder.get("ADDRESSOWNER");
			String addressStatus = decoder.get("ADDRESSSTATUS");
			String shipToName = decoder.get("SHIPTONAME");
			String shipToStreet = decoder.get("SHIPTOSTREET");
			String shipToStreet2 = decoder.get("SHIPTOSTREET2");
			String shipToCity = decoder.get("SHIPTOCITY");
			String shipToState = decoder.get("SHIPTOSTATE");
			String shipToZip = decoder.get("SHIPTOZIP");
			String shipToPhoneNumber= decoder.get("SHIPTOPHONENUM");
			transactionId = decoder.get("TRANSACTIONID");
			String parentTransactionId = decoder.get("PARENTTRANSACTIONID");
			String receiptId = decoder.get("RECEIPTID");
			String transactionType = decoder.get("TRANSACTIONTYPE");
			String paymentType = decoder.get("PAYMENTTYPE");
			String orderTime = decoder.get("ORDERTIME");
			String amount = decoder.get("AMT");
			String currencyCode = decoder.get("CURRENCYCODE");
			String feeAmount =  decoder.get("CURRENCYCODE");
			String settleAmount = decoder.get("SETTLEAMT");
			String taxAmount = decoder.get("TAXAMT");
			String exchangeRate = decoder.get("EXCHANGERATE");
			String paymentStatus = decoder.get("PAYMENTSTATUS");
			String pendingReason = decoder.get("PENDINGREASON");
			String reasonCode = decoder.get("REASONCODE");
			String protectionEligibilty = decoder.get("PROTECTIONELIGIBILITY");
			String invnum = decoder.get("INVNUM");
			String custom = decoder.get("CUSTOM");
			String note = decoder.get("NOTE");
			String saleTax = decoder.get("SALESTAX");
			
			paypalTransaction.setTransactionId(transactionId);
			paypalTransaction.setParentTransactionId(parentTransactionId);
			paypalTransaction.setReceiptId(receiptId);
			paypalTransaction.setTransactionType(transactionType);
			paypalTransaction.setPaymentType(paymentType);
			paypalTransaction.setOrderTime(this.parseDate(orderTime));
			paypalTransaction.setAmount(Double.parseDouble(amount));
			paypalTransaction.setCurrencyCode(currencyCode);
			paypalTransaction.setFeeAmount(Double.parseDouble(feeAmount));
			paypalTransaction.setSettleAmount(Double.parseDouble(settleAmount));
			paypalTransaction.setTaxAmount(Double.parseDouble(taxAmount));
			paypalTransaction.setPendingReason(pendingReason);
			paypalTransaction.setReasonCode(reasonCode);
			paypalTransaction.setProtectionEligibility(protectionEligibilty);
			paypalTransaction.setInvnum(invnum);
			paypalTransaction.setCustom(custom);
			paypalTransaction.setNote(note);
			paypalTransaction.setSalestax(Double.parseDouble(saleTax));
			int counter=1;
			while((decoder.get("DESC")+counter)!=null){
				paypalTransaction.getPaymentItemDescription().add(decoder.get("L_DESC"+counter));
				paypalTransaction.getPaymentItemNumber().add(decoder.get("L_NUMBER"+counter));
				paypalTransaction.getPaymentItemquantity().add(Integer.valueOf(decoder.get("L_QTY"+counter)));
				paypalTransaction.getPaymentItemAmount().add(Double.valueOf(decoder.get("L_AMT"+counter)));
				paypalTransaction.getPaymentItemOptionName().add(decoder.get("L_OPTIONSNAME"+counter));
				paypalTransaction.getPaymentItemOptionValue().add(decoder.get("L_OPTIONSVALUE"+counter));
				counter++;
			}
			
			return paypalTransaction;
		}
		else{
			return null;
		}
	}
	
	public PaypalCustomer getExpressCheckoutDetails(String token)throws PaypalException{
		PaypalRequestEncoder encoder = new PaypalRequestEncoder();
		PaypalResponseDecoder decoder = new PaypalResponseDecoder();
		encoder.add("METHOD", "GetExpressCheckoutDetails");
		encoder.add("TOKEN", token);
		String requestParam = encoder.encode();
		String response = this.paypalCallerService.call(requestParam);
		logger.info("Response is "+response);
		decoder.decode(response);
		if(this.isSuccess(decoder)){
			PaypalCustomer customer = new PaypalCustomer();
			customer.setAddressStatus(decoder.get("ADDRESSSTATUS"));
			customer.setBusiness(decoder.get("BUSINESS"));
			customer.setCountryCode(decoder.get("COUNTRYCODE"));
			customer.setFirstname(decoder.get("FIRSTNAME"));
			customer.setLastname(decoder.get("LASTNAME"));
			customer.setMiddlename(decoder.get("MIDDLENAME"));
			customer.setPayerEmail(decoder.get("EMAIL"));
			customer.setPayerId(decoder.get("PAYERID"));
			customer.setPayerStatus(decoder.get("PAYERSTATUS"));
			customer.setSalutation(decoder.get("SALUTATION"));
			customer.setShipToCity(decoder.get("SHIPTOCITY"));
			customer.setShipToCountryCode(decoder.get("SHIPTOCOUNTRYCODE"));
			customer.setShipToName(decoder.get("SHIPTONAME"));
			customer.setShipToState(decoder.get("SHIPTOSTATE"));
			customer.setShipToStreet1(decoder.get("SHIPTOSTREET"));
			customer.setShipToStreet2(decoder.get("SHITPTOSTREET2"));
			customer.setShipToZip(decoder.get("SHIPTOZIP"));
			customer.setSuffix(decoder.get("SUFFIX"));
			return customer;
		}
		else{
			logger.warning("Paypal API Call has failed");
			return null;
		}
	}
	
	public Invoice createRecurringPaymentsProfile(PaypalCustomer customer, RecurringProductPackage productPackage, String token) throws PaypalException{
		PaypalRequestEncoder encoder = new PaypalRequestEncoder();
		PaypalResponseDecoder decoder = new PaypalResponseDecoder();
		encoder.add("METHOD", "CreateRecurringPaymentsProfile");
		encoder.add("TOKEN", token);
		encoder.add("PROFILESTARTDATE", this.getGMTTimeNow());
		encoder.add("AMT", String.valueOf(productPackage.getPackageCost()));
		encoder.add("BILLINGPERIOD", productPackage.getBillingPeriod());
		encoder.add("BILLINGFREQUENCY", String.valueOf(productPackage.getBillingFrequency()));
		encoder.add("DESC", productPackage.getPackageDescription());
		String requestParam = encoder.encode();
		logger.info("Encode Request Param is "+requestParam);
		String response = this.paypalCallerService.call(requestParam);
		decoder.decode(response);
		if(this.isSuccess(decoder)){
			String profileId = decoder.get("PROFILEID");
			logger.info("Profile Id is"+profileId);
			Invoice invoice = new Invoice();
			invoice.setCreatedDate(new Date());
			invoice.setModifiedDate(new Date());
			invoice.setCustomerId(customer.getId());
			invoice.setProductPackageId(productPackage.getId());
			invoice.setPaypalRecurringPaymentProfileId(profileId);
			invoice.setStatus(decoder.get("STATUS"));
			return invoice;
		}
		else{
			logger.warning("Paypal API has failed");
			return null;
		}
		
		
	}
	
	private String getGMTTimeNow(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		String gmtDate = df.format(new Date());
		logger.info("Current Date is "+gmtDate);
		return gmtDate;
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
	
	private boolean isSuccess(PaypalResponseDecoder decoder){
		logger.info("Success is "+decoder.get("ACK"));
		if(decoder.get("ACK").equalsIgnoreCase("Success")){
			return true;
		}
		else{
			int counter=0;
			String value=decoder.get("L_LONGMESSAGE"+counter);
			while(value!=null){
				logger.warning(value);
				counter++;
				value=decoder.get("L_LONGMESSAGE"+counter);
			}
			return false;
			
		}
	}
	
	

}
