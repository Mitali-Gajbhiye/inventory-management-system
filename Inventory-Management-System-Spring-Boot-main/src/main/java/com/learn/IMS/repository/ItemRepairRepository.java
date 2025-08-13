package com.learn.IMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.IMS.model.ItemRepair;

@Repository
public interface ItemRepairRepository extends JpaRepository<ItemRepair, Long> {

}
