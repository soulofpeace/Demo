package com.appspot.demo.server.paypal.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.model.Role;
import com.appspot.demo.server.paypal.service.UserInfoService;

public class PaypalAdminFilter implements Filter{
	
	private static final Logger logger = Logger.getLogger(PaypalAdminFilter.class.getName());
	
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		logger.info("In admin Filter");
		PaypalApplicationUser appUser = this.userInfoService.getCurrentApplicationUser();
		if(!appUser.getRole().equals(Role.ADMIN)){
			logger.info("Unauthorised Access by "+appUser.getUserName());
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("/demo/paypal/productpackage/view");
		}
		else{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		ServletContext servletContext = filterConfig.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
		
		autowireCapableBeanFactory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		
		
	}

}
