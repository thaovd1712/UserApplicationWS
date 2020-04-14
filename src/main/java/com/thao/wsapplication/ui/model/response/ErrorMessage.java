package com.thao.wsapplication.ui.model.response;

import java.util.Date;

public class ErrorMessage {
	private Date timestamp;
	private String message;

	public ErrorMessage(Date date, String message) {
		super();
		this.timestamp = date;
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
