package com.learn.IMS.service;

import java.util.List;

import com.learn.IMS.model.Item;

public interface ItemService {
	Item getItemById(long id);

	String validateItemId(long id);

	long findItemIdByLoanId(long loanId);

	List<Item> getAllItems();

	void saveItem(Item item);

	String validateItemId(String itemName, String itemType);

	void deleteItem(long itemId, String userName);
	
	public long count();
}
