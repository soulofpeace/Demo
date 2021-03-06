package com.appspot.demo.server.paypal.dao;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class PaypalTransactionDaoImpl implements PaypalTransactionDao{
	private static final Logger logger = Logger.getLogger(PaypalTransaction.class.getName());
	
	@Autowired
	private PersistenceManagerFactory pmf;
	
	@Override
	public PaypalTransaction getPaypalTransactionId(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			PaypalTransaction transaction = pm.getObjectById(PaypalTransaction.class, idKey);
			return pm.detachCopy(transaction);
		}
		finally{
			pm.close();
		}
	}

	@Override
	public String savePaypalTransaction(PaypalTransaction paypalTransaction) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			paypalTransaction = pm.makePersistent(paypalTransaction);
			pm.currentTransaction().commit();
			return KeyFactory.keyToString(paypalTransaction.getId());
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

}
