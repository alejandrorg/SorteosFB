package com.alejandrorg.fbsorteos.objects;

public class User {

	private String id;
	private String name;
	
	public User(String i, String n) {
		this.id = i;
		this.name = n;
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	
}
