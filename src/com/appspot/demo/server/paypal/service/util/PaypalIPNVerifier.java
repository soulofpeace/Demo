package com.appspot.demo.server.paypal.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import com.appspot.demo.server.paypal.service.exception.PaypalException;

public class PaypalIPNVerifier {
	private static final Logger logger = Logger.getLogger(PaypalIPNVerifier.class.getName());
	
	private String paypalIPNServer;
	
	private String sellerEmail;

	public void setPaypalIPNServer(String paypalIPNServer) {
		this.paypalIPNServer = paypalIPNServer;
	}

	public String getPaypalIPNServer() {
		return paypalIPNServer;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}
	
	public boolean verifyResponse(String responseString){
		responseString = "cmd=_notify-validate&"+responseString;
		String response = this.getURLResponse(this.paypalIPNServer, responseString);
		if (response.equalsIgnoreCase("VERIFIED")){
			PaypalResponseDecoder decoder = new PaypalResponseDecoder();
			try {
				decoder.decode(responseString);
				if(decoder.get("receiver_email").equalsIgnoreCase(this.sellerEmail)){
					logger.info("Verified IPN Message");
					return true;
				}
				else{
					logger.warning("Unverified IPN Message");
					return false;
				}
			}catch (PaypalException e) {
				// TODO Auto-generated catch block
				logger.warning("Unable to decode message "+responseString);
				logger.warning(e.getMessage());
				return false;
			}		
		}
		else{
			return false;
		}
	}
	
	private String getURLResponse(String urlString, String outputString){
		try {
			URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(outputString);
            writer.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	String line;
            	String output="";
            	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            	while ((line = reader.readLine()) != null) {
            		output+=line;
            	}
            	return output;
            }
            return null;
            
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
            return null;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return null;
        }
		
	}
	
}
