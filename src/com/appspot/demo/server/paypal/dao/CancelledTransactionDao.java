package com.appspot.demo.server.paypal.dao;

import com.appspot.demo.server.paypal.model.CancelledTransaction;

public interface CancelledTransactionDao {
	public CancelledTransaction getCancelledTransactionById(String id);
	public String saveCancelledTransaction(CancelledTransaction cancelledTransaction);
}
