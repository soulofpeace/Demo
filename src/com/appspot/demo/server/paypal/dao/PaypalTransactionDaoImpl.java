package com.appspot.demo.server.paypal.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.Invoice;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

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

	@Override
	public Collection<PaypalTransaction> getTransactions(Invoice invoice) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(PaypalTransaction.class);
			query.setFilter("invoiceId == invoiceIdParam");
			query.setOrdering("dateCreated desc");
			query.declareParameters("com.google.appengine.api.datastore.Key invoiceIdParam");
			List<PaypalTransaction> transactions = (List<PaypalTransaction>)query.execute(invoice.getId());
			Collection<PaypalTransaction> transactionCollection = pm.detachCopyAll(transactions);
			pm.currentTransaction().commit();
			return transactionCollection;
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
	}
	
	public Collection<PaypalTransaction> getTransactionsByDate(Invoice invoice, Date startDate, Date endDate, PaypalApplicationUser appUser){
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Query query = pm.newQuery("select id from "+PaypalTransaction.class.getName());
			query.setFilter("paypalApplicationUser == appUserParam && invoiceId == invoiceParam");
			query.declareParameters("com.google.appengine.api.datastore.Key appUserParam, com.google.appengine.api.datastore.Key invoiceParam");
			List ids =(List) query.execute(appUser.getId(), invoice.getId());
			Collection<PaypalTransaction> transactions = new ArrayList<PaypalTransaction>();
			if(ids.size()>0){
				logger.info("Size is > 0");
				query = pm.newQuery(PaypalTransaction.class);
				query.setFilter("dateCreated >=startDate && dateCreated <=endDate && tnxIds.contains(id)");
				query.setOrdering("dateCreated desc");
				query.declareParameters("java.util.Date startDate, java.util.Date endDate, java.util.List txnIds");
				transactions = pm.detachCopyAll((Collection<PaypalTransaction>)query.execute(startDate, endDate, ids));
			}
			
			return transactions;
		}
		finally{
			pm.close();
		}
		
	}

}
