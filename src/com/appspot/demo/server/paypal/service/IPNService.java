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
				String txnType = decoder.get("txnType");
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
			this.actionLoggerService.log(ActionType.NOTVERIFIEDIPN, ActionSource.PAYPAL, responseString, null);
		}
	}
	
	private void parseRecurringProfileMsg(PaypalResponseDecoder decoder){
		
	}
	
	private void parseRecurringPaymentMsg(PaypalResponseDecoder decoder){
		
	}
}
