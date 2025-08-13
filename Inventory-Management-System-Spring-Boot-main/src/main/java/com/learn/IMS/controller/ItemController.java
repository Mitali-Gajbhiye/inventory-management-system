package com.learn.IMS.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.learn.IMS.convertor.ItemConvertor;
import com.learn.IMS.dto.ItemDto;
import com.learn.IMS.model.Item;
import com.learn.IMS.model.ItemType;
import com.learn.IMS.model.Vendor;
import com.learn.IMS.serviceImpl.ItemServiceImpl;
import com.learn.IMS.serviceImpl.ItemTypeServiceImpl;
import com.learn.IMS.serviceImpl.VendorServiceImpl;

@Controller
public class ItemController {

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	@Autowired
	private ItemTypeServiceImpl itemTypeServiceImpl;

	@Autowired
	private VendorServiceImpl vendorServiceImpl;

	@Autowired
	private ItemConvertor itemConvertor;
	


	@GetMapping("/ItemView")
	public String View(Model model) {
		model.addAttribute("itemDtoList", itemConvertor.modelToDto(itemServiceImpl.getAllItems()));
		return "/Item/View";
	}

	@GetMapping("/ItemCreate")
	public String Create(Model model) {
		ItemDto itemDto = new ItemDto();
		model.addAttribute("itemDto", itemDto);
		model.addAttribute("itemTypeList", itemTypeServiceImpl.getAllItemTypes());
		return "/Item/Create";
	}

	@PostMapping("/ItemCreate")
	public String Create(@Valid @ModelAttribute("itemDto") ItemDto itemDto, BindingResult result, Model model) {
		Vendor vendor = null;
		ItemType itemType = null;
		Item item = null;
		String err = vendorServiceImpl.validateVendorName(itemDto.getVendorName());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		} else {
			vendor = vendorServiceImpl.getVendorByName(itemDto.getVendorName());
		}
		err = itemTypeServiceImpl.validateItemTypeByName(itemDto.getItemType());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		} else {
			itemType = itemTypeServiceImpl.getItemTypeByName(itemDto.getItemType());
		}
		err = itemServiceImpl.validateItemId(itemDto.getItemName(), itemDto.getItemType());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}

		if (result.hasErrors()) {
			model.addAttribute("itemDtoList", itemConvertor.modelToDto(itemServiceImpl.getAllItems()));
			return "/Item/Create";
		}
		item = itemConvertor.dtoToModel(itemDto);
		item.setVendor(vendor);
		item.setItemType(itemType);
		itemServiceImpl.saveItem(item);
		return "redirect:/ItemView";
	}

	@GetMapping("/ItemEdit/{id}")
	public String Edit(@PathVariable(value = "id") long id, Model model) {
		Item item = itemServiceImpl.getItemById(id);
		model.addAttribute("itemDto", itemConvertor.modelToDto(item));
		model.addAttribute("itemTypeList", itemTypeServiceImpl.getAllItemTypes());
		return "/Item/Edit";
	}

	@GetMapping("/ItemDelete/{id}")
	public String Delete(@PathVariable(value = "id") long id, Model model) {
		Item item = itemServiceImpl.getItemById(id);
		model.addAttribute("itemDto", itemConvertor.modelToDto(item));
		return "/Item/Delete";
	}


	@PostMapping("/ItemDelete/{id}")
	public String Delete(@PathVariable(value = "id") long id, @ModelAttribute("itemDto") ItemDto itemDto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		itemServiceImpl.deleteItem(id, username);
		return "redirect:/ItemView";
	}

}
