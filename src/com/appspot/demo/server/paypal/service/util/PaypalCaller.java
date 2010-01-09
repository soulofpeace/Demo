package com.appspot.demo.server.paypal.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import com.appspot.demo.server.paypal.service.exception.PaypalException;


public class PaypalCaller {
	
	private static final Logger logger = Logger.getLogger(PaypalCaller.class.getName());
	
	private URL payPalServer;
	private String header;

	public void setPayPalServer(URL payPalServer) throws PaypalException {
		/**
		try{
			this.payPalServer = new URL(payPalServer);
		}
		catch(MalformedURLException e){
			throw new PaypalException("Paypal Server URL is invalid "+payPalServer);
		}
		**/
		this.payPalServer=payPalServer;
	}

	public URL getPayPalServer() {
		return payPalServer;
	}
	
	public void setupConnection(APICredential apiCredential) throws PaypalException{
		this.setHeader(apiCredential);
	}
	
	public void setHeader(APICredential apiCredential) throws PaypalException{
		PaypalRequestEncoder encoder = new PaypalRequestEncoder();
		if(! (apiCredential.getUser()==null || apiCredential.getUser().length()==0)){
			encoder.add("USER", apiCredential.getUser());
		}
		if(!(apiCredential.getPassword()==null|| apiCredential.getPassword().length()==0)){
			encoder.add("PWD", apiCredential.getPassword());
		}
		if(!(apiCredential.getVersion()==null|| apiCredential.getVersion().length()==0)){
			encoder.add("VERSION", apiCredential.getVersion());
		}
		if(!(apiCredential.getSignature()==null|| apiCredential.getSignature().length()==0)){
			encoder.add("SIGNATURE", apiCredential.getSignature());
		}
		if(!(apiCredential.getSubject()==null|| apiCredential.getSubject().length()==0)){
			encoder.add("SUBJECT", apiCredential.getSubject());
		}
		this.header = encoder.encode();
	}
	
	public String call(String parameters){
		StringBuffer requestURLStringBuffer= new StringBuffer(this.payPalServer.toString());
		requestURLStringBuffer.append("?");
		requestURLStringBuffer.append(this.header);
		requestURLStringBuffer.append("&");
		requestURLStringBuffer.append(parameters);
		String requestURL = requestURLStringBuffer.toString();
		logger.info("Request URL is "+requestURL);
		String response = this.getURLResponse(requestURL);
		return response;
		
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
