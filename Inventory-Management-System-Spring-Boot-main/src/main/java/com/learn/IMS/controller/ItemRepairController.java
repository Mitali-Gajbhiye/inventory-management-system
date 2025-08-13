package com.learn.IMS.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.learn.IMS.convertor.ItemRepairConvertor;
import com.learn.IMS.dto.ItemRepairDto;
import com.learn.IMS.model.ItemRepair;
import com.learn.IMS.serviceImpl.ItemRepairServiceImpl;
import com.learn.IMS.serviceImpl.ItemServiceImpl;
import com.learn.IMS.serviceImpl.VendorServiceImpl;

@Controller
public class ItemRepairController {

	@Autowired
	private ItemRepairServiceImpl itemRepairServiceImpl;
	@Autowired
	private VendorServiceImpl vendorServiceImpl;
	@Autowired
	private ItemServiceImpl itemServiceImpl;
	@Autowired
	private ItemRepairConvertor itemRepairConvertor;

	@GetMapping("/ItemRepairView")
	public String View(Model model) {
		model.addAttribute("ItemRepairDtoList", itemRepairConvertor.modelToDto(itemRepairServiceImpl.getAllRepairItems()));
		return "/Item Repair/View";
	}

	@GetMapping("/ItemRepairCreate")
	public String Create(Model model) {
		ItemRepairDto itemRepairDto = new ItemRepairDto();
		model.addAttribute("itemRepairDto", itemRepairDto);
		return "/Item Repair/Create";
	}

	@PostMapping("/ItemRepairCreate")
	public String Create(@Valid @ModelAttribute("itemRepairDto") ItemRepairDto itemRepairDto, BindingResult result) {
		String err = vendorServiceImpl.validateVendorId(itemRepairDto.getVendorId());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}
		err = itemServiceImpl.validateItemId(itemRepairDto.getItemId());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}
		if (result.hasErrors()) {
			return "/Item Repair/Create";
		}

		itemRepairServiceImpl.saveItemRepair(itemRepairConvertor.DtoToModel(itemRepairDto));
		return "redirect:/ItemRepairView";

	}

	@GetMapping("/ItemRepairEdit/{id}")
	public String Edit(@PathVariable(value = "id") long id, Model model) {
		ItemRepair itemRepair = itemRepairServiceImpl.findItemRepairById(id);
		model.addAttribute("itemRepairDto", itemRepairConvertor.modelToDto(itemRepair));
		return "/Item Repair/Edit";
	}

	@GetMapping("/ItemRepairDelete/{id}")
	public String Delete(@PathVariable(value = "id") long id, Model model) {
		ItemRepair itemRepair = itemRepairServiceImpl.findItemRepairById(id);
		model.addAttribute("itemRepairDto", itemRepairConvertor.modelToDto(itemRepair));
		return "/Item Repair/Delete";
	}

	@PostMapping("/ItemRepairDelete/{id}")
	public String Delete(@PathVariable(value = "id") long id,
			@ModelAttribute("itemRepairDto") ItemRepairDto itemRepairDto) {
		itemRepairServiceImpl.deleteItemRepairById(id);
		return "redirect:/ItemRepairView";
	}

}
