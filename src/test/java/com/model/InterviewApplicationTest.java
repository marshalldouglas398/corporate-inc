package com.model;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class InterviewApplicationTest {
    InterviewApplication app = new InterviewApplication();
    
    /*
    *   TESTING LOGIN()
    */
    @Test
    public void testLoginValid() {
        User user = app.login("student", "password");
        assertTrue(user != null);
    }

    @Test
    public void testLoginInvalidUsername() {
        User user = app.login("", "password");
        assertTrue(user == null);
    }

    @Test
    public void testLoginInvalidPassword() {
        User user = app.login("student", "invalidpassword");
        assertTrue(user == null);
    }

    /*
    *   TESTING LOGOUT()
    */

    @Test
    public void testLogoutValid() {
        User user = app.login("student", "password");
        boolean result = app.logout(user);
        assertTrue(result);
    }

    @Test
    public void testLogoutInvalidUser() {
        boolean result = app.logout(null);
        assertTrue(result);
    }

    /*
    *   TESTING CREATEACCOUNT()
    */
    @Test
    public void testCreateAccountValid() {
        User user = app.createAccount("userA", "password123", new Date(), "a@example.com", "0123456789", "Computer Science");
        assertTrue(user != null);
    }

    @Test
    public void testCreateAccountEmptyUsername() {
        User user = app.createAccount("", "password123", new Date(), "b@example.com", "5139491005", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidUsername() {
        User user = app.createAccount("invalid username", "password123", new Date(), "c@example.com", "4124015439", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyPassword() {
        User user = app.createAccount("userD", "", new Date(), "d@example.com", "3523028369", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidPassword() {
        User user = app.createAccount("userE", ".", new Date(), "e@example.com", "3456789012", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyDateOfBirth() {
        User user = app.createAccount("userF", "password123", null, "F@example.com", "5933723305", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidDateOfBirth() {
        User user = app.createAccount("userG", "password123", new Date(System.currentTimeMillis() + 100000), "g@example.com", "8184727436", "Computer Science");
        assertTrue(user == null);
    }


    @Test
    public void testCreateAccountEmptyEmail() {
        User user = app.createAccount("userH", "password123", new Date(), "", "8519955095", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidEmail() {
        User user = app.createAccount("userI", "password123", new Date(), "invalidemail", "6954741211", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyUSCID() {
        User user = app.createAccount("userJ", "password123", new Date(), "j@example.com", "", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidUSCID() {
        User user = app.createAccount("userK", "password123", new Date(), "k@example.com", "invaliduscid", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyMajor() {
        User user = app.createAccount("userL", "password123", new Date(), "l@example.com", "7551533767", "");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidMajor() {
        User user = app.createAccount("userM", "password123", new Date(), "m@example.com", "9827774125", "Invalid Major");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountDuplicateUsername() {
        User user = app.createAccount("student", "password", new Date(), "duplicatestudent@example.com", "9293241797", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountDuplicateEmail() {
        User user = app.createAccount("testuser", "password", new Date(), "student@example.com", "7533476999", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountDuplicateUSCID() {
        User user = app.createAccount("testuser", "password", new Date(), "testuser@example.com", "123456890", "Computer Science");
        assertTrue(user == null);
    }

    /*
    *   TESTING DELETEUSER()
    */
   @Test
    public void testDeleteUserValidUser() {
        app.login("admin", "password");
        User newUser = app.createAccount("deleteMe", "password", new Date(), "deleteMe@example.com", "3456789012", "Computer Science");
        boolean result = app.deleteUser(newUser);      // Should be valid, admin is deleting account
        assertTrue(result);
    }

    @Test
    public void testDeleteUserInvalidUser() {
        app.login("student", "password");
        User newUser = app.createAccount("deleteMe", "password", new Date(), "deleteMe@example.com", "3456789012", "Computer Science");
        boolean result = app.deleteUser(newUser);   // Should NOT be valid, student is deleting account
        assertTrue(result);
    }

    @Test
    public void testDeleteUserInvalidAccount() {
        app.login("admin", "password");
        boolean result = app.deleteUser(null);
        assertTrue(result);
    }

    /*
    *   TESTING EDITUSER()
    */
    @Test
    public void testEditUserValid() {  //im not exactly sure how this method works, actually
        app.login("admin", "password");
        User newUser = app.createAccount("editMe", "password", new Date(), "editMe@example.com", "3456789012", "Computer Science");
    }

    /*
    *   TESTING FILTERQUESTION()
    *   WIP
    */

    @Test
    public void testFilterQuestionValid() {
        app.login("student", "password");
        assertTrue();
    }

    /*
    *   TESTING FINDUSER()
    */
    @Test
    public void testFindUserValid() {
        app.login("admin", "password");
        User user = app.findUser("student");
        assertTrue(user != null);
    }

    @Test
    public void testFindUserInvalid() {
        app.login("admin", "password");
        User user = app.findUser("nonexistentuser");
        assertTrue(user == null);
    }

    @Test
    public void testFindUserEmptyUsername() {
        app.login("admin", "password");
        User user = app.findUser("");
        assertTrue(user == null);
    }

    @Test
    public void testFindUserNullUsername() {
        app.login("admin", "password");
        User user = app.findUser(null);
        assertTrue(user == null);
    }
}
