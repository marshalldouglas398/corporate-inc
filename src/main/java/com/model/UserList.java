package com.model;

import java.util.ArrayList;

public class UserList {
    private static  UserList userlist;
    private ArrayList<User> users;

    private UserList() {
        this.users = new ArrayList<User>();
    }

    public static UserList getInstance() {
        if (userlist == null) {
            userlist = new UserList();
        }
        return userlist;
    }
    public User searchUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public boolean checkForUser(String password, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public boolean save() { // to do
        return true;
    }
    public boolean isAdmin(User user) {
        return user.isAdmin();
    }
    public User login(String username, String password) { //to do
        return getUser(username, password);
    }
}