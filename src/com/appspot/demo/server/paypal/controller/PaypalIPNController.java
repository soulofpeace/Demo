package com.appspot.demo.server.paypal.controller;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/ipn")
public class PaypalIPNController {
	private static Logger logger = Logger.getLogger(PaypalIPNController.class.getName());
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public void get(HttpServletRequest request){
		Map<String, String> map = request.getParameterMap();
		Set<String> keys = map.keySet();
		for(String key: keys){
			logger.info("map Key: "+key);
			logger.info("map value"+map.get(key));
		}
	}

}
