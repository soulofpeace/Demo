package com.appspot.demo.client.paypal.productPackage;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PaypalPackage implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		final FormPanel addPackageForm = new FormPanel();
		addPackageForm.setAction("/paypal/package/add");
		addPackageForm.setMethod(FormPanel.METHOD_POST);
		VerticalPanel containerPanel = new VerticalPanel();
		
		Label packageNameLabel = new Label("Package Name");
		TextBox packageNameInput = new TextBox();
		packageNameInput.setName("packageName");
		containerPanel.add(packageNameLabel);
		containerPanel.add(packageNameInput);
		
		Label packageDescriptionLabel = new Label("Package Description");
		TextBox packageDescriptionInput = new TextBox();
		packageDescriptionInput.setName("packageDescription");
		containerPanel.add(packageDescriptionLabel);
		containerPanel.add(packageDescriptionInput);
		
		Label packageImageURLLabel = new Label("Package Image URL");
		TextBox packageImageURLInput = new TextBox();
		packageImageURLInput.setName("packageImageURL");
		containerPanel.add(packageImageURLLabel);
		containerPanel.add(packageImageURLInput);
	
		Label packageCostLabel = new Label("Package Cost");
		TextBox packageCostInput = new TextBox();
		packageCostInput.setName("packageCost");
		containerPanel.add(packageCostLabel);
		containerPanel.add(packageCostInput);
		
		Label billingPeriodLabel = new Label("Billing Period");
		ListBox billingPeriodListBox = new ListBox();
		billingPeriodListBox.addItem("Day");
		billingPeriodListBox.addItem("Week");
		billingPeriodListBox.addItem("SemiMonth");
		billingPeriodListBox.addItem("Month");
		billingPeriodListBox.addItem("Year");
		billingPeriodListBox.setName("billingPeriod");
		containerPanel.add(billingPeriodLabel);
		containerPanel.add(billingPeriodListBox);
		
		Label billingFrequencyLabel = new Label("Billing Frequency");
		TextBox billingFrequencyInput = new TextBox();
		billingFrequencyInput.setName("billingFrequency");
		containerPanel.add(billingFrequencyLabel);
		containerPanel.add(billingFrequencyInput);
		
		Button addButton = new Button("Submit", new ClickHandler() {
		      public void onClick(ClickEvent event) {
		        addPackageForm.submit();
		      }
		});
		containerPanel.add(addButton);
		addPackageForm.setWidget(containerPanel);
		RootPanel.get().add(addPackageForm);
	}

}
