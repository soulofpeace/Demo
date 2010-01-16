package com.appspot.demo.server.paypal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.appspot.demo.server.paypal.dao.PaypalCustomerDao;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.appspot.demo.server.paypal.service.MailerService;
import com.appspot.demo.server.paypal.service.PaypalService;
import com.appspot.demo.server.paypal.service.UserInfoService;
import com.appspot.demo.server.paypal.service.exception.PaypalException;
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
	
	private static final String createURL="http://choonkeedemo.appspot.com/demo/paypal/payment/create";
	
	private static final String cancelURL="http://choonkeedemo.appspot.com/demo/paypal/payment/cancel";
	
	
	
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
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createPaymentArrangement(@RequestParam("token")String token, HttpServletRequest request){
		try {
			HttpSession session = request.getSession();
			logger.info("Session paypalToken is "+session.getAttribute("paypalToken"));
			PaypalCustomer customer = this.paypalService.getExpressCheckoutDetails((String) session.getAttribute("paypalToken"));
			logger.info("Customer email is"+customer.getPayerEmail());
			String productId = (String) session.getAttribute("productPackageId");
			logger.info("productPackageId is "+productId);
			if(customer!=null){
				if(customerDao.getPaypalCustomerByPaypalId(customer.getPayerId())==null){
					String customerId= this.customerDao.savePaypalCustomer(customer);
					session.setAttribute("customerId", customerId);
				}
				else{
					String customerId = KeyFactory.keyToString(customerDao.getPaypalCustomerByPaypalId(customer.getPayerId()).getId());
					session.setAttribute("customerId", customerId);
				}
			
				return "redirect:http://choonkeedemo.appspot.com/demo/paypal/confirmPackage.html?packageKey="+productId;
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
			Invoice invoice = this.paypalService.createRecurringPaymentsProfile(customer, productPackage, paypalToken);
			if (invoice !=null){
				this.invoiceDao.saveInvoice(customer, productPackage, invoice);
				logger.info("profile Id is "+invoice.getPaypalRecurringPaymentProfileId());
				this.sendConfirmationEmail(productPackage);
				return "redirect:http://choonkeedemo.appspot.com/demo/paypal/result.html?success=true";
			}
			else{
				return "redirect:http://choonkeedemo.appspot.com/demo/paypal/result.html?success=false";
			}
		}
		catch(PaypalException ex){
			ex.printStackTrace();
			return "redirect:http://choonkeedemo.appspot.com/demo/paypal/result.html?success=false";
		}
		
	}
	
	
	private void sendConfirmationEmail(RecurringProductPackage productPackage){
		String userEmail ="soulofpeace@gmail.com";
		//String userName = this.userInfoService.getName();
		String userName = "Choon Kee";
		String subject = "Successful Purchase of Package "+productPackage.getPackageName();
		String greetings="Hi "+userName+",\n";
		String body = "You have successfully purchase "+productPackage.getPackageName()+"\n";
		body +="View the package Details at http://choonkeedemo.appspot.com/demo/paypal/productpackage/get/"+KeyFactory.keyToString(productPackage.getId())+"\n";
		String signOff ="From\n The SocialWok Team\n\n";
		String disclaimer="This is an autogenerated email\n";
		String message=greetings+body+signOff+disclaimer;
		this.mailerService.sendMail(userEmail,message, subject);
		
	}

}
