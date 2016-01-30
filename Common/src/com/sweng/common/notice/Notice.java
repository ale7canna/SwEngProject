package com.sweng.common.notice;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public abstract class Notice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Date date;
	String title;
	String message;
	
	public Notice(String _title, String _message) {
		date = Date.from(Instant.now());
		title = _title;
		message = _message;
		
	}
	
	public Date getDate() {
		
		return date;
	}
	
	public String getMessage() {
		
		return message;
	}
	
	public String getTitle() {
		
		return title;
	}
	
}
