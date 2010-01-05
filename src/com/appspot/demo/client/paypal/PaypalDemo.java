package com.appspot.demo.client.paypal;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PaypalDemo implements EntryPoint {
	
	private VerticalPanel contentPanel = new VerticalPanel();
	private FormPanel formPanel = new FormPanel();
	private FlexTable packageTable = new FlexTable();

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		this.setupContentPanel();
		RootPanel.get().add(contentPanel);

	}
	
	private void setupContentPanel(){
		this.setupFormPanel();
		this.contentPanel.add(this.formPanel);
	}
	
	private void setupFormPanel(){
		this.formPanel.setMethod(FormPanel.METHOD_POST);
		this.setupPackageTable();
		this.formPanel.setWidget(packageTable);
	}
	
	private void setupPackageTable(){
		this.packageTable.setText(0, 0, "Package Logo");
		this.packageTable.setText(0, 1, "Package Name");
		this.packageTable.setText(0, 2, "Package Description");
		this.packageTable.setText(0, 3, "Package Cost");
		this.packageTable.setText(0, 4, "Action");
		this.packageTable.getRowFormatter().addStyleName(0, "packageHeader");
		this.packageTable.addStyleName("packageList");
		Image packageImage = new Image();
		packageImage.setUrl("http://www.socialwok.com/themes/socialwok/images/logo.png");
		Label packageLabel = new Label("Package1");
		Label packageDescription = new Label("Package1 is for something");
		Label packageCost = new Label("$5 Per month");
		SubmitButton getIt = new SubmitButton("Get It!");
		this.packageTable.setWidget(1, 0, packageImage);
		this.packageTable.setWidget(1, 1, packageLabel);
		this.packageTable.setWidget(1, 2, packageDescription);
		this.packageTable.setWidget(1, 3, packageCost);
		this.packageTable.setWidget(1, 4, getIt);
	}

}