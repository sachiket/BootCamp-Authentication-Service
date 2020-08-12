package com.cg.iter.authenticationservice.entity.request;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "signup_tbl")
public class SignupRequest {

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="signup_id")
    private long signupid;
	
	@NotBlank
    private String username;
 
    @NotBlank
    @Email
    private String email;
    
    
    @ElementCollection
	@CollectionTable(name = "signup_roles", joinColumns = @JoinColumn(name = "signup_id"))
	@Column(name = "roles")
	private Set<String> role = new HashSet<>();
    
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String phoneno;
  
    public String getUsername() {
        return username;
    }
 
    
    public String getPhoneno() {
		return phoneno;
	}


	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
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
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
}
