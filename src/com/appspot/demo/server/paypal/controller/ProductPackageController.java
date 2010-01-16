package com.appspot.demo.server.paypal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appspot.demo.client.paypal.dto.ProductPackageDto;
import com.appspot.demo.server.paypal.dao.PackageDao;

import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.google.appengine.api.datastore.KeyFactory;


@Controller
@RequestMapping("/paypal/productpackage")
public class ProductPackageController {
	private static final Logger logger = Logger.getLogger(ProductPackageController.class.getName());
	@Autowired
	private PackageDao packageDao;
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String viewAll(){
		return "Paypal";
	}
	
	@RequestMapping(value="/get", method=RequestMethod.GET)
	public ArrayList<ProductPackageDto> getPackages(){
		List<RecurringProductPackage> productPackages = this.packageDao.getAllPackages();
		ArrayList<ProductPackageDto> results = new ArrayList<ProductPackageDto>();
		if(productPackages.iterator().hasNext()){
			for(RecurringProductPackage productPackage: productPackages){
				results.add(this.convertPackageToDto(productPackage));
			}
		}
		return results;
		
	}
	
	@RequestMapping(value="/get/{packageKey}", method=RequestMethod.GET)
	public ProductPackageDto getPackageById(@PathVariable String packageKey){
		RecurringProductPackage productPackage = this.packageDao.getPackageById(packageKey);
		return this.convertPackageToDto(productPackage);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addPackage(@RequestParam("packageImageURL")String packageImageURL, @RequestParam("packageName")String packageName, @RequestParam("packageDescription")String packageDescription, @RequestParam("moreInformation")String moreInformation, @RequestParam("packageCost")double packageCost, @RequestParam("billingPeriod")String billingPeriod, @RequestParam("billingFrequency")int billingFrequency, HttpServletResponse response, HttpServletRequest request){
		logger.info("Adding package");
		RecurringProductPackage productPackage = new RecurringProductPackage();
		productPackage.setPackageImageURL(packageImageURL);
		productPackage.setPackageName(packageName);
		productPackage.setPackageDescription(packageDescription);
		productPackage.setMoreInformation(moreInformation);
		productPackage.setPackageCost(packageCost);
		productPackage.setBillingFrequency(billingFrequency);
		productPackage.setBillingPeriod(billingPeriod);
		packageDao.savePackage(productPackage);
		
		//testing codes for testing datastore
		/**
		PaypalCustomer customer = new PaypalCustomer();
		customer.setPayerEmail("testing@gmail.com");
		paypalCustomerDao.savePaypalCustomer(customer);
		
		Invoice invoice = new Invoice();
		invoice.setPaypalRecurringPaymentProfileId("testing");
		invoiceDao.saveInvoice(customer, productPackage, invoice);
		
		**/
		/**
		//testing codes for testing paypal API
		NVPCallerServices caller = new NVPCallerServices();
		NVPEncoder encoder = new NVPEncoder();
		NVPDecoder decoder = new NVPDecoder();
		
		
		try {
			logger.info("Calling Paypal");
			
			APIProfile profile = ProfileFactory.createSignatureAPIProfile();
			profile.setAPIUsername("sell1_1256008723_biz_api1.socialwok.com");
			profile.setAPIPassword("1256008736");
			profile.setSignature("AyAQA1q4x3jihgYNo.MlslDeJ.h3AKyuoMd142QcPSPklX6uVwULY.87");
			profile.setEnvironment("sandbox");
			profile.setSubject("");
			caller.setAPIProfile(profile);
			encoder.add("VERSION", "51.0");			
			encoder.add("METHOD","SetExpressCheckout");
			encoder.add("RETURNURL", "http://choonkeedemo/demo/paypal/Paypal.html");
			encoder.add("CANCELURL", "http://choonkeedemo/demo/paypal/Paypal.html");	
			encoder.add("AMT", "50");
			encoder.add("PAYMENTACTION","Sale");
			encoder.add("CURRENCYCODE","USD");
			String NVPRequest;
			NVPRequest = encoder.encode();
			String NVPResponse =caller.call(NVPRequest);
			decoder.decode(NVPResponse);
			logger.info(decoder.get("TOKEN"));
			
			
		} catch (PayPalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String signature="AyAQA1q4x3jihgYNo.MlslDeJ.h3AKyuoMd142QcPSPklX6uVwULY.87";
		String password="1256008736";
		
		try {
			signature = URLEncoder.encode(signature, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			password = URLEncoder.encode(password, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		String URL="https://api-3t.sandbox.paypal.com/nvp?CANCELURL=http%3A%2F%2Fchoonkeedemo%2Fdemo%2Fpaypal%2FPaypal.html&VERSION=56.0&AMT=50&RETURNURL=http%3A%2F%2Fchoonkeedemo%2Fdemo%2Fpaypal%2FPaypal.html&PAYMENTACTION=Sale&METHOD=SetExpressCheckout&CURRENCYCODE=USD&PWD="+password+"&SOURCE=PAYPAL_JAVA_SDK_60&VERSION=60.0&SIGNATURE="+signature+"&USER=sell1_1256008723_biz_api1.socialwok.com";
		logger.info(URL);
		logger.info(this.getURLResponse(URL));
		
		response.setContentType("text/html");
		
		try {
			logger.info("Writing Output");
			PrintWriter pw = response.getWriter();
			pw.println("Suceess");
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String paypalToken = this.paypalService.setExpressCheckOut(productPackage);
			session.setAttribute("paypalToken", paypalToken);
			logger.info("Session paypalToken is "+session.getAttribute("paypalToken"));
			PaypalCustomer customer = this.paypalService.getExpressCheckoutDetails((String) session.getAttribute("paypalToken"));
			logger.info("Customer email is"+customer.getPayerEmail());
		} catch (PaypalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
		return null;
	}
	
	
	private ProductPackageDto convertPackageToDto(RecurringProductPackage productPackage){
		ProductPackageDto result = new ProductPackageDto();
		result.setKey(KeyFactory.keyToString(productPackage.getId()));
		result.setBillingFrequency(String.valueOf(productPackage.getBillingFrequency()));
		result.setBillingPeriod(productPackage.getBillingPeriod());
		result.setPackageCost(String.valueOf(productPackage.getPackageCost()));
		result.setPackageDescription(productPackage.getPackageDescription());
		result.setMoreInformation(productPackage.getMoreInformation());
		result.setPackageImageURL(productPackage.getPackageImageURL());
		result.setPackageName(productPackage.getPackageName());
		return result;
	}
	
	/**
	private String getURLResponse(String urlString){
		try {
			URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            String line;
            String output="";
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
            	output+=line;
            }
            return output;
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
            return null;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return null;
        }
		
	}
	**/
	
}
