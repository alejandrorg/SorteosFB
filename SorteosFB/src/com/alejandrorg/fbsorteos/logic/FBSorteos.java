package com.alejandrorg.fbsorteos.logic;

import java.util.LinkedList;
import java.util.Observable;

import org.apache.commons.math3.random.MersenneTwister;

import com.alejandrorg.fbsorteos.gui.MainGUI;
import com.alejandrorg.fbsorteos.objects.ObserverMessage;
import com.alejandrorg.fbsorteos.objects.Post;
import com.alejandrorg.fbsorteos.objects.User;

public class FBSorteos extends Observable implements Runnable {

	private int magicNumber;
	private Post selectedPost;

	public FBSorteos(MainGUI obs, int mn, Post sp) {
		this.addObserver(obs);
		this.magicNumber = mn;
		this.selectedPost = sp;
	}

	public User getRandomUser(Post sp, int i) {
		LinkedList<User> users = sp.getLikes();
		MersenneTwister mt = new MersenneTwister(System.currentTimeMillis() * i);
		int nd = mt.nextInt(users.size());
		return users.get(nd);
	}

	public void run() {
		for (int i = 0; i < magicNumber; i++) {
			User us = getRandomUser(selectedPost, i + 1);
			this.setChanged();
			this.notifyObservers(new ObserverMessage(Constants.ADD_USER, us.getName() + " [" + us.getID() + "]"));
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		User winner = getRandomUser(selectedPost,new java.util.Random(System.currentTimeMillis()).nextInt(1000));
		this.setChanged();
		this.notifyObservers(new ObserverMessage(Constants.SET_WINNER, winner.getName() + " [" + winner.getID() + "]"));

	}

}
