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
import com.appspot.demo.server.paypal.model.PackageInvoice;
import com.appspot.demo.server.paypal.model.RecurringProductPackage;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class PackageDaoImpl implements PackageDao {
	
	private static final Logger logger = Logger.getLogger(PackageDaoImpl.class.getName());

	@Autowired
	private PersistenceManagerFactory pmf;
	
	@Override
	public RecurringProductPackage getPackageById(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			RecurringProductPackage productPackage = pm.getObjectById(RecurringProductPackage.class, idKey);
			return pm.detachCopy(productPackage);
		}
		finally{
			pm.close();
		}
			
	}

	@Override
	public String savePackage(RecurringProductPackage productPackage) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			productPackage = pm.makePersistent(productPackage);
			pm.currentTransaction().commit();
			return KeyFactory.keyToString(productPackage.getId());
		}finally{
			if (pm.currentTransaction().isActive()) {
		        pm.currentTransaction().rollback();
		    }
			pm.close();
		}

	}
	
	public List<RecurringProductPackage> getAllPackages(){
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(RecurringProductPackage.class);
			List<RecurringProductPackage> productPackages = (List<RecurringProductPackage>)query.execute();
			pm.currentTransaction().commit();
			return (List<RecurringProductPackage>)pm.detachCopyAll(productPackages);
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			
			pm.close();
		}
		
		
	}

	@Override
	public void addInvoice(RecurringProductPackage productPackage,
			Invoice invoice) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PackageInvoice.class);
			query.setFilter("productPackage == productPackageParam");
			query.declareParameters("RecurringProductPackage productPackageParam");
			query.setUnique(true);
			PackageInvoice packageInvoice = (PackageInvoice)query.execute(productPackage);
			if(packageInvoice!=null){
				logger.info("PackageInvoice exists");
				packageInvoice.getInvoices().add(invoice.getId());
				packageInvoice.setModifiedDate(new Date());
				pm.makePersistent(packageInvoice);
			}
			else{
				logger.info("new PackageInvoice");
				packageInvoice = new PackageInvoice();
				packageInvoice.getInvoices().add(invoice.getId());
				packageInvoice.setCreatedDate(new Date());
				packageInvoice.setModifiedDate(new Date());
				productPackage.setPackageInvoice(packageInvoice);
				pm.makePersistent(productPackage);
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
	public List<Invoice> getInvoices(RecurringProductPackage productPackage) {
		// TODO Auto-generated method stub
		PersistenceManager pm = this.pmf.getPersistenceManager();
		List<Invoice> invoices = new ArrayList<Invoice>();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PackageInvoice.class);
			query.setFilter("productPackage = productPackageParam");
			query.declareParameters("RecurringProductPackage productPackageParam");
			query.setUnique(true);
			PackageInvoice packageInvoice = (PackageInvoice)query.execute(productPackage);
			if(packageInvoice!=null){
				logger.info("Found packageInvoice");
				List<Key> invoiceKeys = packageInvoice.getInvoices();
				for(Key key:invoiceKeys){
					invoices.add(pm.detachCopy(pm.getObjectById(Invoice.class, key)));
				}
				
				
			}
			pm.currentTransaction().commit();
			return invoices;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Transactions failed to commit");
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
	}

}
