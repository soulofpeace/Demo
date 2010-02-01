package com.appspot.demo.server.paypal.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.PaypalCustomerAppUser;
import com.appspot.demo.server.paypal.model.PaypalCustomerInvoice;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class PaypalCustomerDaoImpl implements PaypalCustomerDao {
	
	private static final Logger logger = Logger.getLogger(PaypalCustomerDaoImpl.class.getName());
	
	@Autowired
	private PersistenceManagerFactory pmf;
	
	
	@Override
	public PaypalCustomer getPaypalCustomerById(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			PaypalCustomer customer = pm.getObjectById(PaypalCustomer.class, idKey);
			return pm.detachCopy(customer);
		}
		finally{
			pm.close();
		}
	}

	@Override
	public String savePaypalCustomer(PaypalCustomer paypalCustomer) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			paypalCustomer =pm.makePersistent(paypalCustomer);
			pm.currentTransaction().commit();
			return KeyFactory.keyToString(paypalCustomer.getId());
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public PaypalCustomer getPaypalCustomerByPaypalId(String paypalId) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PaypalCustomer.class);
			query.setFilter("payerId == payerIdParam");
			query.declareParameters("String payerIdParam");
			List<PaypalCustomer> customers =(List<PaypalCustomer>) query.execute(paypalId);
			pm.currentTransaction().commit();
			if (customers.isEmpty()){
				return null;
			}
			else{
				return pm.detachCopy(customers.get(0));
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
	public void addAppUser(PaypalCustomer paypalCustomer,
			PaypalApplicationUser appUser) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PaypalCustomerAppUser.class);
			query.setFilter("customer == customerParam");
			query.declareParameters("PaypalCustomer customerParam");
			query.setUnique(true);
			PaypalCustomerAppUser paypalCustomerAppUser =(PaypalCustomerAppUser)query.execute(paypalCustomer);
			if(paypalCustomerAppUser!=null){
				logger.info("Existing PaypalCustomerAppUser found");
				paypalCustomerAppUser.getPaypalApplicationUser().add(appUser.getId());
				pm.makePersistent(paypalCustomerAppUser);
			}
			else{
				logger.info("New PaypalCustomerAppUser");
				paypalCustomerAppUser = new PaypalCustomerAppUser();
				paypalCustomerAppUser.getPaypalApplicationUser().add(appUser.getId());
				paypalCustomer.setPaypalCustomerAppUser(paypalCustomerAppUser);
				pm.makePersistent(paypalCustomer);
			}
			pm.currentTransaction().commit();
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Transaction failed to commit");
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
		
	}

	@Override
	public void addInvoice(PaypalCustomer paypalCustomer, Invoice invoice) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PaypalCustomerInvoice.class);
			query.setFilter("customer == customerParam");
			query.declareParameters("PaypalCustomer customerParam");
			query.setUnique(true);
			PaypalCustomerInvoice paypalCustomerInvoice = (PaypalCustomerInvoice)query.execute(paypalCustomer);
			if(paypalCustomerInvoice!=null){
				logger.info("Existing paypalCustomerInvoice found");
				paypalCustomerInvoice.getInvoices().add(invoice.getId());
				pm.makePersistent(paypalCustomerInvoice);
			}
			else{
				logger.info("New PaypalCustomerInvoice");
				paypalCustomerInvoice = new PaypalCustomerInvoice();
				paypalCustomerInvoice.getInvoices().add(invoice.getId());
				paypalCustomer.setPaypalCustomerInvoice(paypalCustomerInvoice);
				pm.makePersistent(paypalCustomer);
			}
			pm.currentTransaction().commit();
		
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Transaction failed to commit");
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}
		

	@Override
	public List<PaypalApplicationUser> getApplicationUser(
			PaypalCustomer paypalCustomer) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		List<PaypalApplicationUser> applicationUsers = new ArrayList<PaypalApplicationUser>();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PaypalCustomerAppUser.class);
			query.setFilter("customer == customerParam");
			query.declareParameters("PaypalCustomer customerParam");
			query.setUnique(true);
			PaypalCustomerAppUser paypalCustomerAppUser =(PaypalCustomerAppUser)query.execute(paypalCustomer);
			if(paypalCustomerAppUser!=null){
				Set<Key> appUserKeys = paypalCustomerAppUser.getPaypalApplicationUser();
				for(Key key: appUserKeys){
					applicationUsers.add(pm.detachCopy(pm.getObjectById(PaypalApplicationUser.class, key)));
				}
			}
			pm.currentTransaction().commit();
			return applicationUsers;
			
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Transaction failed to commit");
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public List<Invoice> getInvoices(PaypalCustomer paypalCustomer) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Invoice> invoices = new ArrayList<Invoice>();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PaypalCustomerInvoice.class);
			query.setFilter("customer == customerParam");
			query.declareParameters("PaypalCustomer customerParam");
			query.setUnique(true);
			PaypalCustomerInvoice paypalCustomerInvoice = (PaypalCustomerInvoice)query.execute(paypalCustomer);
			if(paypalCustomerInvoice!=null){
				List<Key> invoiceKeys = paypalCustomerInvoice.getInvoices();
				for(Key key: invoiceKeys){
					invoices.add(pm.detachCopy(pm.getObjectById(Invoice.class, key)));
				}
			}
			pm.currentTransaction().commit();
			return invoices;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Transaction failed to commit");
				pm.currentTransaction().rollback();
			}
			pm.close();
			
		}
	}
	
	

}
