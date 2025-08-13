package com.learn.IMS.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Setter
@Getter
@Table(name = "Borrower")
public class Borrower {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "borrower_id")
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;
	
	private String userName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "borrower")
	private List<Loan> loan = new ArrayList<>();


	public void addLoan(Loan newLoan) {
		this.loan.add(newLoan);
		newLoan.setBorrower(this);
	}

	public void removeLoan(Loan newLoan) {
		this.loan.remove(newLoan);
		newLoan.setBorrower(null);
	}

	public double totalFine() {
		double totalFine = 0;
		for (Loan l : loan) {
			totalFine += l.calculateFine();
		}
		return totalFine;
	}

	public void updateFine(double finePaid) {
		double fine = 0;
		for (Loan l : loan) {
			if (finePaid <= 0) {
				break;
			} else {
				fine = l.getTotalFine();
				l.setTotalFine(fine - finePaid);
				finePaid -= fine;
			}
		}
	}

	public Object findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
