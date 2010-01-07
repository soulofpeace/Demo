package com.appspot.demo.client.paypal.dao;

import com.google.appengine.api.datastore.Key;

public interface PackageDao {
	public Package getPackageById(Key id);
	public void savePackage(Package packageName);
}
