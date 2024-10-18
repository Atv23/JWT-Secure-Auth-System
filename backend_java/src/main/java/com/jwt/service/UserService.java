package com.jwt.service;

import com.jwt.model.User;

public interface UserService {
	
	public User findUserByUsername(String username);
	
	public User createUser(User user);
}
