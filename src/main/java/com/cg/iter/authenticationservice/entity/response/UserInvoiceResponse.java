package com.cg.iter.authenticationservice.entity.response;

public class UserInvoiceResponse {

	private String email;
	private String name;
	private String phoneno;
	public UserInvoiceResponse(String email, String name, String phoneno) {
		super();
		this.email = email;
		this.name = name;
		this.phoneno = phoneno;
	}
	public UserInvoiceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	
	
	
	
	
	
}
