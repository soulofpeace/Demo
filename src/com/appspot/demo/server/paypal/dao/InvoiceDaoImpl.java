package com.appspot.demo.server.paypal.dao;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
import com.appspot.demo.server.paypal.model.ProductPackage;
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
	public void saveInvoice(PaypalCustomer customer, ProductPackage productPackage, Invoice invoice) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			invoice.setCustomerId(customer.getId());
			invoice.setProductPackageId(productPackage.getId());
			invoice= pm.makePersistent(invoice);
			customer.getInvoiceKeys().add(invoice.getId());
			pm.makePersistent(customer);
			productPackage.getInvoiceKeys().add(invoice.getId());
			pm.makePersistent(productPackage);
		}
		finally{
			pm.close();
		}
	}

}
