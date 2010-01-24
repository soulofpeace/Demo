package com.appspot.demo.server.paypal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appspot.demo.server.paypal.service.IPNService;


@Controller
@RequestMapping("/ipn")
public class PaypalIPNController {
	private static Logger logger = Logger.getLogger(PaypalIPNController.class.getName());
	
	@Autowired
	private IPNService ipnService;
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	@ResponseBody
	public String get(HttpServletRequest request){
		try {
			String line;
			String output ="";
			InputStream inputStream =request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			while((line=reader.readLine())!=null){
				output+=line;
			}
			logger.info("response"+output);
			this.ipnService.handleIPN(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "done";
	}

}
