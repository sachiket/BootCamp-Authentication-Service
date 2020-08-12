package com.cg.iter.authenticationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iter.authenticationservice.entity.User;
import com.cg.iter.authenticationservice.exception.NullParameterException;
import com.cg.iter.authenticationservice.exception.UserNotFoundException;
import com.cg.iter.authenticationservice.service.AdminService;


import io.swagger.annotations.ApiOperation;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/app/admin")
public class AdminController {

	
	@Autowired
	AdminService adminService;
	
	
	@ApiOperation(
			value = "Delete merchant by Id",
			notes = "Admin can remove a merchant in this API",
			response = String.class
			)
	@DeleteMapping("/deleteMerchant")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteMerchant(@RequestParam int userId) {
		if(adminService.deleteMerchant(userId)) {
			return "Merchant deleted successfully";
		}
		throw new UserNotFoundException("Invalid merchant ID!!");
	}
	
	
	@ApiOperation(
			value = "Add merchant",
			notes = "Admin add a merchant in this API",
			response = User.class
			)
	@PostMapping("/addMerchant")
	@PreAuthorize("hasRole('ADMIN')")
	public String addMerchant(@RequestBody User merchant) {
		
		if(merchant==null || merchant.getUsername()==null)throw new NullParameterException("Please provide details of Merchant!");
		
		if(adminService.addMerchant(merchant)) {
			return "Merchant added successfully";
		}
		return "fail to add Merchant!!";
	}
	 

	
	
	@ApiOperation(
			value = "View all Merchants",
			notes = "Admin view the list of Merchants in this API",
			response = List.class
			)
	@GetMapping("/viewAllMerchants")
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> viewAllMerchants() {
		return adminService.viewAllMerchants();
		
	}
	 
	

	
	 
	

	
	 
	
	
	
}
