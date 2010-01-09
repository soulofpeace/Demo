package com.appspot.demo.server.paypal.service.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.appspot.demo.server.paypal.service.exception.PaypalException;
import java.util.logging.Logger;

public class PaypalRequestEncoder {
	
	private static final Logger logger = Logger.getLogger(PaypalRequestEncoder.class.getName());
	private static final String ENCODE_TYPE="UTF-8";
	
	private LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>();
	
	public void add(String name, String value){
		this.requestMap.put(name, value);
	}
	
	public void remove(String name){
		this.requestMap.remove(name);
	}
	
	public void clear(){
		this.requestMap.clear();
	}
	
	public String encode() throws PaypalException{
		StringBuffer buffer = new StringBuffer();
		Set<String> keys = this.requestMap.keySet();
		Iterator<String> iter = keys.iterator();
		boolean firstPair = true;
		while (iter.hasNext()){
			if (! firstPair){
				buffer.append("&");
			}
			String key = iter.next();
			logger.info("Key is "+key);
			try{
				buffer.append(URLEncoder.encode(key, ENCODE_TYPE));
				buffer.append("=");
				buffer.append(URLEncoder.encode(this.requestMap.get(key), ENCODE_TYPE));
				firstPair=false;
			}
			catch(UnsupportedEncodingException ex){
				throw new PaypalException("Unable to encode "+key+" & "+requestMap.get(key));
			}
		}
		return buffer.toString();
	}
	
	
}
