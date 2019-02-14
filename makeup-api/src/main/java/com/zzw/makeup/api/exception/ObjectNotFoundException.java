package com.zzw.makeup.api.exception;

public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = -2372102257058185636L;

	private String message = null;

	public ObjectNotFoundException(Class<?> type) {
		setMessage(type.getSimpleName() + " not found!");
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
