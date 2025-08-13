package com.learn.IMS.service;

import com.learn.IMS.model.Vendor;

public interface IVendorService {
	void saveVendor(Vendor vendor);
	
	Vendor getVendorById(long id);

	Vendor getVendorByName(String name);

	String validateVendorId(long id);
	
	String validateVendorName(String vendorName);
}
