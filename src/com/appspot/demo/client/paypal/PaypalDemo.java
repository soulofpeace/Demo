package com.appspot.demo.client.paypal;

import com.appspot.demo.client.paypal.dto.js.PaypalApplicationUserJs;
import com.appspot.demo.client.paypal.dto.js.ProductPackageJS;
import com.appspot.demo.server.paypal.model.Role;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PaypalDemo implements EntryPoint {
	
	private VerticalPanel packagePanel = new VerticalPanel();
	private VerticalPanel invoicePanel = new VerticalPanel();
	private TabPanel contentPanel = new TabPanel();
	private FlexTable packageTable = new FlexTable();
	private PaypalApplicationUserJs appUser;

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		//this.setupContentPanel();
		this.getCurrentAppUser();
		RootPanel.get().add(contentPanel);

	}
	
	
	private void setupContentPanel(){
		//this.setupFormPanel();
		//tabPanel
		this.setupPackagePanel();
		this.contentPanel.add(this.packagePanel, "Available Packages");
		this.contentPanel.add(this.invoicePanel, "Purchased Packages");
		this.contentPanel.selectTab(0);
	}
	
	/**
	private void setupFormPanel(){
		this.formPanel.setMethod(FormPanel.METHOD_POST);
		this.formPanel.setAction("/demo/paypal/payment/start");
		this.formPanel.setEncoding(FormPanel.ENCODING_URLENCODED);
		this.setupPackageTable();
		this.formPanel.setWidget(packageTable);
	}
	**/
	
	private void setupInvoicePanel(){
		
	}
	
	private void setupPackagePanel(){
		this.packageTable.setText(0, 0, "Package Logo");
		this.packageTable.setText(0, 1, "Package Name");
		this.packageTable.setText(0, 2, "Package Description");
		this.packageTable.setText(0, 3, "More Information");
		this.packageTable.setText(0, 4, "Package Cost (US Dollar)");
		this.packageTable.setText(0, 5, "Billing Period");
		this.packageTable.setText(0, 6, "Billing Frequency");
		this.packageTable.setText(0, 7, "Action");
		this.packageTable.getRowFormatter().addStyleName(0, "packageHeader");
		this.packageTable.addStyleName("packageList");
		
		String url="http://choonkeedemo.appspot.com/demo/paypal/productpackage/get.json";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
		requestBuilder.setHeader("Content-Type", "application/json");
		try{
			Request request= requestBuilder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					if(200==response.getStatusCode()){
						String json = response.getText();
						JsArray<ProductPackageJS> packages= getPackagesFromJson(json);
						for (int i=0; i< packages.length(); i++){
							Image packageImage = new Image();
							packageImage.setUrl(packages.get(i).getPackageImageURL());
							Label packageLabel = new Label(packages.get(i).getPackageName());
							Label packageDescription = new Label(packages.get(i).getPackageDescription());
							Label moreInformation = new Label(packages.get(i).getMoreInformation());
							Label packageCost = new Label(packages.get(i).getPackageCost());
							Label billingPeriod = new Label(packages.get(i).getBillingPeriod());
							Label billingFrequency = new Label(packages.get(i).getBillingFrequency());
							HorizontalPanel formContainer = new HorizontalPanel();
							
							final FormPanel formPanel = new FormPanel();
							formPanel.setMethod(FormPanel.METHOD_POST);
							formPanel.setAction("/demo/paypal/payment/start");
							formPanel.setEncoding(FormPanel.ENCODING_URLENCODED);
							Hidden packagekey = new Hidden("packageKey", packages.get(i).getKey());
							PushButton getIt = new PushButton(new Image("https://fpdbs.sandbox.paypal.com/dynamicimageweb?cmd=_dynamic-image&PAL=NNRCSLN9N9366"));
							
						
							getIt.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									// TODO Auto-generated method stub
									formPanel.submit();
								}
							});
							formContainer.add(getIt);
							formContainer.add(packagekey);
							formPanel.setWidget(formContainer);
							packageTable.setWidget(i+1, 0, packageImage);
							packageTable.setWidget(i+1, 1, packageLabel);
							packageTable.setWidget(i+1, 2, packageDescription);
							packageTable.setWidget(i+1, 3, moreInformation);
							packageTable.setWidget(i+1, 4, packageCost);
							packageTable.setWidget(i+1, 5, billingPeriod);
							packageTable.setWidget(i+1, 6, billingFrequency);
							packageTable.setWidget(i+1, 7, formPanel);
							
							
						}
						
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
		this.packagePanel.add(packageTable);
		if(appUser.getUserRole().equalsIgnoreCase("ADMIN")){
			Anchor addNewPackageLink = new Anchor("Add New Package", "new");
			this.packagePanel.add(addNewPackageLink);
		}
		
	}
	
	private final native JsArray<ProductPackageJS> getPackagesFromJson(String json)/*-{
		return eval('('+json+')').productPackageDtoList;
	}-*/;
	
	
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
						//debug.setText("json is "+json);
						appUser = PaypalApplicationUserJs.fromJson(json);
						//debug.setText("json is "+json+" appUserEmail is"+appUser.getUserEmail());
						setupContentPanel();
					}
					else{
						//debug.setText("No output");
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