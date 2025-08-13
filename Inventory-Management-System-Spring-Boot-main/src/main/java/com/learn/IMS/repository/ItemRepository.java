package com.learn.IMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.IMS.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
