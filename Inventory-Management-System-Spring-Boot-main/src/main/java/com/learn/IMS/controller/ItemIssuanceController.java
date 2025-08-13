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

import com.learn.IMS.convertor.ItemIssuanceConvertor;
import com.learn.IMS.dto.ItemIssuanceDto;
import com.learn.IMS.model.Borrower;
import com.learn.IMS.model.Item;
import com.learn.IMS.model.Loan;
import com.learn.IMS.serviceImpl.BorrowerServiceImpl;
import com.learn.IMS.serviceImpl.ItemIssuanceServiceImpl;
import com.learn.IMS.serviceImpl.ItemServiceImpl;

@Controller
public class ItemIssuanceController {

	@Autowired
	private ItemIssuanceServiceImpl itemIssuanceServiceImpl;

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	@Autowired
	private BorrowerServiceImpl borrowerServiceImpl;

	@Autowired
	private ItemIssuanceConvertor itemIssuanceConvertor;

	@GetMapping("/ItemIssuanceView")
	public String View(Model model) {
		model.addAttribute("ItemIssuanceDtoList",
				itemIssuanceConvertor.modelToDto(itemIssuanceServiceImpl.getAllIssuedItems()));
		return "/Item Issuance/View";
	}

	@GetMapping("/ItemIssuanceCreate")
	public String Create(Model model) {
		ItemIssuanceDto itemIssuanceDto = new ItemIssuanceDto();
		model.addAttribute("itemIssuanceDto", itemIssuanceDto);
		return "/Item Issuance/Create";
	}

	@PostMapping("/ItemIssuanceCreate")
	public String Create(@Valid @ModelAttribute("itemIssuanceDto") ItemIssuanceDto itemIssuanceDto,
			BindingResult result) {
		Borrower borrower = null;
		Item item = null;
		String err = borrowerServiceImpl.validateBorrowerId(itemIssuanceDto.getBorrowerId());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		} else {
			borrower = borrowerServiceImpl.getBorrowerById(itemIssuanceDto.getBorrowerId());
		}
		err = itemServiceImpl.validateItemId(itemIssuanceDto.getItemId());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		} else {
			item = itemServiceImpl.getItemById(itemIssuanceDto.getItemId());
			if (item.getQuantity() <= 0) {
				err = "Item is out of stock.";
				ObjectError error = new ObjectError("globalError", err);
				result.addError(error);
			}
		}
		if (result.hasErrors()) {
			return "/Item Issuance/Create";
		}
		Loan loan = itemIssuanceConvertor.dtoToModel(itemIssuanceDto);
		borrower.addLoan(loan);
		item.addLoan(loan);
		item.descreaseQuantity();
		itemServiceImpl.saveItem(item);
		itemIssuanceServiceImpl.saveItemIssued(loan);
		return "redirect:/ItemIssuanceView";
	}

	@GetMapping("/ItemIssuanceEdit/{id}")
	public String Edit(@PathVariable(value = "id") long id, Model model) {
		Loan loan = itemIssuanceServiceImpl.findItemIssuedById(id);
		model.addAttribute("itemIssuanceDto", itemIssuanceConvertor.modelToDto(loan));
		return "/Item Issuance/Edit";
	}

	@GetMapping("/ItemIssuanceDelete/{id}")
	public String Delete(@PathVariable(value = "id") long id, Model model) {
		Loan loan = itemIssuanceServiceImpl.findItemIssuedById(id);
		model.addAttribute("itemIssuanceDto", itemIssuanceConvertor.modelToDto(loan));
		return "/Item Issuance/Delete";
	}

	@PostMapping("/ItemIssuanceDelete/{id}")
	public String Delete(@PathVariable(value = "id") long id,
			@ModelAttribute("itemIssuanceDto") ItemIssuanceDto itemIssuanceDto) {
		itemIssuanceServiceImpl.deleteIssuedItemById(id);
		return "redirect:/ItemIssuanceView";
	}

}
