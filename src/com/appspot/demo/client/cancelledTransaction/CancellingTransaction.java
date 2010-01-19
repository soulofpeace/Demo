package com.appspot.demo.client.cancelledTransaction;

import com.appspot.demo.client.paypal.dto.js.PaypalApplicationUserJs;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CancellingTransaction implements EntryPoint {
	
	private Label greetingsLabel;
	private Label requestForInformation;
	private TextArea commentBox;
	private PaypalApplicationUserJs appUser;
	private Label thanksMsg;
	private FormPanel cancelForm;
	private VerticalPanel container;
	private Button submitButton;
	private Label debug = new Label("start");
	private Label commentLabel;
	
	
	//Please tell us why you cancelled the payment

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		//RootPanel.get().add(this.debug);
		this.getCurrentAppUser();

	}
	
	private void populatePage(){
		this.greetingsLabel = new Label("Hi "+this.appUser.getUserName());
		this.requestForInformation= new Label("Please tell us why you cancelled the payment so that we could continually improve on our product");
		this.thanksMsg = new Label("Your Feedback is very much appreciated!");
		this.commentBox= new TextArea();
		this.commentBox.setName("comment");
		this.commentLabel = new Label("Comments");
		this.commentBox.setVisibleLines(4);
		this.submitButton = new Button("Submit");
		this.submitButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				cancelForm.submit();
				
			}
		});
		this.container= new VerticalPanel();
		this.container.add(this.greetingsLabel);
		this.container.add(this.requestForInformation);
		this.container.add(this.thanksMsg);
		this.container.add(commentLabel);
		this.container.add(this.commentBox);
		this.container.add(this.submitButton);
		this.cancelForm = new FormPanel();
		this.cancelForm.setAction("/demo/paypal/payment/recordCancelledTransaction");
		this.cancelForm.setEncoding(FormPanel.ENCODING_URLENCODED);
		this.cancelForm.setWidget(container);
		this.cancelForm.setMethod(FormPanel.METHOD_POST);
		RootPanel.get().add(this.cancelForm);
	}
	
	private void getCurrentAppUser(){
		String url="http://choonkeedemo.appspot.com/demo/paypal/appuser/get.json";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
		requestBuilder.setHeader("Content-Type", "application/json");
		try{
			Request request = requestBuilder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					if(200==response.getStatusCode()){
						
						String json = response.getText();
						debug.setText("json is "+json);
						appUser = PaypalApplicationUserJs.fromJson(json);
						debug.setText("json is "+json+" appUserEmail is"+appUser.getUserEmail());
						populatePage();
					}
					else{
						debug.setText("No output");
					}
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		catch(RequestException ex){
			
		}
		
	}

}
