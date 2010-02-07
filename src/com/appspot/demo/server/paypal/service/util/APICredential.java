package com.appspot.demo.server.paypal.service.util;

public class APICredential {
	private String user="";
	private String password="";
	private String version="";
	private String signature="";
	private String subject="";
	private String email="";
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubject() {
		return subject;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUser() {
		return user;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSignature() {
		return signature;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	

}
