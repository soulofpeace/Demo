package com.appspot.demo.server.paypal.dao;



import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.stereotype.Component;

@Component
public class PMF {
	private static final PersistenceManagerFactory pmfInstance =JDOHelper.getPersistenceManagerFactory("transactions-optional");
 
    public PMF() {}
 
    public  PersistenceManagerFactory persistenceManagerFactory() {
        return pmfInstance;
    }
}
