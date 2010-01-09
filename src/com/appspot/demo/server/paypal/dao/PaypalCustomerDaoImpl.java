package com.appspot.demo.server.paypal.dao;


import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

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

}
