package com.learn.IMS.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.IMS.model.Vendor;
import com.learn.IMS.repository.VendorRepository;
import com.learn.IMS.service.IVendorService;

@Service
public class VendorServiceImpl implements IVendorService {

	@Autowired
	private VendorRepository vendorRepository;

	@Override
	public Vendor getVendorById(long id) {
		Optional<Vendor> optional = vendorRepository.findById(id);
		Vendor vendor = null;
		if (optional.isPresent()) {
			vendor = optional.get();
		} else {
			// Exception
		}
		return vendor;
	}

	@Override
	public String validateVendorId(long id) {
		String errorMessage = "";
		Vendor vendor = getVendorById(id);
		if (vendor == null) {
			errorMessage = "Vendor ID does not exist";
		}

		return errorMessage;
	}

	@Override
	public Vendor getVendorByName(String name) {
		List<Vendor> vendorList = vendorRepository.findAll();
		Vendor vendor = null;
		for (Vendor v : vendorList) {
			if (v.getName().equalsIgnoreCase(name)) {
				vendor = v;
				break;
			}
		}
		return vendor;
	}

	@Override
	public String validateVendorName(String vendorName) {
		String errorMessage = "";
		Vendor vendor = getVendorByName(vendorName);
		if (vendor == null) {
			errorMessage = "Vendor with name: " + vendorName + " does not exist.";
		}
		return errorMessage;
	}

	@Override
	public void saveVendor(Vendor vendor) {
		vendorRepository.save(vendor);
		
	}
}
