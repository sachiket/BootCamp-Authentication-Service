package com.cg.iter.authenticationservice.service;

import java.util.List;

import com.cg.iter.authenticationservice.entity.User;

public interface AdminService {

	
	
	/****************************************************************************************************************************************
	 * - Function Name : deleteMerchant <br>
	 * - Description : Only admin can add a new merchant. Method returns a boolean. <br>
	 * 
	 * @param int userId
	 * @return boolean
	 ****************************************************************************************************************************************/
	boolean deleteMerchant(int userId);

	
	
	/****************************************************************************************************************************************
	 * - Function Name : addMerchant <br>
	 * - Description : Only admin can delete a merchant. Method return a boolean. <br>
	 * 
	 * @param User user
	 * @return boolean
	 ****************************************************************************************************************************************/
	boolean addMerchant(User user);

	
	

	/****************************************************************************************************************************************
	 * - Function Name : viewAllMerchants <br>
	 * - Description : Only admin can view the list of merchants. Method returns a list. <br>
	 * 
	 * @return List<User>
	 ****************************************************************************************************************************************/
	List<User> viewAllMerchants();

}
