package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public User findUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User saveUser(User user) {
		User newUser = userRepository.save(user);
		return newUser;
	}
}
