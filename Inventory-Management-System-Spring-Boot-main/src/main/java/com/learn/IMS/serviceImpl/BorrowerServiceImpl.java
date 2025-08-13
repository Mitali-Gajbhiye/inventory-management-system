package com.learn.IMS.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.IMS.model.Borrower;
import com.learn.IMS.model.Loan;
import com.learn.IMS.repository.BorrowerRepository;
import com.learn.IMS.service.BorrowerService;

@Service
public class BorrowerServiceImpl implements BorrowerService {

	@Autowired
	private BorrowerRepository borrowerRepository;

	@Override
	public Borrower getBorrowerById(long id) {
		return borrowerRepository.findById(id).orElseThrow(() -> new RuntimeException("Borrower Id not found"));
	}

	@Override
	public List<Borrower> getAllBorrowers() {
		return borrowerRepository.findAll();
	}

	@Override
	public long getBorrowerIdByLoanId(long loanId) {
		List<Borrower> borrowerList = getAllBorrowers();
		List<Loan> loanList;
		boolean found = false;
		long borrowerId = -1;
		for (Borrower borrower : borrowerList) {
			loanList = borrower.getLoan();
			for (Loan loan : loanList) {
				if (loan.getId() == loanId) {
					borrowerId = borrower.getId();
					found = true;
					break;
				}
			}
			if (found) {
				break;
			}
		}
		return borrowerId;
	}

	@Override
	public String validateBorrowerId(long borrowerId) {
		String errorMessage = "";
		Borrower borrower = getBorrowerById(borrowerId);
		if (borrower == null) {
			errorMessage = "Borrower id does not exist.";
		}
		return errorMessage;
	}

	@Override
	public void updateBorrower(Borrower borrower) {
		borrowerRepository.save(borrower);

	}

	@Override
	public Long count() {
		return borrowerRepository.count();
	}

}
