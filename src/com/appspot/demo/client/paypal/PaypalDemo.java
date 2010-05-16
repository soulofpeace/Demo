package com.appspot.demo.client.paypal;

import java.util.Date;

import com.appspot.demo.client.paypal.dto.js.InvoiceJs;
import com.appspot.demo.client.paypal.dto.js.PaypalApplicationUserJs;
import com.appspot.demo.client.paypal.dto.js.PaypalTransactionJs;
import com.appspot.demo.client.paypal.dto.js.ProductPackageJS;
import com.appspot.demo.server.paypal.model.Role;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class PaypalDemo implements EntryPoint {
	
	private VerticalPanel packagePanel = new VerticalPanel();
	private VerticalPanel invoicePanel = new VerticalPanel();
	private VerticalPanel transactionPanel = new VerticalPanel();
	private VerticalPanel purchasedPackagePanel = new VerticalPanel();
	private TabPanel contentPanel = new TabPanel();
	private FlexTable packageTable = new FlexTable();
	private FlexTable invoiceTable = new FlexTable();
	private FlexTable transactionTable = new FlexTable();
	private PaypalApplicationUserJs appUser;
	private boolean isAdmin = false;
	

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
		this.setupInvoicePanel();
		this.purchasedPackagePanel.add(invoicePanel);
		this.contentPanel.add(this.packagePanel, "Available Packages");
		this.contentPanel.add(this.purchasedPackagePanel, "Purchased Packages");
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
		//TextBox startDateTextBox = new TextBox();
		
		if(!isAdmin){
			this.getUserInvoice();
		}
		
		final DatePanel datePanel = this.setupDatePanel();
		
		Button searchButton = new Button("Search");
		searchButton.setEnabled(true);
		searchButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Date endDate = DateTimeFormat.getMediumDateFormat().parse(datePanel.getEndDate());
				Date startDate = DateTimeFormat.getMediumDateFormat().parse(datePanel.getStartDate());
				if(endDate.compareTo(startDate)==-1){
					DialogBox error = getErrorDialogBox("End Date cannot be earlier than Start Date");
					error.show();
				}else{
					getUserInvoiceByDate(datePanel.getStartDate(), datePanel.getEndDate());
				}
				
			}
		});
		datePanel.add(searchButton);
		
		this.invoicePanel.add(datePanel);
		this.invoicePanel.add(invoiceTable);
	}
	
	private DatePanel setupDatePanel(){
		
		final DatePanel datePanel = new DatePanel();

		HorizontalPanel startDatePanel = new HorizontalPanel();
		Label startDate = new Label("Start Date");
		final TextBox startDateBox = new TextBox();
		startDateBox.setReadOnly(true);
		startDateBox.setText(DateTimeFormat.getMediumDateFormat().format(new Date()));
		datePanel.setStartDate(DateTimeFormat.getMediumDateFormat().format(new Date()));
		final PopupPanel startDatePopup = new PopupPanel(true);
		DatePicker startDatePicker = new DatePicker();
		startDatePicker.setTitle("Start Date");
		startDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				// TODO Auto-generated method stub
				Date date = event.getValue();
		        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
		        startDateBox.setText(dateString);
		        datePanel.setStartDate(dateString);
		        startDatePopup.hide();
			}
		});
		
		startDatePopup.setWidget(startDatePicker);
		startDateBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				startDatePopup.show();
			}
		});
		startDatePanel.add(startDate);
		startDatePanel.add(startDateBox);
		
		HorizontalPanel endDatePanel = new HorizontalPanel();
		Label endDate = new Label("End Date");
		final TextBox endDateBox = new TextBox();
		endDateBox.setReadOnly(true);
		endDateBox.setText(DateTimeFormat.getMediumDateFormat().format(new Date()));
		datePanel.setEndDate(DateTimeFormat.getMediumDateFormat().format(new Date()));
		DatePicker endDatePicker = new DatePicker();
		endDatePicker.setTitle("End Date");
		final PopupPanel endDatePopup = new PopupPanel();
		endDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				// TODO Auto-generated method stub
				Date date = event.getValue();
		        String dateString = DateTimeFormat.getMediumDateFormat().format(date);
		        endDateBox.setText(dateString);
		        datePanel.setEndDate(dateString);
		        endDatePopup.hide();
			}
		});
		endDatePopup.setWidget(endDatePicker);
		endDateBox.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				endDatePopup.show();
			}
		});
		endDatePanel.add(endDate);
		endDatePanel.add(endDateBox);
		
		
		
		datePanel.add(startDatePanel);
		datePanel.add(endDatePanel);
		return datePanel;
		
		
	}
	
	private DialogBox getErrorDialogBox(String message){
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		dialogBox.setGlassEnabled(true);
		dialogBox.setText("Wassup");
		VerticalPanel contentPanel = new VerticalPanel();
		Label messageLabel = new Label(message);
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				dialogBox.hide();
			}
		});
		contentPanel.add(messageLabel);
		contentPanel.add(closeButton);
		dialogBox.setWidget(contentPanel);
		dialogBox.setPopupPosition(Window.getClientWidth()/2, Window.getClientHeight()/2);
		return dialogBox;
	}
	
	private void setupTransactionPanel(String invoiceId){
	
		String url ="https://choonkeedemo.appspot.com/demo/paypal/transaction/invoice/"+invoiceId+"/get.json";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
		requestBuilder.setHeader("Content-Type", "application/json");
		final DatePanel datePanel = this.setupDatePanel();
		try{
			Request request = requestBuilder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					if(200==response.getStatusCode()){
						String json = response.getText();
						JsArray<PaypalTransactionJs> transactions = getTransactionsFromJson(json);
						populateTransactionTable(transactions);
						Button backbutton1 = new Button("Back to Invoices");
						backbutton1.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								purchasedPackagePanel.remove(transactionPanel);
								transactionPanel = new VerticalPanel();
								setupInvoicePanel();
								purchasedPackagePanel.add(invoicePanel);
							}
						});
						transactionPanel.add(backbutton1);
						transactionPanel.add(transactionTable);
						Button backbutton2 = new Button("Back to Invoices");
						backbutton2.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								purchasedPackagePanel.remove(transactionPanel);
								transactionPanel = new VerticalPanel();
								setupInvoicePanel();
								purchasedPackagePanel.add(invoicePanel);
							}
						});
						transactionPanel.add(backbutton2);
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
	
	private void populateTransactionTable(JsArray<PaypalTransactionJs> transactions){
		this.transactionTable.clear();
		this.transactionTable.setText(0, 0, "Id");
		this.transactionTable.setText(0, 1, "Date Created");
		this.transactionTable.setText(0, 2, "Paypal Transaction Id");
		this.transactionTable.setText(0, 3, "Parent Transaction Id");
		this.transactionTable.setText(0, 4, "Recipt Id");
		this.transactionTable.setText(0, 5, "Transaction Type");
		this.transactionTable.setText(0, 6, "Payment Type");
		this.transactionTable.setText(0, 7, "Order Time");
		this.transactionTable.setText(0, 8, "Amount");
		this.transactionTable.setText(0, 9, "Currency Code");
		this.transactionTable.setText(0, 10, "Fee Amount");
		this.transactionTable.setText(0, 11, "Settle Amount");
		this.transactionTable.setText(0, 12, "Tax Amount");
		this.transactionTable.setText(0, 13, "Exchange Rate");
		this.transactionTable.setText(0, 14, "Pending Reason");
		this.transactionTable.setText(0, 15, "Reason Code");
		this.transactionTable.setText(0, 16, "Protection Eligibility");
		this.transactionTable.setText(0, 17, "Custom");
		this.transactionTable.setText(0, 18, "Note");
		this.transactionTable.setText(0, 19, "Sale Tax");
		this.transactionTable.setText(0, 20, "Payment Date");
		this.transactionTable.setText(0, 21, "Subject");
		this.transactionTable.setText(0, 22, "Shipping");
		this.transactionTable.setText(0, 23, "Payment Gross");
		this.transactionTable.setText(0, 24, "Payment Status");
		this.transactionTable.getRowFormatter().addStyleName(0, "packageHeader");
		this.transactionTable.addStyleName("packageList");
		
		for(int i =0; i<transactions.length(); i++){
			Label id = new Label(transactions.get(i).getKey());
			Label dateCreated = new Label(transactions.get(i).getDateCreated());
			Label paypalTransactionId = new Label(transactions.get(i).getTransactionId());
			Label parentTransactionId = new Label(transactions.get(i).getParentTransactionId());
			Label receiptId = new Label(transactions.get(i).getReceiptId());
			Label transactionType = new Label(transactions.get(i).getTransactionType());
			Label paymentType = new Label(transactions.get(i).getPaymentType());
			Label orderTime = new Label(transactions.get(i).getOrderTime());
			Label amount = new Label(transactions.get(i).getAmount());
			Label currencyCode = new Label(transactions.get(i).getCurrencyCode());
			Label feeAmount = new Label(transactions.get(i).getFeeAmount());
			Label settleAmount = new Label(transactions.get(i).getSettleAmount());
			Label taxAmount = new Label(transactions.get(i).getTaxAmount());
			Label exchangeRate = new Label(transactions.get(i).getExchangeRate());
			Label pendingReason = new Label(transactions.get(i).getPendingReason());
			Label reasonCode = new Label(transactions.get(i).getReasonCode());
			Label protectionEligbility = new Label(transactions.get(i).getProtectionEligibility());
			Label custom = new Label(transactions.get(i).getCustom());
			Label note = new Label(transactions.get(i).getNote());
			Label saleTax = new Label(transactions.get(i).getSalesTax());
			Label paymentDate = new Label(transactions.get(i).getPaymentDate());
			Label subject = new Label(transactions.get(i).getSubject());
			Label shipping = new Label(transactions.get(i).getShipping());
			Label paymentGross = new Label(transactions.get(i).getPaymentGross());
			Label paymentStatus = new Label(transactions.get(i).getPaymentStatus());
			
			transactionTable.setWidget(i+1, 0, id);
			transactionTable.setWidget(i+1, 1, dateCreated);
			transactionTable.setWidget(i+1, 2, paypalTransactionId);
			transactionTable.setWidget(i+1, 3, parentTransactionId);
			transactionTable.setWidget(i+1, 4, receiptId);
			transactionTable.setWidget(i+1, 5, transactionType);
			transactionTable.setWidget(i+1, 6, paymentType);
			transactionTable.setWidget(i+1, 7, orderTime);
			transactionTable.setWidget(i+1, 8, amount);
			transactionTable.setWidget(i+1, 9, currencyCode);
			transactionTable.setWidget(i+1, 10, feeAmount);
			transactionTable.setWidget(i+1, 11, settleAmount);
			transactionTable.setWidget(i+1, 12, taxAmount);
			transactionTable.setWidget(i+1, 13, exchangeRate);
			transactionTable.setWidget(i+1, 14, pendingReason);
			transactionTable.setWidget(i+1, 15, reasonCode);
			transactionTable.setWidget(i+1, 16, protectionEligbility);
			transactionTable.setWidget(i+1, 17, custom);
			transactionTable.setWidget(i+1, 18, note);
			transactionTable.setWidget(i+1, 19, saleTax);
			transactionTable.setWidget(i+1, 20, paymentDate);
			transactionTable.setWidget(i+1, 21, subject);
			transactionTable.setWidget(i+1, 22, shipping);
			transactionTable.setWidget(i+1, 23, paymentGross);
			transactionTable.setWidget(i+1, 24, paymentStatus);
			
		}
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
		
		String url="https://choonkeedemo.appspot.com/demo/paypal/productpackage/get.json";
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
		if(isAdmin){
			Anchor addNewPackageLink = new Anchor("Add New Package", "new");
			this.packagePanel.add(addNewPackageLink);
		}
		
	}
	
	private void getUserInvoiceByDate(String startDate, String endDate){
		String url ="https://choonkeedemo.appspot.com/demo/paypal/invoice/startDate/"+startDate+"/endDate/"+endDate+"/get.json";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
		requestBuilder.setHeader("Content-Type", "application/json");
		try{
			Request request = requestBuilder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					if(200==response.getStatusCode()){
						String json = response.getText();
						JsArray<InvoiceJs> invoices = getInvoicesFromJson(json);
						populateInvoiceTable(invoices);
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
	
	
	private void getUserInvoice(){
		String url ="https://choonkeedemo.appspot.com/demo/paypal/invoice/get.json";
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
		requestBuilder.setHeader("Content-Type", "application/json");
		try{
			Request request = requestBuilder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					// TODO Auto-generated method stub
					if(200==response.getStatusCode()){
						String json = response.getText();
						JsArray<InvoiceJs> invoices = getInvoicesFromJson(json);
						populateInvoiceTable(invoices);
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
	
	private final native JsArray<ProductPackageJS> getPackagesFromJson(String json)/*-{
		return eval('('+json+')').productPackageDtoList;
	}-*/;
	
	private final native JsArray<InvoiceJs> getInvoicesFromJson(String json)/*-{
		return eval('('+json+')').invoiceDtoList;
	}-*/;
	
	private final native JsArray<PaypalTransactionJs> getTransactionsFromJson(String json)/*-{
		return eval('('+json+')').transactionDtoList;
	}-*/;
	
	
	private void populateInvoiceTable(JsArray<InvoiceJs> invoices){
		this.invoiceTable.clear();
		this.invoiceTable.setText(0, 0, "Id");
		this.invoiceTable.setText(0, 1, "Created Date");
		this.invoiceTable.setText(0, 2, "Modified Date");
		this.invoiceTable.setText(0, 3, "Status");
		this.invoiceTable.setText(0, 4, "Outstanding Balance");
		this.invoiceTable.setText(0, 5, "Package Name");
		this.invoiceTable.setText(0, 6, "Paypal Email");
		this.invoiceTable.setText(0, 7, "Next Payment Date");
		this.invoiceTable.setText(0, 8, "Currency Code");
		this.invoiceTable.setText(0, 9, "Intial Payment Amount");
		this.invoiceTable.setText(0, 10, "Shipping");
		this.invoiceTable.setText(0, 11, "Tax");
		this.invoiceTable.setText(0, 12, "Action");
		this.invoiceTable.getRowFormatter().addStyleName(0, "packageHeader");
		this.invoiceTable.addStyleName("packageList");
		for(int i =0; i<invoices.length();i++){
			final Label id = new Label(invoices.get(i).getKey());
			Label createdDate = new Label(invoices.get(i).getCreatedDate());
			Label modifiedDate = new Label(invoices.get(i).getModifiedDate());
			Label status = new Label(invoices.get(i).getStatus());
			Label outstandingBalance = new Label(invoices.get(i).getOutstandingBalance());
			Label packageName = new Label(invoices.get(i).getProductPackageName());
			Label paypalEmail = new Label(invoices.get(i).getCustomerEmail());
			Label nextPaymentDate = new Label(invoices.get(i).getNextPaymentDate());
			Label currencyCode = new Label(invoices.get(i).getCurrencyCode());
			Label initialPaymentAmount = new Label(invoices.get(i).getInitialPaymentAmount());
			Label shipping = new Label(invoices.get(i).getShipping());
			Label tax = new Label(invoices.get(i).getTax());
	
			
			invoiceTable.setWidget(i+1, 0, id);
			invoiceTable.setWidget(i+1, 1, createdDate);
			invoiceTable.setWidget(i+1, 2, modifiedDate);
			invoiceTable.setWidget(i+1, 3, status);
			invoiceTable.setWidget(i+1, 4, outstandingBalance);
			invoiceTable.setWidget(i+1, 5, packageName);
			invoiceTable.setWidget(i+1, 6, paypalEmail);
			invoiceTable.setWidget(i+1, 7, nextPaymentDate);
			invoiceTable.setWidget(i+1, 8, currencyCode);
			invoiceTable.setWidget(i+1, 9, initialPaymentAmount);
			invoiceTable.setWidget(i+1, 10, shipping);
			invoiceTable.setWidget(i+1, 11, tax);
			
			Button viewTransaction = new Button("View Transactions");
			viewTransaction.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					setupTransactionPanel(id.getText());
					purchasedPackagePanel.remove(invoicePanel);
					invoicePanel = new VerticalPanel();
					purchasedPackagePanel.add(transactionPanel);
				}
			});
			
			invoiceTable.setWidget(i+1, 12, viewTransaction);
		}
	}
	
	private void getCurrentAppUser(){
		String url="https://choonkeedemo.appspot.com/demo/paypal/appuser/get.json";
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
						if(appUser.getUserRole().equalsIgnoreCase("ADMIN")){
							isAdmin = true;
						}
						else{
							isAdmin = false;
						}
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
	
	private class DatePanel extends HorizontalPanel{
		
		private String startDate;
		private String endDate;
		
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getEndDate() {
			return endDate;
		}
		
		
		
	}

}