package com.appspot.demo.server.paypal.service.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.appspot.demo.server.paypal.service.exception.PaypalException;

public class PaypalResponseDecoder {
	
	private static final String ENCODE_TYPE="UTF-8";
	private HashMap<String, String> responseMap = new HashMap<String, String>();
	
	public String get(String key){
		return this.responseMap.get(key);
	}
	
	public void decode(String response) throws PaypalException{
		try{
			StringTokenizer responseTokenizer = new StringTokenizer(response, "&");
			while(responseTokenizer.hasMoreTokens()){
				StringTokenizer keyValueTokenizer = new StringTokenizer(responseTokenizer.nextToken(), "=");
				if (keyValueTokenizer.countTokens()==2){
					responseMap.put(URLDecoder.decode(keyValueTokenizer.nextToken(), ENCODE_TYPE), URLDecoder.decode(keyValueTokenizer.nextToken(), ENCODE_TYPE));
				}
			
			}
		}
		catch(UnsupportedEncodingException ex){
			throw new PaypalException("Unable to use UTF-8 to decode "+response);
		}
	}
}
