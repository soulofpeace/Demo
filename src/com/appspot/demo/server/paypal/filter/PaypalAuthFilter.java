package com.appspot.demo.server.paypal.filter;

import java.io.IOException;
import java.util.Date;
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

import com.appspot.demo.server.paypal.dao.PaypalApplicationUserDao;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.Role;
import com.appspot.demo.server.paypal.service.UserInfoService;


public class PaypalAuthFilter implements Filter{
	
	public static Logger logger = Logger.getLogger(PaypalAuthFilter.class.getName());
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private PaypalApplicationUserDao appUserDao;
	
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
			if(this.appUserDao.getApplicationUserByEmail(this.userInfoService.getCurrentUserEmail())==null){
				logger.info("Creating New User");
				PaypalApplicationUser appUser  = new PaypalApplicationUser();
				appUser.setDateCreated(new Date());
				appUser.setEmail(this.userInfoService.getCurrentUserEmail());
				appUser.setUserName(this.userInfoService.getName());
				appUser.setRole(Role.CUSTOMER);
				this.appUserDao.saveApplicationUser(appUser);
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
