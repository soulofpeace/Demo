package com.appspot.demo.server.paypal.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@Repository
public class InvoiceDaoImpl implements InvoiceDao {
	
	private static final Logger logger= Logger.getLogger(InvoiceDaoImpl.class.getName());
	
	@Autowired
	private PersistenceManagerFactory pmf;
	
	@Override
	public Invoice getInvoiceById(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			Invoice invoice = pm.getObjectById(Invoice.class, idKey);
			return pm.detachCopy(invoice);
		}
		finally{
			pm.close();
		}
		
	}

	/**
	@Override
	public String saveInvoice(Invoice invoice) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			//invoice.setCustomerId(customer.getId());
			//invoice.setProductPackageId(productPackage.getId());
			//invoice.setAppUser(appUser.getId());
			invoice= pm.makePersistent(invoice);
			//customer.getInvoiceKeys().add(invoice.getId());
			//pm.makePersistent(customer);
			//productPackage.getInvoiceKeys().add(invoice.getId());
			//pm.makePersistent(productPackage);
			return KeyFactory.keyToString(invoice.getId());
		}
		finally{
			pm.close();
		}
	}
	**/
	
	

	@Override
	public Invoice getInvoiceByProfileId(String profileId) {
		// TODO Auto-generated method stub
		PersistenceManager pm = this.pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(Invoice.class);
			query.setFilter("paypalRecurringPaymentProfileId == profileIdParam");
			query.declareParameters("String profileIdParam");
			query.setOrdering("createdDate desc");
			List<Invoice> invoices = (List<Invoice>)query.execute(profileId);
			pm.currentTransaction().commit();
			if(invoices.isEmpty()){
				return null;
			}
			else{
				return pm.detachCopy(invoices.get(0));
			}
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public String saveInvoice(Invoice invoice) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			invoice = pm.makePersistent(invoice);
			return KeyFactory.keyToString(invoice.getId());
		}
		finally{
			pm.close();
		}
	}

	@Override
	public Collection<Invoice> getInvoiceByCustomer(PaypalCustomer customer) {
		// TODO Auto-generated method stub
		
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(Invoice.class);
			query.setFilter("customerId == customerIdParam");
			query.declareParameters("com.google.appengine.api.datastore.Key customerIdParam");
			query.setOrdering("createdDate desc");
			Collection<Invoice> invoices =pm.detachCopyAll((Collection<Invoice>) query.execute(customer.getId()));
			pm.currentTransaction().commit();
			return invoices;
			
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public Collection<Invoice> getInvoiceByPackage(
			RecurringProductPackage productPackage) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(Invoice.class);
			query.setFilter("productPackageId == productPackageIdParam");
			query.declareParameters("com.google.appengine.api.datastore.Key productPackageIdParam");
			query.setOrdering("createdDate desc");
			Collection<Invoice> invoices = pm.detachCopyAll((Collection<Invoice> )query.execute(productPackage.getId()));
			pm.currentTransaction().commit();
			return invoices;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
	}

	@Override
	public Collection<Invoice> getInvoiceByAppUser(PaypalApplicationUser appUser) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(Invoice.class);
			query.setFilter("appUser == appUserParam");
			query.setOrdering("createdDate desc");
			query.declareParameters("com.google.appengine.api.datastore.Key appUserParam");
			Collection<Invoice> invoices = pm.detachCopyAll((Collection<Invoice>)query.execute(appUser.getId()));
			pm.currentTransaction().commit();
			return invoices;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public Collection<Invoice> getAllInvoice() {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(Invoice.class);
			query.setOrdering("createdDate desc");
			Collection<Invoice> invoices = pm.detachCopyAll((Collection<Invoice>)query.execute());
			pm.currentTransaction().commit();
			return invoices;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
	}

	@Override
	public Collection<Invoice> getInvoiceByDate(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query =  pm.newQuery(Invoice.class);
			query.setOrdering("createdDate desc");
			query.setFilter("createdDate >=startDateParam && createdDate<=endDateParam");
			query.declareParameters("java.util.Date startDateParam, java.util.Date endDateParam");
			Collection<Invoice> invoices = pm.detachCopyAll((Collection<Invoice>)query.execute(startDate, endDate));
			pm.currentTransaction().commit();
			return invoices;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public Collection<Invoice> getInvoiceByDate(Date startDate, Date endDate,
			PaypalApplicationUser appUser) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(Invoice.class);
			query.setOrdering("createdDate desc");
			query.setFilter("createdDate >=startDateParam && createdDate<=endDateParam && appUser==appUserParam");
			query.declareParameters("java.util.Date startDateParam, java.util.Date endDateParam, com.google.appengine.api.datastore.Key appUserParam");
			Collection<Invoice> invoices = pm.detachCopyAll((Collection<Invoice>)query.execute(startDate, endDate,  appUser.getId()));
			pm.currentTransaction().commit();
			return invoices;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public Collection<Invoice> getInvoiceWithFailedTransaction() {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Query query = pm.newQuery("select from com.appspot.demo.server.paypal.model.PaypalTransaction where paymentStatus != 'Completed' order by paymentStatus, dateCreated desc");
		Collection<Invoice> invoiceList = new HashSet<Invoice>();
		List<PaypalTransaction> transactions =(List<PaypalTransaction>) query.execute();
		if(transactions.iterator().hasNext()){
			for(PaypalTransaction transaction: transactions){
				Key invoiceId = transaction.getInvoiceId();
				Invoice invoice = pm.getObjectById(Invoice.class, invoiceId);
				invoiceList.add(pm.detachCopy(invoice));
			}
		}
		return invoiceList;
	}

	@Override
	public Collection<Invoice> getInvoiceWithFailedTransaction(Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		//Query query = pm.newQuery("select from com.appspot.demo.server.paypal.model.PaypalTransaction where paymentStatus != 'Completed' and createdDate >= :startDate and createdDate <= :endDate order by paymentStatus, dateCreated desc PARAMETERS java.util.Date startDate, java.util.Date endDate");
		Query query = pm.newQuery("select id from "+ PaypalTransaction.class.getName());
		query.setFilter("paymentStatus != 'Completed'");
		List ids = (List) query.execute();
		logger.info("ids length is "+ids.size());
		Collection<Invoice> invoiceList = new HashSet<Invoice>();
		if(ids.size()>0){
			query = pm.newQuery(PaypalTransaction.class);
			query.setFilter("dateCreated >= startDate && dateCreated <= endDate && ids.contains(id)");
			query.declareParameters("java.util.Date startDate, java.util.Date endDate, java.util.List ids");
			query.setOrdering("dateCreated desc");
		
			List<PaypalTransaction> transactions =(List<PaypalTransaction>) query.execute(startDate, endDate, ids);
			if(transactions.iterator().hasNext()){
				for(PaypalTransaction transaction: transactions){
					Key invoiceId = transaction.getInvoiceId();
					Invoice invoice = pm.getObjectById(Invoice.class, invoiceId);
					invoiceList.add(pm.detachCopy(invoice));
				}
			}
		}
		return invoiceList;
	}

	@Override
	public Collection<Invoice> getInvoiceWithFailedTransaction(
			PaypalApplicationUser paypalApplicationUser) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		//Query query = pm.newQuery("select from com.appspot.demo.server.paypal.model.PaypalTransaction where paymentStatus != 'Completed' and appUser == :appUserParam order by paymentStatus, dateCreated desc PARAMETERS com.google.appengine.api.datastore.Key appUserParam");
		Query query = pm.newQuery("select id from "+Invoice.class.getName());
		query.setFilter("appUser == appUserParam");
		query.declareParameters("com.google.appengine.api.datastore.Key appUserParam");
		logger.info("ApplicationUserId is "+paypalApplicationUser.getId());
		List ids = (List) query.execute(paypalApplicationUser.getId());
		Collection<Invoice> invoiceList = new HashSet<Invoice>();
		if(ids.size()>0){
			query = pm.newQuery(PaypalTransaction.class);
			query.setFilter("paymentStatus!= 'Completed' && ids.contains(invoiceId)");
			query.setOrdering("paymentStatus");
			query.declareParameters("java.util.List ids");
			
			List<PaypalTransaction> transactions =(List<PaypalTransaction>) query.execute(ids);
			if(transactions.iterator().hasNext()){
				for(PaypalTransaction transaction: transactions){
					Key invoiceId = transaction.getInvoiceId();
					Invoice invoice = pm.getObjectById(Invoice.class, invoiceId);
					invoiceList.add(pm.detachCopy(invoice));
				}
			}
		}
		return invoiceList;
	}

	@Override
	public Collection<Invoice> getInvoiceWithFailedTransaction(Date startDate,
			Date endDate, PaypalApplicationUser paypalApplicationUser) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		//Query query = pm.newQuery("select from com.appspot.demo.server.paypal.model.PaypalTransaction where paymentStatus != 'Completed' and appUser == :appUserParam order by paymentStatus, dateCreated desc PARAMETERS com.google.appengine.api.datastore.Key appUserParam");
		Query query = pm.newQuery("select id from "+Invoice.class.getName());
		query.setFilter("appUser == appUserParam");
		query.declareParameters("com.google.appengine.api.datastore.Key appUserParam");
		logger.info("ApplicationUserId is "+paypalApplicationUser.getId());
		List ids = (List) query.execute(paypalApplicationUser.getId());
		Collection<Invoice> invoiceList = new HashSet<Invoice>();
		if(ids.size()>0){
			query = pm.newQuery("select id from "+ PaypalTransaction.class.getName());
			query.setFilter("paymentStatus!= 'Completed' && ids.contains(invoiceId)");
			query.setOrdering("paymentStatus");
			query.declareParameters("java.util.List ids");
			List txnIds = (List) query.execute(ids);
			if(txnIds.size()>0){
				query = pm.newQuery(PaypalTransaction.class);
				query.setFilter("dateCreated >= startDate && dateCreated <= endDate && txnIds.contains(id)");
				query.declareParameters("java.util.Date startDate, java.util.Date endDate, java.util.List txnIds");
				query.setOrdering("dateCreated desc");
			
				List<PaypalTransaction> transactions =(List<PaypalTransaction>) query.execute(startDate, endDate, txnIds);
				if(transactions.iterator().hasNext()){
					for(PaypalTransaction transaction: transactions){
						Key invoiceId = transaction.getInvoiceId();
						Invoice invoice = pm.getObjectById(Invoice.class, invoiceId);
						invoiceList.add(pm.detachCopy(invoice));
					}
				}
			}
		}
		return invoiceList;
	}
	
	

}
