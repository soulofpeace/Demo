package com.appspot.demo.server.paypal.dao;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.ProductPackage;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class PackageDaoImpl implements PackageDao {
	
	private static final Logger logger = Logger.getLogger(PackageDaoImpl.class.getName());

	@Autowired
	private PersistenceManagerFactory pmf;
	
	@Override
	public Package getPackageById(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			Package packageName = pm.getObjectById(Package.class, idKey);
			return pm.detachCopy(packageName);
		}
		finally{
			pm.close();
		}
			
	}

	@Override
	public void savePackage(ProductPackage productPackage) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			pm.makePersistent(productPackage);
			pm.currentTransaction().commit();
		}finally{
			if (pm.currentTransaction().isActive()) {
		        pm.currentTransaction().rollback();
		    }
			pm.close();
		}

	}

}
