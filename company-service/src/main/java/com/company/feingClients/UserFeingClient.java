package com.company.feingClients;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.models.User;

@FeignClient(name = "user-service")
public interface UserFeingClient {

	@PostMapping("/api/users")
	public User saveUser(@RequestBody User user);
	
	@GetMapping("/api/users")
	public List<User> getAllUsers();
	
}
