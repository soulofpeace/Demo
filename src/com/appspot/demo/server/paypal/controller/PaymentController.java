package com.appspot.demo.server.paypal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.appspot.demo.server.paypal.dao.InvoiceDao;
import com.appspot.demo.server.paypal.dao.PackageDao;
import com.appspot.demo.server.paypal.dao.PaypalApplicationUserDao;
import com.appspot.demo.server.paypal.dao.PaypalCustomerDao;

import com.appspot.demo.server.paypal.model.ActionSource;
import com.appspot.demo.server.paypal.model.ActionType;
import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.appspot.demo.server.paypal.service.ActionLoggerService;
import com.appspot.demo.server.paypal.service.MailerService;
import com.appspot.demo.server.paypal.service.PaypalService;
import com.appspot.demo.server.paypal.service.UserInfoService;
import com.appspot.demo.server.paypal.service.exception.PaypalException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@Controller
@RequestMapping("/paypal/payment/")
public class PaymentController {
	private static final Logger logger = Logger.getLogger(PaymentController.class.getName());
	
	@Autowired
	private PaypalService paypalService;
	
	@Autowired
	private PackageDao packageDao;
	
	@Autowired
	private PaypalCustomerDao customerDao;
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	private MailerService mailerService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private PaypalApplicationUserDao appUserDao;
	
	@Autowired
	private ActionLoggerService actionLoggerService;
	
	
	private static final String createURL="http://choonkeedemo.appspot.com/demo/paypal/payment/create";
	
	private static final String cancelURL="http://choonkeedemo.appspot.com/demo/paypal/payment/cancel";
	
	
	@RequestMapping(value="/cancel", method=RequestMethod.GET)
	public String cancelPaymentAgreement(){
		logger.info("Returning cancelled payment view");
		return "cancelPayment";
	}
	
	@RequestMapping(value="/recordCancelledTransaction", method=RequestMethod.POST)
	public String recordCancelledTransaction(@RequestParam("comment")String comment, HttpServletRequest request){
		logger.info("logging cancelled transaction");
		/**
		String email = this.userInfoService.getCurrentUserEmail();
		PaypalApplicationUser appUser = this.appUserDao.getApplicationUserByEmail(email);
		CancelledTransaction cancelledTransaction = new CancelledTransaction();
		cancelledTransaction.setComment(comment);
		cancelledTransaction.setDateCreated(new Date());
		cancelledTransaction.setPaypalApplicationUser(appUser.getId());
		HttpSession session = request.getSession();
		String productPackageId = (String)session.getAttribute("productPackageId");
		cancelledTransaction.setProductPackageKey(KeyFactory.stringToKey(productPackageId));
		this.cancelledTransactionDao.saveCancelledTransaction(cancelledTransaction);
		**/
		HttpSession session = request.getSession();
		String productPackageId = (String)session.getAttribute("productPackageId");
		List<Key> keys = new ArrayList<Key>();
		keys.add(KeyFactory.stringToKey(productPackageId));
		keys.add(this.userInfoService.getCurrentApplicationUser().getId());
		this.actionLoggerService.log(ActionType.CANCELTRANSACTION, ActionSource.WEB, comment, keys);
		return "redirect:http://choonkeedemo.appspot.com/demo/paypal/productpackage/view";
	}
	
