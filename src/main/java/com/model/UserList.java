package com.model;

import java.util.ArrayList;
/**
 * This class represents a list of all users
 * @author Eden Moore
 */
public class UserList {
    private static  UserList userlist;
    private ArrayList<User> users;

    private UserList() {
        this.users = new ArrayList<User>();
    }
/**
 * Gets the instance of the UserList class
 * @return The instance of the UserList class
 */
    public static UserList getInstance() {
        if (userlist == null) {
            userlist = new UserList();
        }
        return userlist;
    }
    /**
     * Searches for a user in the list of users
     * @param username username of the user you want to find
     * @return the user if found, null otherwise
     */
    public User searchUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    /**
     * returns a user based on username and password
     * @param username username of the user you want to find
     * @param password password of the user you want to find
     * @return the user if found, null otherwise
     */
    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    /**
     * Checks if a user exists in the list of users based on username and password
     * @param password password of the user you want to find
     * @param username username of the user you want to find
     * @return true if found, false if not
     */
    public boolean checkForUser(String password, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Saves the list of users to the database
     * @return true if saved, false if not
     */
    public boolean save() {
        return DataWriter.saveUsers();
    }
    /**
     * Checks if a user is an admin
     * @param user the user you want to check
     * @return true if admin, false if not
     */
    public boolean isAdmin(User user) {
        return user.isAdmin();
    }
    /**
     * Logs in a user based on username and password
     * @param username username of the user you want to log in
     * @param password password of the user you want to log in
     * @return the user who logged in
     */
    public User login(String username, String password) {
        boolean userExists = checkForUser(password, username);
        if (!userExists) {
            return null;
        } else {
            return getUser(username, password);
        }
    }
    /**
     * Logs out a user
     * @param user user you want to log out
     * @return null as user has been logged out
     */
    public User logout(User user) { // may need updating
        UserList.getInstance().save();
        QuestionList.getInstance().save();
        return null;
    }
}