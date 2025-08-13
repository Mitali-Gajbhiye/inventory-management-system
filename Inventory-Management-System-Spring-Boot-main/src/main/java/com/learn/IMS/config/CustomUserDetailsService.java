package com.learn.IMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learn.IMS.model.User;
import com.learn.IMS.repository.userRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private userRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found"));
		return new CustomUserDetails(user);
	}

}
