package com.appspot.demo.server.paypal.service;

import java.util.logging.Logger;

import javax.xml.ws.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appspot.demo.server.paypal.dao.InvoiceDao;
import com.appspot.demo.server.paypal.dao.PackageDao;
import com.appspot.demo.server.paypal.dao.PaypalCustomerDao;
import com.appspot.demo.server.paypal.model.ActionSource;
import com.appspot.demo.server.paypal.model.ActionType;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.appspot.demo.server.paypal.service.exception.PaypalException;
import com.appspot.demo.server.paypal.service.util.PaypalIPNVerifier;
import com.appspot.demo.server.paypal.service.util.PaypalResponseDecoder;

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
	
	public void handleIPN(String responseString){
		if(this.paypalIPNVerifier.verifyResponse(responseString)){
			PaypalResponseDecoder decoder = new PaypalResponseDecoder();
			try {
				decoder.decode(responseString);
				String txnType = decoder.get("txn_type");
				if(txnType.equalsIgnoreCase("recurring_payment_profile_created")){
					this.parseRecurringProfileMsg(decoder);
					
				}
				else if(txnType.equalsIgnoreCase("recurring_payment")){
					this.parseRecurringPaymentMsg(decoder);
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
	
	private void parseRecurringProfileMsg(PaypalResponseDecoder decoder){
		String responsePaymentCycle = decoder.get("responsepayment_cycle");
		String lastName = decoder.get("last_name");
		String nextPaymentDate = decoder.get("next_payment_date");
		String residenceCountry = decoder.get("residence_country");
		String initialPaymentAmount = decoder.get("initial_payment_amount");
		String timeCreated = decoder.get("time_created");
		String verifySignature= decoder.get("verify_sign");
		String periodType= decoder.get("period_type");
		String payerStatus= decoder.get("payer_status");
		String tax = decoder.get("tax");
		String payerEmail = decoder.get("payer_email");
		String firstName = decoder.get("first_name");
		String productType = decoder.get("product_type");
		String shipping = decoder.get("shipping");
		String amountPerCycle= decoder.get("amount_per_cycle");
		String amount = decoder.get("amount");
		String status = decoder.get("profile_status");
		String outstandingBalance = decoder.get("outstanding_balance");
		String currencyCode = decoder.get("currency_code");
		String payerId = decoder.get("payer_id");
		String profileId = decoder.get("recurring_payment_id");
		String product_name= decoder.get("product_name");
		
	}
	
	private void parseRecurringPaymentMsg(PaypalResponseDecoder decoder){
		
	}
}
