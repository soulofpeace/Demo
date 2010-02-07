package com.appspot.demo.server.paypal.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.appspot.demo.client.paypal.dto.InvoiceDto;
import com.appspot.demo.server.paypal.dao.InvoiceDao;
import com.appspot.demo.server.paypal.dao.PackageDao;
import com.appspot.demo.server.paypal.dao.PaypalCustomerDao;
import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.appspot.demo.server.paypal.service.UserInfoService;
import com.google.appengine.api.datastore.KeyFactory;





@Controller
@RequestMapping("paypal/invoice/")
public class PaypalInvoiceController {
	private static final Logger logger = Logger.getLogger(PaypalInvoiceController.class.getName());
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	private PaypalCustomerDao paypalCustomerDao;
	
	@Autowired
	private PackageDao packageDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/get",  method=RequestMethod.GET)
	public String getInvoiceByUserId(Model model){
		Collection<Invoice> invoices = this.invoiceDao.getInvoiceByAppUser(this.userInfoService.getCurrentApplicationUser());
		List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
		for(Invoice invoice: invoices){
			invoiceDtos.add(this.convertInvoiceToDto(invoice));
		}
		model.addAttribute("invoiceDtoList", invoiceDtos);
		return "invoiceDtoList";
	}
	
	private InvoiceDto convertInvoiceToDto(Invoice invoice){
		PaypalCustomer customer = paypalCustomerDao.getPaypalCustomerById(KeyFactory.keyToString(invoice.getCustomerId()));
		RecurringProductPackage productPackage = packageDao.getPackageById(KeyFactory.keyToString(invoice.getProductPackageId()));
		
		InvoiceDto invoiceDto = new InvoiceDto();
		invoiceDto.setAppUserId(KeyFactory.keyToString(invoice.getId()));
		invoiceDto.setCustomerId(KeyFactory.keyToString(invoice.getCustomerId()));
		invoiceDto.setCustomerEmail(customer.getPayerEmail());
		invoiceDto.setProductPackageId(KeyFactory.keyToString(invoice.getProductPackageId()));
		invoiceDto.setProductPackageName(productPackage.getPackageName());
		invoiceDto.setPaypalRecurringPaymentProfileId(invoice.getPaypalRecurringPaymentProfileId());
		invoiceDto.setCreatedDate(invoice.getCreatedDate().toString());
		invoiceDto.setNextPaymentDate(invoice.getNextPaymentDate().toString());
		invoiceDto.setCurrencyCode(invoice.getCurrencyCode());
		invoiceDto.setInitalPaymentAmount(String.valueOf(invoice.getInitialPaymentAmount()));
		invoiceDto.setTax(String.valueOf(invoice.getTax()));
		invoiceDto.setShipping(String.valueOf(invoice.getShipping()));
		
		return invoiceDto;
		
		
		
	}
}
