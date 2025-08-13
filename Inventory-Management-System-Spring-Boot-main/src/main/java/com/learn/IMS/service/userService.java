package com.learn.IMS.service;

import com.learn.IMS.model.User;

public interface userService {
	public void saveUser(User user);
	public boolean hasRole(String userName, String role);
	public Long count();

}
