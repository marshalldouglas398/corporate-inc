package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * This class represents an admin, which is a type of user that can create, edit, and delete questions
 * @author Marshall Pigford
 */
public class Admin extends User {
    private ArrayList<Question> questionsMade;
    
    /**
     * Parameterized constructor for the Admin class
     * @param username The username of the admin
     * @param password The password of the admin
     * @param dateOfBirth The date of birth of the admin
     * @param email The email of the admin
     */
    public Admin(String username, String password, Date dateOfBirth, String email) {
        super(username, password, dateOfBirth, email, "Admin");
        this.questionsMade = new ArrayList<>();
    }

    /**
     * Copy constructor for the Admin class
     * @param id The UUID of the admin to copy
     * @param username The username of the admin to copy
     * @param password The password of the admin to copy
     * @param dateOfBirth The date of birth of the admin to copy
     * @param email The email of the admin to copy
     */
    public Admin(UUID id, String username, String password, Date dateOfBirth, String email) {
        super(id, username, password, dateOfBirth, email, "Admin");
        this.questionsMade = new ArrayList<>();
    }

    /**
     * Removes a user from the database
     * @param username The username of the user to remove from the user list
     */
    public void deleteUser(String username) {
        UserList userList = UserList.getInstance();
        User user = userList.searchUser(username);
        userList.getUsers().remove(user);
        userList.save();
    }

    /**
     * Edits a user in the database
     * @param username The username of the user to edit in the database
     */
    public void editUser(String username, User user) {
        UserList userList = UserList.getInstance();
        User oldUser = userList.searchUser(username);
        userList.getUsers().remove(oldUser);
        userList.getUsers().add(user);
        userList.save();
    }

    /**
     * Gets the list of questions made by the admin
     * @return The list of questions made by the admin
     */
    public ArrayList<Question> getQuestionsMade() {
        return this.questionsMade;
    }

    /**
     * Checks if the user is an admin
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin() { return true; }
}
