package com.learn.IMS.convertor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learn.IMS.dto.ItemRepairDto;
import com.learn.IMS.model.ItemRepair;
import com.learn.IMS.serviceImpl.ItemServiceImpl;
import com.learn.IMS.serviceImpl.VendorServiceImpl;

@Component
public class ItemRepairConvertor {

	@Autowired
	private VendorServiceImpl vendorServiceImpl;

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	public ItemRepairDto modelToDto(ItemRepair item) {
		long itemId = 0;
		if (item.getItem() != null) {
			itemId = item.getItem().getId();
		}
		long itemRepairId = item.getId();
		long vendorId = item.getVendor().getId();
		double itemPrice = item.getPrice();
		double repairCost = item.getCost();
		ItemRepairDto itemRepairDto = new ItemRepairDto(itemRepairId, itemId, vendorId, repairCost, itemPrice);
		return itemRepairDto;
	}

	public ItemRepair DtoToModel(ItemRepairDto item) {
		ItemRepair itemRepair = new ItemRepair();
		itemRepair.setId(item.getId());
		itemRepair.setCost(item.getRepairCost());
		itemRepair.setPrice(item.getPrice());
		itemRepair.setItem(itemServiceImpl.getItemById(item.getItemId()));
		itemRepair.setVendor(vendorServiceImpl.getVendorById(item.getVendorId()));
		return itemRepair;
	}

	public List<ItemRepairDto> modelToDto(List<ItemRepair> item) {
		List<ItemRepairDto> DtoList = new ArrayList<>();
		int size = item.size();
		for (int i = 0; i < size; i++) {
			DtoList.add(modelToDto(item.get(i)));
		}
		return DtoList;
	}

	public List<ItemRepair> DtoToModel(List<ItemRepairDto> item) {
		List<ItemRepair> itemList = new ArrayList<>();
		int size = item.size();
		for (int i = 0; i < size; i++) {
			itemList.add(DtoToModel(item.get(i)));
		}
		return itemList;
	}
}
