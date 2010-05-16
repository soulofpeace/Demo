package com.appspot.demo.server.paypal.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.appspot.demo.server.paypal.dao.ActionLogDao;
import com.appspot.demo.server.paypal.dao.InvoiceDao;
import com.appspot.demo.server.paypal.dao.PackageDao;
import com.appspot.demo.server.paypal.dao.PaypalCustomerDao;
import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.appspot.demo.server.paypal.model.Role;
import com.appspot.demo.server.paypal.service.ActionLoggerService;
import com.appspot.demo.server.paypal.service.UserInfoService;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.i18n.client.DateTimeFormat;




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
	
	@RequestMapping(value="/admin/get", method=RequestMethod.GET)
	public String getAllInvoice(Model model){
		Collection<Invoice> invoices = this.invoiceDao.getAllInvoice();
		List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
		for(Invoice invoice: invoices){
			invoiceDtos.add(this.convertInvoiceToDto(invoice));
		}
		model.addAttribute("invoiceDtoList", invoiceDtos);
		return "invoiceDtoList";
		
	}
	
	@RequestMapping(value="/startDate/{startDate}/endDate/{endDate}/get", method=RequestMethod.GET)
	public String getInvoiceBetween(@PathVariable String startDate, @PathVariable String endDate, Model model){
		logger.info("Start date is "+ startDate);
		logger.info("End Date is "+ endDate);
		Date start = this.parseDate(startDate);
		Date end = this.parseDate(endDate);
		if(start != null && end !=null){
			Collection<Invoice> invoices = this.invoiceDao.getInvoiceByDate(start, end, this.userInfoService.getCurrentApplicationUser());
			List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
			for(Invoice invoice: invoices){
				invoiceDtos.add(this.convertInvoiceToDto(invoice));
			}
			model.addAttribute("invoiceDtoList", invoiceDtos);
			return "invoiceDtoList";
		}
		else{
			return null;
		}
	}
	
	@RequestMapping(value="/admin/startDate/{startDate}/endDate/{endDate}/get", method=RequestMethod.GET)
	public String getAllInvoiceBetween(@PathVariable String startDate, @PathVariable String endDate, Model model){
		logger.info("Start date is "+ startDate);
		logger.info("End Date is "+ endDate);
		Date start = this.parseDate(startDate);
		Date end = this.parseDate(endDate);
		if(start != null && end !=null){
			Collection<Invoice> invoices = this.invoiceDao.getInvoiceByDate(start, end);
			List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
			for(Invoice invoice: invoices){
				invoiceDtos.add(this.convertInvoiceToDto(invoice));
			}
			model.addAttribute("invoiceDtoList", invoiceDtos);
			return "invoiceDtoList";
		}
		else{
			return null;
		}
	}
	
	@RequestMapping(value="/failed/get", method=RequestMethod.GET)
	public String getInvoiceWithFailedTransaction(Model model){
		Collection<Invoice> invoices = this.invoiceDao.getInvoiceWithFailedTransaction(this.userInfoService.getCurrentApplicationUser());
		List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
		for(Invoice invoice: invoices){
			invoiceDtos.add(this.convertInvoiceToDto(invoice));
		}
		model.addAttribute("invoiceDtoList", invoiceDtos);
		return "invoiceDtoList";		
	}
	
	@RequestMapping(value="/failed/startDate/{startDate}/endDate/{endDate}/get")
	public String getInvoiceWithFailedTransaction(@PathVariable String startDate, @PathVariable String endDate, Model model){
		logger.info("Start date is "+ startDate);
		logger.info("End Date is "+ endDate);
		Date start = this.parseDate(startDate);
		Date end = this.parseDate(endDate);
		if(start != null && end !=null){
			Collection<Invoice> invoices = this.invoiceDao.getInvoiceWithFailedTransaction(start, end, this.userInfoService.getCurrentApplicationUser());
			List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
			for(Invoice invoice: invoices){
				invoiceDtos.add(this.convertInvoiceToDto(invoice));
			}
			model.addAttribute("invoiceDtoList", invoiceDtos);
			return "invoiceDtoList";
		}
		else{
			return null;
		}
	}
	
	@RequestMapping(value="/admin/failed/get" ,method=RequestMethod.GET)
	public String getAllInvoiceWithFailedTransaction(Model model){
		Collection<Invoice> invoices = this.invoiceDao.getInvoiceWithFailedTransaction();
		List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
		for(Invoice invoice: invoices){
			invoiceDtos.add(this.convertInvoiceToDto(invoice));
		}
		model.addAttribute("invoiceDtoList", invoiceDtos);
		return "invoiceDtoList";
			
	}
	
	@RequestMapping(value="/admin/failed/startDate/{startDate}/endDate/{endDate}/get")
	public String getAllInvoiceWithFailedTransaction(@PathVariable String startDate, @PathVariable String endDate, Model model){
		logger.info("Start date is "+ startDate);
		logger.info("End Date is "+ endDate);
		Date start = this.parseDate(startDate);
		Date end = this.parseDate(endDate);
		if(start != null && end !=null){
			Collection<Invoice> invoices = this.invoiceDao.getInvoiceWithFailedTransaction(start, end);
			List<InvoiceDto> invoiceDtos = new ArrayList<InvoiceDto>();
			for(Invoice invoice: invoices){
				invoiceDtos.add(this.convertInvoiceToDto(invoice));
			}
			model.addAttribute("invoiceDtoList", invoiceDtos);
			return "invoiceDtoList";
		}
		else{
			return null;
		}
	}
	
	private Date parseDate(String date){
		//SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.warning("Unable to parse Date");
			return null;
		}
	}
	
	private InvoiceDto convertInvoiceToDto(Invoice invoice){
		PaypalCustomer customer = paypalCustomerDao.getPaypalCustomerById(KeyFactory.keyToString(invoice.getCustomerId()));
		RecurringProductPackage productPackage = packageDao.getPackageById(KeyFactory.keyToString(invoice.getProductPackageId()));
		
		InvoiceDto invoiceDto = new InvoiceDto();
		invoiceDto.setKey(KeyFactory.keyToString(invoice.getId()));
		invoiceDto.setStatus(invoice.getStatus());
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
		invoiceDto.setOutstandingBalance(String.valueOf(invoice.getOutstandingBalance()));
		invoiceDto.setModifiedDate(invoice.getModifiedDate().toString());
		return invoiceDto;
			
	}
	
}
