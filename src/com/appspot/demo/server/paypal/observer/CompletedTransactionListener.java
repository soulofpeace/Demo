package com.appspot.demo.server.paypal.observer;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appspot.demo.server.paypal.model.ActionLog;
import com.appspot.demo.server.paypal.model.ActionType;
import com.appspot.demo.server.paypal.model.PaypalTransaction;
import com.appspot.demo.server.paypal.service.ActionLoggerService;
import com.google.appengine.api.datastore.Key;

@Component
public class CompletedTransactionListener implements Observer{
	
	private static Logger logger = Logger.getLogger(CompletedTransactionListener.class.getName());
	@Autowired
	private ActionLoggerService actionLoggedService;
	
	@Autowired
	private PersistenceManagerFactory pmf;
	
	public CompletedTransactionListener(){}

	public CompletedTransactionListener(ActionLoggerService actionLoggedService){
		this.actionLoggedService= actionLoggedService;
		actionLoggedService.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o.hasChanged()){
			if (arg instanceof ActionLog){
				ActionLog actionLog = (ActionLog)arg;
				if(actionLog.getType()==ActionType.COMPLETEDTRANSACTION){
					logger.info("Completed Transaction");
					PersistenceManager pm = pmf.getPersistenceManager();
					List<Key> keys = actionLog.getKeyList();
					try{
						for(Key key: keys){
							Object obj = pm.getObjectById(key);
							if(obj instanceof PaypalTransaction){
								PaypalTransaction transaction = (PaypalTransaction)obj;
								logger.info("Transaction "+ transaction.getId()+ " retrieved");
							}
						}
						
					}
					finally{
						pm.close();
					}
				}
			}
		}
	}

}
