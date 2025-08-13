package com.learn.IMS.service;

import java.util.List;

import com.learn.IMS.model.ItemType;

public interface ItemTypeService {
	void saveItemType(ItemType itemType);
	ItemType getItemTypeByName(String name);
	List<ItemType> getAllItemTypes();
	String validateItemTypeByName(String name);
}
