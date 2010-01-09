package com.appspot.demo.server.paypal.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
