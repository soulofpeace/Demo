package com.appspot.demo.server.paypal.dao;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.CancelledTransaction;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class CancelledTransactionDaoImpl implements CancelledTransactionDao{
	
	public static final Logger logger = Logger.getLogger(CancelledTransactionDaoImpl.class.getName());
	
	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public CancelledTransaction getCancelledTransactionById(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			CancelledTransaction cancelledTransaction = pm.getObjectById(CancelledTransaction.class, idKey);
			return pm.detachCopy(cancelledTransaction);
			
		}
		finally{
			pm.close();
		}
	}

	@Override
	public String saveCancelledTransaction(
			CancelledTransaction cancelledTransaction) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			cancelledTransaction =pm.makePersistent(cancelledTransaction);
			pm.currentTransaction().commit();
			return KeyFactory.keyToString(cancelledTransaction.getId());
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

}
