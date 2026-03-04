package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
/**
 * This class represents a list of all users
 * @author Eden Moore
 */
public class UserList {
    private static UserList userlist;
    private ArrayList<User> users;

    private UserList() {
        this.users = DataLoader.getUsers();
    }
/**
 * Gets the instance of the UserList class
 * @return The instance of the UserList class
 */
    public static UserList getInstance() {
        if (userlist == null) {
            userlist = new UserList();
            userlist.users = DataLoader.getUsers();
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
    public User getUser(UUID uuid) {
        for (User user : users) {
            if (user.getID().equals(uuid.toString())) {
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
    public boolean checkForUser(String username, String password) {
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
    public User login(String username, String password) { // changed to unbreak might need to fix again
        boolean userExists = checkForUser(username, password);
        if (!userExists) {
            return null;
        } else {
            return searchUser(username);
        }
    }
    /**
     * Returns the list of users
     * @return list of users
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> newUsers = new ArrayList<>();
        for (User user : users) {
            newUsers.add(user);
        }
        return newUsers;
    }

    public boolean addUser(String username, String password, Date dateOfBirth, String email, String role) {
        if (checkForUser(username, password) == true) {
            return false;
        }
        User newUser = new User(username, password, dateOfBirth, email, role);
        users.add(newUser);
        System.out.println("Users in memory: " + users.size());
        System.out.println("Created: " + newUser.getUsername());
        System.out.println("UserList instance in addUser: " + this);
        /*for (User user : users) {
            System.out.println("User in list: " + user.getUsername());
        }*/
       save();
        return true;

    }
}