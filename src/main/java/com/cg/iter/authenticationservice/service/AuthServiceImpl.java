package com.cg.iter.authenticationservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cg.iter.authenticationservice.entity.CapStoreRoles;
import com.cg.iter.authenticationservice.entity.ConfirmationToken;
import com.cg.iter.authenticationservice.entity.NotificationResponse;
import com.cg.iter.authenticationservice.entity.User;
import com.cg.iter.authenticationservice.entity.request.LoginRequest;
import com.cg.iter.authenticationservice.entity.request.SignupRequest;
import com.cg.iter.authenticationservice.entity.response.JwtResponse;
import com.cg.iter.authenticationservice.entity.response.UserInvoiceResponse;
import com.cg.iter.authenticationservice.repository.ConfirmationRepository;
import com.cg.iter.authenticationservice.repository.UserRepository;
import com.cg.iter.authenticationservice.util.GenerateUserId;
import com.cg.iter.authenticationservice.util.JwtUtil;
import com.cg.iter.authenticationservice.util.Validator;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	GenerateUserId generate;
	
	@Autowired
	JwtUtil jwtUtils;
	
	@Autowired
	Validator validator;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ConfirmationRepository confirmationRepository;
	
	@Value("${notification.service.url}")
	private String notificationURL;
	
	
	/****************************************************************************************************************************************
	 * - Function Name : authenticateUser <br>
	 * - Description : it will authenticate the user with user id and password and return details and authentication token as response <br>
	 * 
	 * @param LoginRequest loginRequest
	 * @return ResponseEntity<JwtResponse>
	 ****************************************************************************************************************************************/
	@Override
	public ResponseEntity<JwtResponse> authenticateUser(@Valid LoginRequest loginRequest) {
		User user = userRepository.findByEmail(loginRequest.getEmail()).get();
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 userDetails.getPhoneno(),
												 roles));
	}

	
	

	 
	
	
	 
	/****************************************************************************************************************************************
	 * - Function Name : registerUser <br>
	 * - Description : it will authenticate the user with user , create a new user and return success of failed message. <br>
	 * 
	 * @param SignupRequest signUpRequest
	 * @return ResponseEntity<?> registerUser
	 ****************************************************************************************************************************************/
	@Override
	public ResponseEntity<?> registerUser(@Valid SignupRequest signUpRequest) {
		
		validator.checkPassword(signUpRequest.getPassword());
		validator.checkPhoneNumber(signUpRequest.getPhoneno());
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
		}
		
		ConfirmationToken confirmationToken = new ConfirmationToken(signUpRequest);
		confirmationRepository.save(confirmationToken);
		NotificationResponse notificationResponse = new NotificationResponse(signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPhoneno(), confirmationToken.getConfirmationToken());
		restTemplate.postForObject(notificationURL+"/welcomeMessage/send",notificationResponse, String.class);
		
		return ResponseEntity.accepted().body("A link is sent to your email address. Please verify your email!!");
		
	
		
	}





	/****************************************************************************************************************************************
	 * - Function Name : editUser <br>
	 * - Description : Any actor can edit existing actor details and return a boolean value. <br>
	 * 
	 * @param User user
	 * @return boolean
	 ****************************************************************************************************************************************/
	@Override
	public boolean editUser(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			userRepository.save(user);
			return true;
		}
		return false;
	}





	
	/****************************************************************************************************************************************
	 * - Function Name : deleteUser <br>
	 * - Description : Admin can delete existing user or product master and return a boolean value. <br>
	 * 
	 * @param User user
	 * @return boolean
	 ****************************************************************************************************************************************/
	@Override
	public boolean deleteUser(User user) {
		userRepository.delete(user);
		return true;
	}





	/****************************************************************************************************************************************
	 * - Function Name : getDbCount <br>
	 * - Description : It will return the number of rows in userDB to generate key. <br>
	 * 
	 * @return long
	 ****************************************************************************************************************************************/
	@Override
	public long getDbCount() {
		return userRepository.count();
	}





	/****************************************************************************************************************************************
	 * - Function Name : addUser <br>
	 * - Description : Admin can add user or product master and return a boolean value. <br>
	 * 
	 * @param User user
	 * @return boolean
	 ****************************************************************************************************************************************/
	@Override
	public boolean addUser(User user) {
		user.setId(generate.generateUserId(user.getPhoneno()));
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return true;
	}



	/****************************************************************************************************************************************
	 * Function Name : confirmAccount <br>
	 * Description : Function will register the user with confirmed email address.<br>
	 * 
	 * @param String confirmationToken
	 * @return String
	 ****************************************************************************************************************************************/
	@Override
	public String confirmAccount(String confirmationToken) {

		ConfirmationToken token = confirmationRepository.findByConfirmationToken(confirmationToken);
		SignupRequest signUpRequest = token.getUser();

		// Create new user's account

		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getEmail(), signUpRequest.getPhoneno());

		Set<String> strRoles = signUpRequest.getRole();
		Set<String> roles = new HashSet<>();

		if (strRoles == null) {
			new RuntimeException("Error: Role is not found.");
		}

		else {
			strRoles.forEach(role -> {

				switch (role) {
				case "admin":
					roles.add(CapStoreRoles.ROLE_ADMIN.toString());
					break;

				case "merchant":
					roles.add(CapStoreRoles.ROLE_MERCHANT.toString());
					break;

				default:
					roles.add(CapStoreRoles.ROLE_CUSTOMER.toString());
				}
			});
		}

		user.setRoles(roles);
		user.setId(generate.generateUserId(signUpRequest.getPhoneno()));
		userRepository.save(user);

		// authenticate the same user to login
		LoginRequest login = new LoginRequest();
		login.setEmail(signUpRequest.getEmail());
		login.setPassword(signUpRequest.getPassword());

		return "Email verified successfully!! Please signin again";

	}







	/****************************************************************************************************************************************
	 * Function Name : getUserInvoiceResponse <br>
	 * Description : Function will provide user details for given userId to generate invoice.<br>
	 * 
	 * @param String userId
	 * @return UserInvoiceResponse
	 ****************************************************************************************************************************************/
	@Override
	public UserInvoiceResponse getUserInvoiceResponse(String userId) {
		User user = userRepository.findById(Integer.parseInt(userId)).get();
		UserInvoiceResponse response = new UserInvoiceResponse(user.getEmail(), user.getUsername(), user.getPhoneno());
		return response;
	}
	
}
