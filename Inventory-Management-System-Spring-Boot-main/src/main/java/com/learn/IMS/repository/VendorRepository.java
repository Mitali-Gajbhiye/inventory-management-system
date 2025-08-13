package com.learn.IMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.IMS.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
