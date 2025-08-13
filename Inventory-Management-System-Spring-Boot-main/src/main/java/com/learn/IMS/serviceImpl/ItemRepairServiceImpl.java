package com.learn.IMS.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.IMS.model.ItemRepair;
import com.learn.IMS.repository.ItemRepairRepository;
import com.learn.IMS.service.ItemRepairService;

@Service
public class ItemRepairServiceImpl implements ItemRepairService {

	@Autowired
	private ItemRepairRepository itemRepairRepository;

	@Override
	public List<ItemRepair> getAllRepairItems() {
		return itemRepairRepository.findAll();
	}

	@Override
	public void saveItemRepair(ItemRepair itemRepair) {
		this.itemRepairRepository.save(itemRepair);
	}

	@Override
	public void deleteItemRepairById(long id) {
		this.itemRepairRepository.deleteById(id);
	}

	@Override
	public ItemRepair findItemRepairById(long id) {
		Optional<ItemRepair> optional = itemRepairRepository.findById(id);
		ItemRepair itemRepair = null;
		if (optional.isPresent()) {
			itemRepair = optional.get();
		} else {
			// Exception
		}
		return itemRepair;
	}

}
