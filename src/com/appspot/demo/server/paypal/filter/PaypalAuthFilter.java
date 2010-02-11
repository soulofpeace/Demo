package com.appspot.demo.server.paypal.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import com.appspot.demo.server.paypal.dao.ActionLogDao;
import com.appspot.demo.server.paypal.dao.PaypalApplicationUserDao;
import com.appspot.demo.server.paypal.model.ActionSource;
import com.appspot.demo.server.paypal.model.ActionType;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.Role;
import com.appspot.demo.server.paypal.service.ActionLoggerService;
import com.appspot.demo.server.paypal.service.UserInfoService;


public class PaypalAuthFilter implements Filter{
	
	public static Logger logger = Logger.getLogger(PaypalAuthFilter.class.getName());
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private PaypalApplicationUserDao appUserDao;
	
	@Autowired
	private ActionLoggerService actionLoggerService; 
	
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
			if(this.userInfoService.getCurrentApplicationUser()==null){
				logger.info("Creating New User");
				PaypalApplicationUser appUser  = new PaypalApplicationUser();
				appUser.setDateCreated(new Date());
				appUser.setEmail(this.userInfoService.getCurrentUserEmail());
				appUser.setUserName(this.userInfoService.getName());
				if(!this.userInfoService.isCurrentUserAdmin()){
					appUser.setRole(Role.CUSTOMER);
				}
				else{
					appUser.setRole(Role.ADMIN);
				}
				String keyString = this.appUserDao.saveApplicationUser(appUser);
				if (keyString != null){
					List<Key> keys = new ArrayList<Key>();
					keys.add(KeyFactory.stringToKey(keyString));
					this.actionLoggerService.log(ActionType.NEWUSER, ActionSource.WEB, null, keys);
				}
				else{
					List<Key> keys = new ArrayList<Key>();
					this.actionLoggerService.log(ActionType.FAILEDUSER, ActionSource.WEB, this.userInfoService.getCurrentUserEmail()+" failed to create", keys);
				}
				chain.doFilter(request, response);
			}
		}
		else{
			logger.info("User has not authenticated");
			resp.sendRedirect(this.userInfoService.getLoginUrl(req.getRequestURI()));
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	
		ServletContext servletContext = filterConfig.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
 
		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
 		
		
		//autowireCapableBeanFactory.configureBean(this, "UserInfoService");
		//autowireCapableBeanFactory.configureBean(this, "PaypalApplicationUserDao");
		autowireCapableBeanFactory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
	}
	

}
