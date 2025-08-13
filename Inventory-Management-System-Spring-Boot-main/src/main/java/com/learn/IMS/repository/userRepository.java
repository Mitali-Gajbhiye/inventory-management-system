package com.learn.IMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.IMS.model.User;

@Repository
public interface userRepository extends JpaRepository<User, Long>{
	

	Optional<User> findByUserName(String userName);
	
	
}
