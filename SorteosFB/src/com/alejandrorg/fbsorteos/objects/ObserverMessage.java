package com.alejandrorg.fbsorteos.objects;

public class ObserverMessage {

	private String message;
	private int typeOfMessage;

	public ObserverMessage(int tom) {
		this.typeOfMessage = tom;
	}

	public ObserverMessage(int tom, String m) {
		this.typeOfMessage = tom;
		this.message = m;
	}

	public String getMessage() {
		return message;
	}

	public int getTypeOfMessage() {
		return typeOfMessage;
	}

}
