package com.cg.iter.authenticationservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iter.authenticationservice.entity.CapStoreRoles;
import com.cg.iter.authenticationservice.entity.User;
import com.cg.iter.authenticationservice.repository.UserRepository;
import com.cg.iter.authenticationservice.util.Validator;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	Validator validator;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthService authService;

	/****************************************************************************************************************************************
	 * - Function Name : deleteMerchant <br>
	 * - Description : Only admin can add a new merchant. Method returns a
	 * boolean. <br>
	 * 
	 * @param int userId
	 * @return boolean
	 ****************************************************************************************************************************************/
	@Override
	public boolean deleteMerchant(int userId) {
		userRepository.deleteById(userId);

		return true;
	}

	/****************************************************************************************************************************************
	 * - Function Name : addMerchant <br>
	 * - Description : Only admin can delete a merchant. Method return a
	 * boolean. <br>
	 * 
	 * @param User user
	 * @return boolean
	 ****************************************************************************************************************************************/
	@Override
	public boolean addMerchant(User user) {
		validator.checkPassword(user.getPassword());
		validator.checkPhoneNumber(user.getPhoneno());
		Set<String> roles = new HashSet<>();
		roles.add(CapStoreRoles.ROLE_CUSTOMER.toString());
		roles.add(CapStoreRoles.ROLE_MERCHANT.toString());
		user.setRoles(roles);
		authService.addUser(user);
		return true;
	}

	/****************************************************************************************************************************************
	 * - Function Name : viewAllMerchants <br>
	 * - Description : Only admin can view the list of merchants. Method
	 * returns a list. <br>
	 * 
	 * @return List<User>
	 ****************************************************************************************************************************************/
	@Override
	public List<User> viewAllMerchants() {
		List<User> resultList = new ArrayList<>();
		List<User> allUsers = userRepository.findAll();

		Iterator<User> itr = allUsers.iterator();

		int index = 0;

		while (itr.hasNext()) {

			if (allUsers.get(index).getRoles().contains(CapStoreRoles.ROLE_MERCHANT.toString())) {
				resultList.add(allUsers.get(index));
			}
			index++;
			itr.next();
		}
		return resultList;
	}

}
