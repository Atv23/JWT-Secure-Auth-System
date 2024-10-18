package com.jwt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.dao.UserRepository;
import com.jwt.model.User;

@Service
public class userServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public User findUserByUsername(String username) {
		Optional<User> optional = this.userRepository.findByEmail(username);
		return optional.get();
	}

	@Override
	public User createUser(User user) {
		User savedUser = this.userRepository.save(user);
		return savedUser;
	}

}
