package controllers;

public class LookUpToken {
	private boolean status;
	private String message;

	public LookUpToken(boolean status, String message) {
		this.status = status;
		this.message = message;
	}

	public boolean getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
