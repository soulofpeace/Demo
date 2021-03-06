package com.appspot.demo.server.paypal.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class PaypalApplicationUserDaoImpl implements PaypalApplicationUserDao {
	
	private static final Logger logger = Logger.getLogger(PaypalApplicationUserDaoImpl.class.getName());

	@Autowired
	private PersistenceManagerFactory pmf;
	
	@Override
	public PaypalApplicationUser getApplicationUserByEmail(String email) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PaypalApplicationUser.class);
			query.setFilter("email == emailParam");
			query.declareParameters("String emailParam");
			List<PaypalApplicationUser> applicationUsers = (List<PaypalApplicationUser>)query.execute(email);
			pm.currentTransaction().commit();
			if(applicationUsers.isEmpty()){
				return null;
			}
			else{
				return pm.detachCopy(applicationUsers.get(0));
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
	public PaypalApplicationUser getApplicationUserById(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			PaypalApplicationUser applicationUser = pm.getObjectById(PaypalApplicationUser.class, idKey);
			return pm.detachCopy(applicationUser);
			
		}
		finally{
			pm.close();
		}
	}

	@Override
	public String saveApplicationUser(PaypalApplicationUser applicationUser) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			applicationUser = pm.makePersistent(applicationUser);
			pm.currentTransaction().commit();
			return KeyFactory.keyToString(applicationUser.getId());
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		
		}
	}

}