	@RequestMapping(value="/start", method=RequestMethod.POST)
	public String startPaymentArrangement(@RequestParam("packageKey")String packageKey, HttpServletRequest request){
		logger.info("Starting Express Checkout");
		logger.info("packageKey is "+packageKey);
		HttpSession session = request.getSession();
		RecurringProductPackage productPackage = packageDao.getPackageById(packageKey);
		try {
			String paypalToken = this.paypalService.setExpressCheckOut(productPackage, createURL, cancelURL);
			session.setAttribute("paypalToken", paypalToken);
			session.setAttribute("productPackageId", packageKey);
			return "redirect:"+paypalService.getPaypalExpressCheckoutURL(paypalToken);
			
		} catch (PaypalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	@RequestMapping(value="/viewPayment", method=RequestMethod.GET)
	public String viewPayment(){
		return "confirmPackage";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createPaymentArrangement(@RequestParam("token")String token, HttpServletRequest request){
		try {
			HttpSession session = request.getSession();
			logger.info("Session paypalToken is "+session.getAttribute("paypalToken"));
			PaypalCustomer customer = this.paypalService.getExpressCheckoutDetails((String) session.getAttribute("paypalToken"));
			PaypalApplicationUser appUser = this.userInfoService.getCurrentApplicationUser();
			logger.info("Customer email is"+customer.getPayerEmail());
			String productId = (String) session.getAttribute("productPackageId");
			logger.info("productPackageId is "+productId);
			if(customer!=null){
				if(customerDao.getPaypalCustomerByPaypalId(customer.getPayerId())==null){
					String customerId= this.customerDao.savePaypalCustomer(customer);
					customer= this.customerDao.getPaypalCustomerById(customerId);
					//customer.getAppUser().add(appUser.getId());
					this.customerDao.savePaypalCustomer(customer);
					//appUser.getPaypalCustomers().add(customer.getId());
					String appUserId  = this.appUserDao.saveApplicationUser(appUser);
					appUser.setId(KeyFactory.stringToKey(appUserId));
					session.setAttribute("customerId", customerId);
					boolean sucess = this.appUserDao.addPaypalCustomertoApplicationUser(customer, appUser);
					logger.info("adding customer to application "+sucess);
					if (customerId!=null){
						List<Key> keys = new ArrayList<Key>();
						keys.add(this.userInfoService.getCurrentApplicationUser().getId());
						keys.add(KeyFactory.stringToKey(customerId));
						keys.add(KeyFactory.stringToKey(productId));
						keys.add(appUser.getId());
	
						this.actionLoggerService.log(ActionType.NEWCUSTOMER, ActionSource.WEB, null, keys);
					}
					else{
						
						List<Key> keys = new ArrayList<Key>();
						keys.add(this.userInfoService.getCurrentApplicationUser().getId());
						keys.add(KeyFactory.stringToKey(productId));
						keys.add(appUser.getId());
						this.actionLoggerService.log(ActionType.FAILEDCUSTOMER, ActionSource.WEB, null, keys);
	
					}
				}
				else{
					customer= this.customerDao.getPaypalCustomerByPaypalId(customer.getPayerId());
					//customer.getAppUser().add(appUser.getId());
					this.customerDao.savePaypalCustomer(customer);
					//appUser.getPaypalCustomers().add(customer.getId());
					String appUserId  = this.appUserDao.saveApplicationUser(appUser);
					appUser.setId(KeyFactory.stringToKey(appUserId));
					String customerId = KeyFactory.keyToString(customerDao.getPaypalCustomerByPaypalId(customer.getPayerId()).getId());
					this.appUserDao.addPaypalCustomertoApplicationUser(customer, appUser);
					session.setAttribute("customerId", customerId);
				}
			
				return "redirect:http://choonkeedemo.appspot.com/demo/paypal/payment/viewPayment?packageKey="+productId;
			}
			else{
				return null;
			}
			
		} catch (PaypalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value="/result", method=RequestMethod.GET)
	public String getPaymentResult(){
		return "result";
	}
	
	
	@RequestMapping(value="/confirm", method=RequestMethod.GET)
	public String confirmPaymentArrangement(HttpServletRequest request){
		try{
			HttpSession session = request.getSession();
			String paypalToken = (String) session.getAttribute("paypalToken");
			logger.info("PaypalToken is "+paypalToken);
			String packageId = (String) session.getAttribute("productPackageId");
			logger.info("product package Id is "+packageId);
			RecurringProductPackage productPackage = this.packageDao.getPackageById(packageId);
			String customerId = (String) session.getAttribute("customerId");
			PaypalCustomer customer = this.customerDao.getPaypalCustomerById(customerId);
			PaypalApplicationUser appUser = this.userInfoService.getCurrentApplicationUser();
			Invoice invoice = this.paypalService.createRecurringPaymentsProfile(customer, productPackage, paypalToken);
			if (invoice !=null){
				invoice.setAppUser(appUser.getId());
				invoice.setProductPackageId(productPackage.getId());
				invoice.setCustomerId(customer.getId());
				String invoiceId =this.invoiceDao.saveInvoice(invoice);
				if(invoiceId !=null){
					logger.info("profile Id is "+invoice.getPaypalRecurringPaymentProfileId());
					this.sendConfirmationEmail(productPackage);
					List<Key> keys = new ArrayList<Key>();
					keys.add(this.userInfoService.getCurrentApplicationUser().getId());
					keys.add(productPackage.getId());
					keys.add(customer.getId());
					keys.add(KeyFactory.stringToKey(invoiceId));
					this.actionLoggerService.log(ActionType.NEWINVOICE, ActionSource.WEB, null, keys);
					return "redirect:http://choonkeedemo.appspot.com/demo/paypal/payment/result?success=true";
				}
				else{
					List<Key> keys = new ArrayList<Key>();
					keys.add(this.userInfoService.getCurrentApplicationUser().getId());
					keys.add(productPackage.getId());
					keys.add(customer.getId());
					this.actionLoggerService.log(ActionType.FAILEDINVOICE, ActionSource.WEB, null, keys);
					return "redirect:http://choonkeedemo.appspot.com/demo/paypal/payment/result?success=false";
				}
				
			}
			else{
				return "redirect:http://choonkeedemo.appspot.com/demo/paypal/payment/result?success=false";
			}
		}
		catch(PaypalException ex){
			ex.printStackTrace();
			return "redirect:http://choonkeedemo.appspot.com/demo/paypal/payment/result?success=false";
		}
		
	}
	
	
	private void sendConfirmationEmail(RecurringProductPackage productPackage){
		String userEmail =this.userInfoService.getCurrentUserEmail();
		String userName = this.userInfoService.getName();
		//String userName = "Choon Kee";
		String subject = "Successful Purchase of Package "+productPackage.getPackageName();
		String greetings="Hi "+userName+",\n";
		String body = "You have successfully purchase "+productPackage.getPackageName()+"\n";
		body +="View the package Details at http://choonkeedemo.appspot.com/demo/paypal/productpackage/view/"+KeyFactory.keyToString(productPackage.getId())+"\n\n";
		String signOff ="From\nThe SocialWok Team\n\n";
		String disclaimer="This is an computer generated email\n";
		String message=greetings+body+signOff+disclaimer;
		this.mailerService.sendMail(userEmail,message, subject);
		
	}

}
