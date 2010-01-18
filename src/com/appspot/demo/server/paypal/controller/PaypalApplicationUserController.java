package com.appspot.demo.server.paypal.controller;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appspot.demo.client.paypal.dto.PaypalApplicationUserDto;
import com.appspot.demo.server.paypal.dao.PaypalApplicationUserDao;
import com.appspot.demo.server.paypal.model.PaypalApplicationUser;
import com.appspot.demo.server.paypal.service.UserInfoService;
import com.google.appengine.api.datastore.KeyFactory;

@Controller
@RequestMapping("paypal/appuser/")
public class PaypalApplicationUserController {
	private static final Logger logger = Logger.getLogger(PaypalApplicationUserController.class.getName());
	
	@Autowired
	private PaypalApplicationUserDao paypalApplicationUserDao;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(value="/get", method=RequestMethod.GET)
	public String getCurrentPaypalApplicationUser(Model model){
		String email = this.userInfoService.getCurrentUserEmail();
		PaypalApplicationUser applicationUser = this.paypalApplicationUserDao.getApplicationUserByEmail(email);
		PaypalApplicationUserDto applicationUserDto = new PaypalApplicationUserDto();
		applicationUserDto.setKey(KeyFactory.keyToString(applicationUser.getId()));
		applicationUserDto.setEmail(applicationUser.getEmail());
		applicationUserDto.setRole(applicationUser.getRole().toString());
		applicationUserDto.setUserName(applicationUser.getUserName());
		model.addAttribute("paypalApplicationUserDto", applicationUserDto);
		return "paypalApplicationUserDto";
	}
}
