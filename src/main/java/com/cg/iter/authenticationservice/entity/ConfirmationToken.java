package com.cg.iter.authenticationservice.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cg.iter.authenticationservice.entity.request.SignupRequest;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = SignupRequest.class, fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "signupRequestId")
    private SignupRequest user;

    
    
    public ConfirmationToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConfirmationToken(SignupRequest user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public SignupRequest getUser() {
		return user;
	}

	public void setUser(SignupRequest user) {
		this.user = user;
	}
    
    

   
}
