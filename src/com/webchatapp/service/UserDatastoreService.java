package com.webchatapp.service;

import java.util.List;

import com.webchatapp.hibernatebeans.User;

public interface UserDatastoreService {
	/**
	 * This is the method to be used to create the User corresponding to a
	 * passed user's username and password.
	 */
	public void createUserByHibernate(String name, String password);

	/**
	 * This is the method to be used to logged in by a passed user's username
	 * and password.
	 */
	public User loginByUsernameAndPassword(String username, String password);

	/**
	 * This is the method to be used to get all users list.
	 */
	public List getAllUsers(String loggedUserName);

	/**
	 * This is the method to be used to get all online users list.
	 */
	public List getOnlineUsers(String loggedUserName);

	/**
	 * This is the method to be used to change users live status as user in
	 * logged in or not.
	 */
	public void changeMyOnlineStatus(int loggedUserId, int status);
}
