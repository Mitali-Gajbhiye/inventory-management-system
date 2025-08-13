package com.learn.IMS.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.learn.IMS.Utilities.Helper;
import com.learn.IMS.convertor.ItemIssuanceConvertor;
import com.learn.IMS.dto.ItemReturnDto;
import com.learn.IMS.model.Borrower;
import com.learn.IMS.model.Item;
import com.learn.IMS.model.Loan;
import com.learn.IMS.serviceImpl.BorrowerServiceImpl;
import com.learn.IMS.serviceImpl.ItemIssuanceServiceImpl;
import com.learn.IMS.serviceImpl.ItemServiceImpl;

@Controller
public class ItemReturnController {

	@Autowired
	private BorrowerServiceImpl borrowerServiceImpl;

	@Autowired
	private ItemServiceImpl itemServiceImpl;

	@Autowired
	private ItemIssuanceServiceImpl itemIssuanceServiceImpl;

	@Autowired
	private ItemIssuanceConvertor itemIssuanceConvertor;

	@GetMapping("/ItemReturnView")
	public String Index(Model model) {
		model.addAttribute("ItemIssuanceDtoList",
				itemIssuanceConvertor.modelToDto(itemIssuanceServiceImpl.getAllReturnedItem()));
		return "/Item Return/View";
	}

	@GetMapping("/ItemReturnCreate")
	public String Create(Model model) {
		ItemReturnDto itemReturnDto = new ItemReturnDto();
		model.addAttribute("itemReturnDto", itemReturnDto);
		return "/Item Return/Create";
	}

	@PostMapping("/ItemReturnCreate")
	public String Create(@Valid @ModelAttribute("itemReturnDto") ItemReturnDto itemReturnDto, BindingResult result) {
		itemReturnDto.setReturnDate(Helper.getCurrentTime());
		Borrower borrower = null;
		Item item = null;
		Loan loan = null;

		String err = borrowerServiceImpl.validateBorrowerId(itemReturnDto.getBorrowerId());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		} else {
			borrower = borrowerServiceImpl.getBorrowerById(itemReturnDto.getBorrowerId());
		}
		err = itemServiceImpl.validateItemId(itemReturnDto.getItemId());
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		} else {
			item = itemServiceImpl.getItemById(itemReturnDto.getItemId());
		}

		try {
			loan = itemIssuanceServiceImpl.findItemIssued(borrower.getId(), item.getId());
			borrower.removeLoan(loan);
			item.removeLoan(loan);
			loan.setReturnDate(err);
			loan.calculateFine();
			item.increaseQuantity();
			itemServiceImpl.saveItem(item);
			itemIssuanceServiceImpl.saveItemIssued(loan);
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception Caught in Item Return Controller.");
			err = "Loan ID does not exist. Invalid input";
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}
		if (result.hasErrors()) {
			return "/Item Return/Create";
		}
		return "redirect:/ItemReturnView";
	}
}
