package com.appspot.demo.server.paypal.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import com.appspot.demo.server.paypal.dao.ActionLogDao;
import com.appspot.demo.server.paypal.model.ActionLog;
import com.appspot.demo.server.paypal.model.ActionSource;
import com.appspot.demo.server.paypal.model.ActionType;

@Service
public class ActionLoggerService {
	public static final Logger logger = Logger.getLogger(ActionLoggerService.class.getName());
	
	@Autowired
	private ActionLogDao actionLogDao;
	
	public void log(ActionType actionType, ActionSource actionSource, String comment, List<Key> keys){
		ActionLog actionLog = new ActionLog();
		actionLog.setComment(comment);
		actionLog.setDateCreated(new Date());
		actionLog.setSource(actionSource);
		actionLog.setType(actionType);
		actionLog.setKeyList(keys);
		actionLogDao.saveActionLog(actionLog);
	}
	
}
