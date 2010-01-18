package com.appspot.demo.client.paypal.dto.js;

import com.google.gwt.core.client.JavaScriptObject;

public class PaypalApplicationUserJs extends JavaScriptObject{
	protected PaypalApplicationUserJs(){}
	
	public static native final PaypalApplicationUserJs fromJson(String json)/*-{
		return eval('('+json+')').paypalApplicationUserDto;
	}-*/;
	
	public final native String getKey()/*-{
		return this.key;
	}-*/;
	
	public final native String getUserEmail()/*-{
		return this.email;
	}-*/;
	
	public final native String getUserName()/*-{
		return this.userName;
	}-*/;
	
	public final native String getUserRole()/*-{
		return this.role;
	}-*/;
	
}
