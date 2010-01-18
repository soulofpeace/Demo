package com.appspot.demo.client.paypal.success;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class PaypalSuccess implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		String success = Window.Location.getParameter("success");
		if (success.equalsIgnoreCase("true")){
			Label label=new Label("Package Successfully purchased");
			Label emailMessage = new Label("Email Receipt Confirmation for transaction Id sent to");
			RootPanel.get().add(label);
		}
		else{
			Label label = new Label("Failed to Purchase Package");
			RootPanel.get().add(label);
		}
		Anchor packageListLink = new Anchor("Back to Package List", "http://choonkeedemo.appspot.com/demo/paypal/productpackage/view");
		RootPanel.get().add(packageListLink);
	}

}
