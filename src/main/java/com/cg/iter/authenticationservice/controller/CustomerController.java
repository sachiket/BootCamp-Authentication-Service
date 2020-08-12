package com.cg.iter.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.iter.authenticationservice.entity.User;
import com.cg.iter.authenticationservice.service.AuthService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/app/user")
public class CustomerController {
	
	@Autowired
	AuthService authService;
	
	
	@ApiOperation(
			value = "Edit user",
			notes = "Any authenticated user can edit their profile in this API",
			response = String.class
			)
	@PostMapping("/editUser")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('MERCHANT') or hasRole('ADMIN') ")
	public String editUser(@RequestBody User user) {
		if(authService.editUser(user)) {
			return "User updated successfully";
		}
		return "fail to edit user!!";
	}
	
	 
	
	

}
