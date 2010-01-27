package com.appspot.demo.server.paypal.service;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

import com.appspot.demo.server.paypal.dao.ActionLogDao;
import com.appspot.demo.server.paypal.model.ActionLog;
import com.appspot.demo.server.paypal.model.ActionSource;
import com.appspot.demo.server.paypal.model.ActionType;

@Service
@Scope("singleton")
public class ActionLoggerService extends Observable{
	public static final Logger logger = Logger.getLogger(ActionLoggerService.class.getName());
	public static int count =0;
	public String name;
	
	@Autowired
	private ActionLogDao actionLogDao;
	
	public ActionLoggerService(){
		logger.info("setting name to "+count);
		name="hello"+count;
		count++;
	}
	
	public void log(ActionType actionType, ActionSource actionSource, String comment, List<Key> keys){
		ActionLog actionLog = new ActionLog();
		if(actionLog.getComment()!=null){
			actionLog.setComment(new Text(comment));
		}
		else{
			actionLog.setComment(new Text(""));
		}
		actionLog.setDateCreated(new Date());
		actionLog.setSource(actionSource);
		actionLog.setType(actionType);
		actionLog.setKeyList(keys);
		String actionLogId = actionLogDao.saveActionLog(actionLog);
		actionLog.setId(KeyFactory.stringToKey(actionLogId));
		this.setChanged();
		this.notifyObservers(actionLog);
		logger.info("My name is "+name);
		logger.info("Number of listeners "+this.countObservers());
	}
	
}
