package com.appspot.demo.server.paypal.controller;

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
import com.appspot.demo.server.paypal.model.ProductPackage;


@Controller
@RequestMapping("/package/")
public class ProductPackageController {
	
	@Autowired
	private PackageDao packageDao;
	
	@Autowired 
	private InvoiceDao invoiceDao;
	
	@Autowired
	private PaypalCustomerDao paypalCustomerDao;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void addPackage(@RequestParam("packageImageURL")String packageImageURL, @RequestParam("packageName")String packageName, @RequestParam("packageDescription")String packageDescription, @RequestParam("packageCost")double packageCost, @RequestParam("billingPeriod")String billingPeriod, @RequestParam("billingFrequency")int billingFrequency){
		ProductPackage productPackage = new ProductPackage();
		productPackage.setPackageImageURL(packageImageURL);
		productPackage.setPackageName(packageName);
		productPackage.setPackageDescription(packageDescription);
		productPackage.setPackageCost(packageCost);
		productPackage.setBillingFrequency(billingFrequency);
		productPackage.setBillingPeriod(billingPeriod);
		packageDao.savePackage(productPackage);
		
		//testing codes
		PaypalCustomer customer = new PaypalCustomer();
		customer.setPayerEmail("testing@gmail.com");
		paypalCustomerDao.savePaypalCustomer(customer);
		
		Invoice invoice = new Invoice();
		invoice.setPaypalRecurringPaymentProfileId("testing");
		invoiceDao.saveInvoice(customer, productPackage, invoice);
		
	}
}
