package com.learn.IMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.IMS.model.Borrower;
import com.learn.IMS.model.User;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
	Optional<User> findByUserName(String userName);

}
