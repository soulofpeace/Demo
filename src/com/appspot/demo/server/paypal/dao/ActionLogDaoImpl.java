package com.appspot.demo.server.paypal.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appspot.demo.server.paypal.model.ActionLog;
import com.appspot.demo.server.paypal.model.ActionType;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Repository
public class ActionLogDaoImpl implements ActionLogDao{
	
	private static Logger logger = Logger.getLogger(ActionLogDaoImpl.class.getName());

	@Autowired
	private PersistenceManagerFactory pmf;
	
	@Override
	public ActionLog getActionLog(String id) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		try{
			Key idKey = KeyFactory.stringToKey(id);
			ActionLog actionLog = pm.getObjectById(ActionLog.class, idKey);
			return pm.detachCopy(actionLog);
		}
		finally{
			pm.close();
		}
	}

	@Override
	public List<ActionLog> getActionLogByActionType(ActionType actionType) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			Query query = pm.newQuery(ActionLog.class);
			query.setFilter("type == typeParam");
			query.declareParameters("ActionType typeParam");
			List<ActionLog> actionLogs = (List<ActionLog>)query.execute(actionType);
			pm.currentTransaction().commit();
			return pm.detachCopy(actionLogs);
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

	@Override
	public String saveActionLog(ActionLog actionLog) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.currentTransaction().begin();
		try{
			actionLog = pm.makePersistent(actionLog);
			pm.currentTransaction().commit();
			return KeyFactory.keyToString(actionLog.getId());
			
		}
		finally{
			if(pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
	}

}
