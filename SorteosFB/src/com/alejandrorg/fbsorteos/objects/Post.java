package com.alejandrorg.fbsorteos.objects;

import java.util.LinkedList;

public class Post {

	private String message;
	private String createdTime;
	private String id;
	private LinkedList<User> likes;

	public Post(String id, String m, String cd, LinkedList<User> l) {
		this.message = m;
		this.createdTime = cd;
		this.likes = l;
		this.id = id;
	}
	public String getID() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public LinkedList<User> getLikes() {
		return this.likes;
	}

}
