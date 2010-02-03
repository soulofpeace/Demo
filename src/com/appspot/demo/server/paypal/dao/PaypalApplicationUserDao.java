package com.appspot.demo.server.paypal.dao;

import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;

public interface PaypalApplicationUserDao {
	public PaypalApplicationUser getApplicationUserById(String id);
	public PaypalApplicationUser getApplicationUserByEmail(String email);
	public String saveApplicationUser(PaypalApplicationUser applicationUser);
	public boolean addPaypalCustomertoApplicationUser(PaypalCustomer customer, PaypalApplicationUser applicationUser);

}
