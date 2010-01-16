package com.appspot.demo.server.paypal.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appspot.demo.server.paypal.service.UserInfoService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class PaypalAuthFilter implements Filter{
	
	public static Logger logger = Logger.getLogger(PaypalAuthFilter.class.getName());
	
	private UserInfoService userInfoService;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp =(HttpServletResponse) response;
		if(this.userInfoService.isUserLogin()){
			logger.info("User has authenticated");
			chain.doFilter(request, response);
		}
		else{
			logger.info("User has not authenticated");
			resp.sendRedirect(this.userInfoService.getLoginUrl(req.getRequestURI()));
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.userInfoService = new UserInfoService();
	}
	

}
