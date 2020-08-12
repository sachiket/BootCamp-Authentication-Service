package com.cg.iter.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.iter.authenticationservice.entity.ConfirmationToken;

public interface ConfirmationRepository extends JpaRepository<ConfirmationToken,Integer>{
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
