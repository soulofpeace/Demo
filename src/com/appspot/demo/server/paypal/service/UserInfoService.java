package com.appspot.demo.server.paypal.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Service
public class UserInfoService {
	public static final Logger logger = Logger.getLogger(UserInfoService.class.getName());
	
	public String getCurrentUserEmail(){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		return user.getEmail();
	}
	
	public boolean isUserLogin(){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(user==null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public String getLoginUrl(String requestURL){
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(requestURL);
	}
	
	public String getName(){
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser().getNickname();
	}
}
