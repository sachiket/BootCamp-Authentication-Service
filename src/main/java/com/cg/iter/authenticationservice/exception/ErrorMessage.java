package com.cg.iter.authenticationservice.exception;

public class ErrorMessage{
	private String message;
	private String details;
	private long timestamp;
	
	public ErrorMessage() {}
	
	
	
	public ErrorMessage(String message, String details, long timestamp) {
		super();
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
	}



	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}



	public long getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}