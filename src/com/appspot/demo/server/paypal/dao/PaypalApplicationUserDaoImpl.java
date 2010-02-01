package com.appspot.demo.server.paypal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.AppUserPaypalCustomer;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;
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

	@Override
	public void addPaypalCustomer(PaypalApplicationUser applicationUser,
			PaypalCustomer paypalCustomer) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(AppUserPaypalCustomer.class);
			query.setFilter("appUser == appUserParam");
			query.declareParameters("PaypalApplicationUser appUserParam");
			query.setUnique(true);
			AppUserPaypalCustomer appUserPaypalCustomer = (AppUserPaypalCustomer)query.execute(applicationUser);
			if(appUserPaypalCustomer!=null){
				logger.info("Existing appUserPaypalCustomer found");
				appUserPaypalCustomer.getPaypalCustomers().add(paypalCustomer.getId());
				pm.makePersistent(appUserPaypalCustomer);
			}
			else{
				logger.info("New appUserPaypalCustomer");
				appUserPaypalCustomer = new AppUserPaypalCustomer();
				appUserPaypalCustomer.getPaypalCustomers().add(paypalCustomer.getId());
				applicationUser.setAppUserPaypalCustomer(appUserPaypalCustomer);
				pm.makePersistent(applicationUser);
				
			}
			pm.currentTransaction().commit();
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Unable to commit changes");
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
	}

	@Override
	public List<PaypalCustomer> getPaypalCustomer(
			PaypalApplicationUser applicationUser) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		List<PaypalCustomer> customers = new ArrayList<PaypalCustomer>();
		try{
			Query query = pm.newQuery(AppUserPaypalCustomer.class);
			query.setFilter("appUser == appUserParam");
			query.declareParameters("PaypalApplicationUser appUserParam");
			query.setUnique(true);
			AppUserPaypalCustomer appUserPaypalCustomer =(AppUserPaypalCustomer)query.execute(applicationUser);
			if(appUserPaypalCustomer!=null){
				logger.info("Found appUserPaypalCustomer");
				Set<Key> customerKeys = appUserPaypalCustomer.getPaypalCustomers();
				for(Key key:customerKeys){
					customers.add(pm.detachCopy(pm.getObjectById(PaypalCustomer.class, key)));
				}
			}
			pm.currentTransaction().commit();
			return customers;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				logger.info("Transaction failed to commit");
				pm.currentTransaction().commit();
			}
			pm.close();
		}
		
	}

}
