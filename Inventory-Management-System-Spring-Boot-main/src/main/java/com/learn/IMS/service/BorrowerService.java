package com.learn.IMS.service;

import java.util.List;

import com.learn.IMS.model.Borrower;

public interface BorrowerService {
	Borrower getBorrowerById(long id);

	List<Borrower> getAllBorrowers();

	long getBorrowerIdByLoanId(long loanId);

	String validateBorrowerId(long borrowerId);

	void updateBorrower(Borrower borrower);
	
	public Long count();
}
