package com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.entity.Company;
import com.company.feingClients.UserFeingClient;
import com.company.models.User;
import com.company.service.CompanyService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	
	@Autowired
	private CompanyService companyService;
	
	//***THIS METHODS ARE OF MICROSERVICE COMPANY***
	
	@GetMapping
	public ResponseEntity<List<Company>> listAllCompanies(){
		List<Company> companies = companyService.getAll();
			if(companies.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
		return ResponseEntity.ok(companies);
	}
	
	@PostMapping
	public ResponseEntity<Company> saveCompany(@RequestBody Company company){
		if(!companyService.searchCompany(company.getEmail())) {
			Company newCompany = companyService.saveCompany(company);
			if(newCompany == null) {
				return ResponseEntity.noContent().build();		
				}
			return ResponseEntity.ok(newCompany);
		}
		return new ResponseEntity("Company has already created, change the email or loggin with your password", HttpStatus.OK);
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> findCompanyById(@PathVariable("id") int companyId){
		Company company = companyService.findCompanyById(companyId);
		if(company == null) {
			return ResponseEntity.noContent().build();		}
		return ResponseEntity.ok(company);
	}
	
	//***THIS METHONDS NEED THE OTHERS MICROSERVICES***
	
	@CircuitBreaker(name= "UserCB", fallbackMethod = "fbSaveUser")
	@PostMapping("/users/{companyId}")
	public ResponseEntity<User> saveUser(@RequestBody User user, @PathVariable("companyId") int companyId){
		User newUser = companyService.saveUser(companyId, user);
		System.out.println("Entramos al metodo SAVEUSER del controller company");
		if(user == null ) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(newUser);
	}
	
	@CircuitBreaker(name= "UserCB", fallbackMethod = "fbGetAllUsers")
	@GetMapping("/users")
	public ResponseEntity<List<User>> listAllUsers(){
		List<User> users = companyService.getAllUsers();
		if(users == null ||users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}
	
	
	//***THIS METHODS ARE FOR THE FALLBACK OF CIRCUIT BREAKER***
	
	private ResponseEntity<User> fbSaveUser(RuntimeException ex){
		return new ResponseEntity("The service for add news users is not working, please try in five minutes.", HttpStatus.OK);
	}
	
	private ResponseEntity<List<User>> fbGetAllUsers(RuntimeException ex){
		return new ResponseEntity("The service for get all users is not working, please try in five minutes", HttpStatus.OK);
	}
}   

