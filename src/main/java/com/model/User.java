package com.model;

import java.util.Date;
import java.util.UUID;

/**
 * A class representing a user of the software
 * @author Marshall Pigford
 */
public class User {
    protected String username;
    protected String password;
    protected Date dateOfBirth;
    protected UUID id;
    protected String email;

    /**
     * Parameterized constructor for the User class
     * @param username The username of the user
     * @param password The password of the user
     * @param dateOfBirth The date of birth of the user
     * @param email The email of the user
     */
    public User(String username, String password, Date dateOfBirth, String email) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.id = UUID.randomUUID();
        this.email = email;
    }

    /**
     * Copy constructor for the User class
     * @param id The UUID of the user to copy
     */
    public User(UUID id) {
        // You need to implement user retrieval first before you can implement this constructor
    }

    /**
     * Gets the username of the user
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Resets the password of the user
     * @param password The new password to set for the user
     */
    public void resetPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is an editor
     * @return true if the user is an editor, false otherwise
     */
    public boolean isEditor() { return false; } // This method will be overridden in the Editor class

    /**
     * Checks if the user is an admin
     * @return true if the user is an admin, false otherwise
     */
    public boolean isAdmin() { return false; } // This method will be overridden in the Admin class
}
