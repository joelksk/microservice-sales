package com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.entity.Company;
import com.company.models.User;
import com.company.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	
	@Autowired
	private CompanyService companyService;
	
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
		Company newCompany = companyService.saveCompany(company);
		if(newCompany == null) {
			return ResponseEntity.noContent().build();		
			}
		return ResponseEntity.ok(newCompany);
	}
	
	@PostMapping("/users/{companyId}")
	public ResponseEntity<User> saveUser(@RequestBody User user, @PathVariable("companyId") int companyId){
		User newUser = companyService.saveUser(companyId, user);
		return ResponseEntity.ok(newUser);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> findCompanyById(@PathVariable("id") int companyId){
		Company company = companyService.findCompanyById(companyId);
		if(company == null) {
			return ResponseEntity.noContent().build();		}
		return ResponseEntity.ok(company);
	}
}   
