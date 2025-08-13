package com.learn.IMS.service;

import java.util.List;

import com.learn.IMS.model.ItemRepair;

public interface ItemRepairService {
	List<ItemRepair> getAllRepairItems();

	void saveItemRepair(ItemRepair itemRepair);

	void deleteItemRepairById(long id);

	ItemRepair findItemRepairById(long id);
}