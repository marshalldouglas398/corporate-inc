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
    

}
