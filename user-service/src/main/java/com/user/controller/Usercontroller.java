package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.User;
import com.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class Usercontroller {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> listAllUsers(){
		List<User> users = userService.getAll();
		if(users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}
	
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user){
		User newUser = userService.saveUser(user);
		if(newUser == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(newUser);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findUserById(@PathVariable("id") int userId){
		User user = userService.findUserById(userId);
		if(user == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(user);
	}
	
}
