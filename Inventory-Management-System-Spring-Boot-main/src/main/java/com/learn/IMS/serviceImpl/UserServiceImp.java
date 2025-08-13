package com.learn.IMS.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.IMS.model.User;
import com.learn.IMS.repository.userRepository;
import com.learn.IMS.service.userService;

@Service
public class UserServiceImp implements userService{
	
	@Autowired
	private userRepository userRepo;

	@Override
	public void saveUser(User user) {
		userRepo.save(user);	
	}

	
//	  public boolean hasRole(String username, String roleName) {
//	        return userRepo.findByUserName(username)
//	        		.filter(user -> user.getRole()
//	                        .stream()
//	                        .anyMatch(role -> role.getName().equalsIgnoreCase(roleName)))
//	                .isPresent();	        
//	    }
	  
	@Override
	public boolean hasRole(String username, String roleName) {
	    return userRepo.findByUserName(username)
	            .map(user -> user.getRole().equalsIgnoreCase(roleName))
	            .orElse(false);
	}


	    public Long count() {
	        return userRepo.count();
	    }
}
