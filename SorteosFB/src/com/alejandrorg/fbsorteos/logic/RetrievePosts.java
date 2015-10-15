package com.alejandrorg.fbsorteos.logic;

import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import com.alejandrorg.fbsorteos.gui.PostSelectorWindow;
import com.alejandrorg.fbsorteos.objects.ObserverMessage;
import com.alejandrorg.fbsorteos.objects.Post;
import com.alejandrorg.fbsorteos.objects.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RetrievePosts extends Observable implements Runnable {

	private String access_token;
	private Map<String, Post> posts;
	private int maxNumberPosts;
	
	public RetrievePosts(PostSelectorWindow mg, Map<String, Post> posts, int mnp) throws Exception {
		this.addObserver(mg);
		loadData();
		this.posts = posts;
		this.maxNumberPosts = mnp;
	}

	private void loadData() throws Exception {
		try {
			this.access_token = ConfigManager.getConfig(Constants.ACCESS_TOKEN);
		} catch (Exception e) {
			throw new Exception("Error getting access token: " + e.getMessage());
		}
	}

	private LinkedList<User> getLikes(String idp) throws Exception {
		LinkedList<User> likes = new LinkedList<User>();
		String urlLikes = Constants.GRAPH_BASE_URL_V22 + idp
				+ Constants.LIKES_OBJECT + Constants.ACCESS_TOKEN_PARAMETER
				+ access_token;
		URL url = new URL(urlLikes);
		getLikes(urlLikes, url.openStream(), likes);
		return likes;
	}
	
	private void getLikes(String url, InputStream is, LinkedList<User> likes)
			throws Exception {
		ObjectMapper om = new ObjectMapper();
		JsonNode root = om.readValue(is, JsonNode.class);
		Iterator<JsonNode> itRoot = root.get("data").iterator();
		while (itRoot.hasNext()) {
			JsonNode node = itRoot.next();
			if (node != null) {
				JsonNode id = node.get("id");
				JsonNode name = node.get("name");
				User us = new User(id.asText(), name.asText());
				likes.add(us);
			}
		}
		JsonNode paging = root.get("paging");
		if (paging != null) {
			JsonNode nextPage = paging.get("next");
			if (nextPage != null) {
				String urlNext = nextPage.asText();
				is.close();
				getLikes(urlNext, new URL(urlNext).openStream(), likes);
			}
		}
	}

	public void run() {
		try {
			getPosts();
			this.setChanged();
			this.notifyObservers(new ObserverMessage(Constants.POSTS_RETRIEVED));
		} catch (Exception e) {
			e.printStackTrace();
			this.setChanged();
			this.notifyObservers(new ObserverMessage(Constants.ERROR_MESSAGE, "Error obtaining post data: " + e.getMessage()));
		}
	}

	private void getPosts() throws Exception {
		String urlPosts = Constants.GRAPH_BASE_URL + "me/posts" + Constants.ACCESS_TOKEN_PARAMETER + access_token;
		URL url = new URL(urlPosts);
		int postsRetrieved = 0;
		getPosts(url.openStream(), postsRetrieved);
	}
	
	private void getPosts(InputStream is, int postsRetrieved) throws Exception {
		ObjectMapper om = new ObjectMapper();
		JsonNode root = om.readValue(is, JsonNode.class);
		Iterator<JsonNode> itRoot = root.get("data").iterator();

		while (itRoot.hasNext()) {
			JsonNode node = itRoot.next();
			if (node != null) {
				postsRetrieved++;
				JsonNode message = node.get("message");
				JsonNode id = node.get("id");
				JsonNode createdTime = node.get("created_time");
				String idPost = id.asText();
				Post p = new Post(idPost, message.asText(), createdTime.asText(), getLikes(idPost));
				this.posts.put(idPost, p);
				this.setChanged();
				this.notifyObservers(new ObserverMessage(Constants.UPDATE_GUI, "Obtenido post " + postsRetrieved + " de " + maxNumberPosts));
			}
			if (postsRetrieved >= maxNumberPosts) {
				return;
			}
		}
		JsonNode paging = root.get("paging");
		if (paging != null) {
			JsonNode nextPage = paging.get("next");
			if (nextPage != null) {
				String urlNext = nextPage.asText();
				is.close();
				getPosts(new URL(urlNext).openStream(), postsRetrieved);
			}
		}
		is.close();
	}
}
