package com.appspot.demo.server.paypal.dao;

import java.util.List;

import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.PaypalCustomer;

public interface PaypalApplicationUserDao {
	public PaypalApplicationUser getApplicationUserById(String id);
	public PaypalApplicationUser getApplicationUserByEmail(String email);
	public String saveApplicationUser(PaypalApplicationUser applicationUser);
	public void addPaypalCustomer(PaypalApplicationUser applicationUser, PaypalCustomer paypalCustomer);
	public List<PaypalCustomer> getPaypalCustomer(PaypalApplicationUser applicationUser);
}
