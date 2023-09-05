package com.company.feingClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.models.User;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserFeingClient {

	@PostMapping("/api/users")
	public User saveUser(@RequestBody User user);
	
}
