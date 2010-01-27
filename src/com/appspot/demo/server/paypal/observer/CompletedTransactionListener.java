package com.appspot.demo.server.paypal.observer;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appspot.demo.server.paypal.model.ActionLog;
import com.appspot.demo.server.paypal.model.ActionType;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.appspot.demo.server.paypal.service.ActionLoggerService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;


@Component
public class CompletedTransactionListener implements Observer{
	
	private static Logger logger = Logger.getLogger(CompletedTransactionListener.class.getName());

	private ActionLoggerService actionLoggerService;
	
	@Autowired
	public CompletedTransactionListener( ActionLoggerService actionLoggerService){
		logger.info("register observer");
		logger.info("my name is "+actionLoggerService.name);
		actionLoggerService.addObserver(this);
	}
	
	/**@PostConstruct
	public void registerListener(){
		logger.info("register observer");
		logger.info("my name is "+actionLoggerService.name);
		actionLoggerService.addObserver(this);
	}**/
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		logger.info("Listener Activated");
	
		if (arg instanceof ActionLog){
			logger.info("arg is of actionLog type");
			ActionLog actionLog = (ActionLog)arg;
			if(actionLog.getType().equals(ActionType.COMPLETEDTRANSACTION)){
				logger.info("Completed Transaction");
				DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
				List<Key> keys = actionLog.getKeyList();
				try{
					for(Key key: keys){
						Entity entity = dataStoreService.get(key);
						logger.info("Entity Kind:"+entity.getKind());
						if(entity.getKind().equalsIgnoreCase("PaypalTransaction")){

							logger.info("Transaction "+ entity.getKey()+ " retrieved");
						}
					}
					
				}
				catch(EntityNotFoundException e){
					logger.warning(e.getMessage());
				}
				
			}
		}
	}
	

}
