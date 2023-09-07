package com.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.entity.Company;
import com.company.feingClients.UserFeingClient;
import com.company.models.User;
import com.company.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserFeingClient userFeingClient;
	
	public List<Company> getAll(){
		return companyRepository.findAll();
	}
	
	public Company findCompanyById(int companyId) {
		return companyRepository.findById(companyId).orElse(null);
	}
	
	public Company saveCompany(Company company) {
		Company newCompany = companyRepository.save(company);
		return newCompany;
	}
	
	public boolean searchCompany(String email) {
		Company company = companyRepository.findByEmail(email);
		if(company==null) {
			return false;
		}
		return true;
	}
	
	
	//***THIS METHONDS NEED THE OTHERS MICROSERVICES***
	
	public User saveUser(int id, User user) {
		user.setCompanyId(id);
		User newUser = userFeingClient.saveUser(user);
		return newUser;
	}
	
	public List<User> getAllUsers(){
		List<User> users = userFeingClient.getAllUsers();
		return users;
	}
}
