package com.appspot.demo.server.paypal.dao;

import java.util.List;

import com.appspot.demo.server.paypal.model.RecurringProductPackage;



public interface PackageDao {
	public RecurringProductPackage getPackageById(String id);
	public String savePackage(RecurringProductPackage productPackage);
	public List<RecurringProductPackage> getAllPackages();
}
