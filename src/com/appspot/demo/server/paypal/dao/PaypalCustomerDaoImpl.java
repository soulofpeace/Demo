package com.appspot.demo.server.paypal.dao;


import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.PaypalCustomer;
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
	public void savePaypalCustomer(PaypalCustomer paypalCustomer) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			pm.makePersistent(paypalCustomer);
			pm.currentTransaction().commit();
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

}