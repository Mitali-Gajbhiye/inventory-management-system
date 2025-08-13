package com.learn.IMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.IMS.model.Loan;

public interface ItemIssuanceRepository extends JpaRepository<Loan, Long> {

}
