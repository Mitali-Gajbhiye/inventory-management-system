package com.learn.IMS.model;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.learn.IMS.Utilities.Helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Loan")
@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_id")
	private long id;

	@Column(name = "loan_duration")
	private long loanDuration;

	@Column(name = "issue_date")
	private String issueDate;

	@Column(name = "return_date")
	private String returnDate;

	@Column(name = "total_fine")
	private double totalFine;

	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;

	
	@ManyToOne(fetch = FetchType.LAZY)
	private Borrower borrower;
	
	

	public double calculateFine() {
		double _totalFine = 0;
		double fineRate = 0;
		try {
			if (!returnDate.isEmpty()) {
				if (item.getItemType().getTypeName().equalsIgnoreCase("High Precedence")) {
					fineRate = Helper.highPrecedenceFineRate;
				}
				fineRate = Helper.lowPrecedenceFineRate;
				Date _issueDate = Helper.convertStringToDate(issueDate);
				Date _returnDate = Helper.convertStringToDate(returnDate);
				long daysBetween = ChronoUnit.DAYS.between(_issueDate.toInstant(), _returnDate.toInstant());
				if (daysBetween > 0) {
					_totalFine = daysBetween * fineRate;
				} else {
					_totalFine = 0;
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception Caught");
		}
		totalFine = _totalFine;
		return _totalFine;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Loan))
			return false;
		return (Long) id != null && id == (((Loan) o).getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
