package com.appspot.demo.server.paypal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType =  IdentityType.APPLICATION, detachable = "true")
public class ActionLog {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private ActionType type;
	
	@Persistent
	private String comment;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private ActionSource source;
	
	@Persistent
	private List<Key> keyList = new ArrayList<Key>();

	public void setId(Key id) {
		this.id = id;
	}

	public Key getId() {
		return id;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public ActionType getType() {
		return type;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setSource(ActionSource source) {
		this.source = source;
	}

	public ActionSource getSource() {
		return source;
	}

	public void setKeyList(List<Key> keys) {
		this.keyList = keys;
	}

	public List<Key> getKeyList() {
		return keyList;
	}
}
