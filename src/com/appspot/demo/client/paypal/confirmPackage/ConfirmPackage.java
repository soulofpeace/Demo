package com.appspot.demo.client.paypal.confirmPackage;

import com.appspot.demo.client.paypal.dto.js.ProductPackageJS;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class ConfirmPackage implements EntryPoint {
	private ProductPackageJS productPackage;
	private FlexTable displayTable = new FlexTable();

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		String packageKey = Window.Location.getParameter("packageKey");
		this.getProductPackage(packageKey);
		RootPanel.get().add(displayTable);
		
		final FormPanel submitformPanel = new FormPanel("_self");
		submitformPanel.setMethod(FormPanel.METHOD_GET);
		submitformPanel.setAction("/demo/paypal/payment/confirm");
		submitformPanel.setEncoding(FormPanel.ENCODING_URLENCODED);
		
		final FormPanel cancelformPanel = new FormPanel("_self");
		cancelformPanel.setMethod(FormPanel.METHOD_GET);
		cancelformPanel.setAction("/demo/paypal/payment/cancel");
		cancelformPanel.setEncoding(FormPanel.ENCODING_URLENCODED);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		Button cancelButton = new Button("Cancel");
		Button confirmButton = new Button("Confirm");
		submitformPanel.setWidget(confirmButton);
		confirmButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				submitformPanel.submit();
			}
		});
		cancelformPanel.setWidget(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				cancelformPanel.submit();
			}
		});
		buttonPanel.add(submitformPanel);
		buttonPanel.add(cancelformPanel);
		RootPanel.get().add(buttonPanel);
		
		
	}
	
	private void getProductPackage(String packageKey){
		String requestURL="https://choonkeedemo.appspot.com/demo/paypal/productpackage/get/"+packageKey+".json";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, requestURL);
		requestBuilder.setHeader("Content-Type", "application/json");
		try{
			Request request = requestBuilder.sendRequest(null, new RequestCallback() {
			
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					if(response.getStatusCode()==200){
						String json = response.getText();
						productPackage=ProductPackageJS.fromJson(json);
						buildDisplayTable();
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
	
	private void buildDisplayTable(){
		
		Label packageLogoLabel = new Label("Package Logo");
		Image packageLogo = new Image();
		packageLogo.setUrl(this.productPackage.getPackageImageURL());
		this.displayTable.setWidget(0, 0, packageLogoLabel);
		this.displayTable.setWidget(0, 1, packageLogo);
		
		Label packageNameLabel = new Label("Package Name");
		Label packageName = new Label(this.productPackage.getPackageName());
		this.displayTable.setWidget(1, 0, packageNameLabel);
		this.displayTable.setWidget(1, 1, packageName);
		
		Label packageDescriptionLabel = new Label("Package Description");
		Label packageDescription = new Label(this.productPackage.getPackageDescription());
		this.displayTable.setWidget(2, 0, packageDescriptionLabel);
		this.displayTable.setWidget(2, 1, packageDescription);
		
		Label moreInformationLabel = new Label("More Information");
		Label moreInformation =new Label(this.productPackage.getMoreInformation());
		this.displayTable.setWidget(3, 0, moreInformationLabel);
		this.displayTable.setWidget(3, 1, moreInformation);
		
		Label packageCostLabel = new Label("Package Cost");
		Label packageCost = new Label(this.productPackage.getPackageCost());
		this.displayTable.setWidget(4, 0, packageCostLabel);
		this.displayTable.setWidget(4, 1, packageCost);
		
		Label billingPeriodLabel = new Label("Billing Period");
		Label billingPeriod = new Label(productPackage.getBillingPeriod());
		this.displayTable.setWidget(6, 0, billingPeriodLabel);
		this.displayTable.setWidget(6, 1, billingPeriod);
		
		Label billingFrequencyLabel = new Label("Billing Frequency");
		Label billingFrequency = new Label(productPackage.getBillingFrequency());
		this.displayTable.setWidget(7, 0, billingFrequencyLabel);
		this.displayTable.setWidget(7, 1, billingFrequency);
		
	}

}
