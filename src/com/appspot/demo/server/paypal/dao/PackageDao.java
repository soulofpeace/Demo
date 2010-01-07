package com.appspot.demo.server.paypal.dao;

import com.appspot.demo.server.paypal.model.ProductPackage;



public interface PackageDao {
	public Package getPackageById(String id);
	public void savePackage(ProductPackage productPackage);
}
