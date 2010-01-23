package com.appspot.demo.server.paypal.dao;

import java.util.List;

import com.appspot.demo.server.paypal.model.ActionLog;
import com.appspot.demo.server.paypal.model.ActionType;

public interface ActionLogDao {
	public ActionLog getActionLog(String id);
	public String saveActionLog(ActionLog actionLog);
	public List<ActionLog> getActionLogByActionType(ActionType actionType);
}
