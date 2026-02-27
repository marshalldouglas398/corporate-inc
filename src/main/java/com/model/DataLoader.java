package com.model;

import java.util.ArrayList;

public class DataLoader {
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        return users;
    }
}

public static void main(String[] args){
    ArrayList<User> users = DataLoader.getUsers();
    for(User user : users) {
        System.out.println(user);
    }
}
