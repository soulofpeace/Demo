package com.appspot.demo.server.paypal.dao;

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

}
