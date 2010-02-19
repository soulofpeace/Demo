package com.appspot.demo.server.paypal.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appspot.demo.client.paypal.dto.PaypalTransactionDto;
import com.appspot.demo.server.paypal.dao.InvoiceDao;
import com.appspot.demo.server.paypal.dao.PackageDao;
import com.appspot.demo.server.paypal.dao.PaypalCustomerDao;
import com.appspot.demo.server.paypal.dao.PaypalTransactionDao;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.appspot.demo.server.paypal.service.UserInfoService;
import com.google.appengine.api.datastore.KeyFactory;




@Controller
@RequestMapping("paypal/transaction")
public class PaypalTransactionController {
	private static final Logger logger = Logger.getLogger(PaypalTransactionController.class.getName());
	
	@Autowired
	private PaypalTransactionDao paypalTransactionDao;
	
	@Autowired
	private PackageDao packageDao;
	
	@Autowired
	private PaypalCustomerDao paypalCustomerDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@RequestMapping(value="/invoice/{invoiceId}/get", method=RequestMethod.GET)
	public String getTransactionByInvoiceId(@PathVariable String invoiceId, Model model){
		Collection<PaypalTransaction> transactions =this.paypalTransactionDao.getTransactions(this.invoiceDao.getInvoiceById(invoiceId));
		ArrayList<PaypalTransactionDto> transactionDtos = new ArrayList<PaypalTransactionDto>();
		for (PaypalTransaction transaction: transactions){
			transactionDtos.add(this.convertPaypalTransactionToDto(transaction));
		}
		model.addAttribute("transactionDtoList", transactionDtos);
		return "transactionDtoList";
	}
	
	private PaypalTransactionDto convertPaypalTransactionToDto(PaypalTransaction paypalTransaction){
		PaypalTransactionDto paypalTransactionDto = new PaypalTransactionDto();
		paypalTransactionDto.setKey(KeyFactory.keyToString(paypalTransaction.getId()));
		paypalTransactionDto.setDateCreated(paypalTransaction.getDateCreated());
		//paypalTransactionDto.setDateCreated(paypalTransaction.getDateCreatedIndex().getDateCreated());
		paypalTransactionDto.setMcGross(String.valueOf(paypalTransaction.getMcGross()));
		paypalTransactionDto.setMcFee(String.valueOf(paypalTransaction.getMcFee()));
		paypalTransactionDto.setMcHandling(String.valueOf(paypalTransaction.getMcHandling()));
		paypalTransactionDto.setMcCurrency(paypalTransaction.getMcCurrency());
		paypalTransactionDto.setInvoiceId(KeyFactory.keyToString(paypalTransaction.getInvoiceId()));
		paypalTransactionDto.setTransactionId(paypalTransaction.getTransactionId());
		paypalTransactionDto.setParentTransactionId(paypalTransaction.getParentTransactionId());
		paypalTransactionDto.setReceiptId(paypalTransaction.getReceiptId());
		paypalTransactionDto.setTransactionType(paypalTransaction.getTransactionType());
		paypalTransactionDto.setPaymentType(paypalTransaction.getPaymentType());
		paypalTransactionDto.setOrderTime(paypalTransaction.getOrderTime());
		paypalTransactionDto.setAmount(String.valueOf(paypalTransaction.getAmount()));
		paypalTransactionDto.setCurrencyCode(paypalTransaction.getCurrencyCode());
		paypalTransactionDto.setFeeAmount(String.valueOf(paypalTransaction.getFeeAmount()));
		paypalTransactionDto.setSettleAmount(String.valueOf(paypalTransaction.getSettleAmount()));
		paypalTransactionDto.setTaxAmount(String.valueOf(paypalTransaction.getTaxAmount()));
		paypalTransactionDto.setExchangeRate(String.valueOf(paypalTransaction.getExchangeRate()));
		paypalTransactionDto.setPendingReason(String.valueOf(paypalTransaction.getPendingReason()));
		paypalTransactionDto.setReasonCode(paypalTransaction.getReasonCode());
		paypalTransactionDto.setProtectionEligibility(paypalTransaction.getProtectionEligibility());
		paypalTransactionDto.setInvnum(paypalTransaction.getInvnum());
		paypalTransactionDto.setCustom(paypalTransaction.getCustom());
		paypalTransactionDto.setNote(paypalTransaction.getNote());
		paypalTransactionDto.setSalesTax(String.valueOf(paypalTransaction.getSalestax()));
		paypalTransactionDto.setPaymentDate(paypalTransaction.getPaymentDate());
		paypalTransactionDto.setSubject(paypalTransaction.getSubject());
		paypalTransactionDto.setShipping(String.valueOf(paypalTransaction.getShipping()));
		paypalTransactionDto.setPaymentGross(String.valueOf(paypalTransaction.getPaymentGross()));
		paypalTransactionDto.setPaymentStatus(paypalTransaction.getPaymentStatus());
		paypalTransactionDto.setPaymentItemDescription(paypalTransaction.getPaymentItemDescription());
		paypalTransactionDto.setPaymentItemNumber(paypalTransaction.getPaymentItemNumber());
		paypalTransactionDto.setPaymentItemQuantity(paypalTransaction.getPaymentItemquantity());
		ArrayList<String> paymentItemAmount = new ArrayList<String>();
		for (Double itemAmount : paypalTransaction.getPaymentItemAmount()){
			paymentItemAmount.add(String.valueOf(itemAmount));
		}
		paypalTransactionDto.setPaymentItemAmount(paymentItemAmount);
		paypalTransactionDto.setPaymentItemOptionName(paypalTransaction.getPaymentItemOptionName());
		paypalTransactionDto.setPaymentItemOptionValue(paypalTransaction.getPaymentItemOptionValue());
		
		return paypalTransactionDto;
	}

}
