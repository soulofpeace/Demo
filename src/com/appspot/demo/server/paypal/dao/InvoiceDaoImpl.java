package com.appspot.demo.server.paypal.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.InvoiceTransaction;
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

	@Override
	//public String saveInvoice(PaypalCustomer customer, RecurringProductPackage productPackage, Invoice invoice, PaypalApplicationUser appUser) {
	public String saveInvoice(Invoice invoice) {
		
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			invoice = pm.makePersistent(invoice);
			//invoice.setCustomerId(customer.getId());
			//invoice.setProductPackageId(productPackage.getId());
			//invoice.setAppUser(appUser.getId());
			//invoice= pm.makePersistent(invoice);
			//customer.addInvoice(invoice.getId());
			//pm.makePersistent(customer);
			//productPackage.addInvoice(invoice.getId());
			//pm.makePersistent(productPackage);
			pm.currentTransaction().commit();
			return KeyFactory.keyToString(invoice.getId());
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}
	
	
	

	@Override
	public Invoice getInvoiceByProfileId(String profileId) {
		// TODO Auto-generated method stub
		PersistenceManager pm = this.pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(Invoice.class);
			query.setFilter("paypalRecurringPaymentProfileId == profileIdParam");
			query.declareParameters("String profileIdParam");
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
	public void addTransactionToInvoice(Invoice invoice, PaypalTransaction transaction) {
		// TODO Auto-generated method stub
		PersistenceManager pm = this.pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(InvoiceTransaction.class);
			query.setFilter("invoice == invoiceParam");
			query.declareParameters("Invoice invoiceParam");
			query.setUnique(true);
			InvoiceTransaction invoiceTransaction = (InvoiceTransaction) query.execute(invoice);
			if (invoiceTransaction!=null){
				logger.info("InvoiceTransaction already exists");
				invoiceTransaction.getTransactions().add(transaction.getId());
				invoiceTransaction.setModifiedDate(new Date());
				pm.makePersistent(invoiceTransaction);
			}
			else{
				logger.info("New Transaction created");
				invoiceTransaction = new InvoiceTransaction();
				invoiceTransaction.getTransactions().add(transaction.getId());
				invoiceTransaction.setCreatedDate(new Date());
				invoiceTransaction.setModifiedDate(new Date());
				invoice.setInvoiceTransaction(invoiceTransaction);
				pm.makePersistent(invoice);
			}
			pm.currentTransaction().commit();
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Transactions failed to commit");
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
	}
	
	public List<PaypalTransaction> getPaypalTransactions(Invoice invoice){
		PersistenceManager pm = this.pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		List<PaypalTransaction> paypalTransactions = new ArrayList<PaypalTransaction>();
		try{
			Query query = pm.newQuery(InvoiceTransaction.class);
			query.setFilter("invoice == invoiceParam");
			query.declareParameters("Invoice invoiceParam");
			query.setUnique(true);
			InvoiceTransaction invoiceTransaction = (InvoiceTransaction) query.execute(invoice);
			if(invoiceTransaction!=null){
				logger.info("Found invoiceTransaction");
				List<Key> transactions = invoiceTransaction.getTransactions();
				for(Key key:transactions){
					paypalTransactions.add(pm.detachCopy(pm.getObjectById(PaypalTransaction.class, key)));
				}
			}
			pm.currentTransaction().commit();
			return paypalTransactions;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
	}


}
