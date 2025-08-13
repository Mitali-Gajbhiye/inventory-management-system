package com.learn.IMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.IMS.model.ItemType;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

}