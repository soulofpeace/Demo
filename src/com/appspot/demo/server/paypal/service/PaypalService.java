package com.appspot.demo.server.paypal.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.appspot.demo.server.paypal.model.ProductPackage;

@Service
public class PaypalService {
	private static final Logger logger = Logger.getLogger(PaypalService.class.getName());
	
	public void setExpressCheckOut(HttpSession session, ProductPackage productPackage){
		
	}
	
	private String getURLResponse(String urlString){
		try {
			URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            String line;
            String output="";
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
            	output+=line;
            }
            return output;
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
            return null;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return null;
        }
		
	}

}
