package com.cg.iter.authenticationservice.entity;



public class NotificationResponse {


	private String username;
	private String email;
	private String phoneno;
	private String confirmationToken;



	public NotificationResponse() {}


	

	public NotificationResponse(String username, String email, String phoneno, String confirmationToken) {
		super();
		this.username = username;
		this.email = email;
		this.phoneno = phoneno;
		this.confirmationToken = confirmationToken;
	}




	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}


	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}


	
	
	
	
	
	
	
}
