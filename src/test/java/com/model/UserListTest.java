package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class UserListTest {
    private static final UUID ALICE_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID BOB_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final UUID MISSING_ID = UUID.fromString("99999999-9999-9999-9999-999999999999");
    private static final Date FIXED_DATE = new Date(0L);

    private UserList userList;
    private ArrayList<User> controlledUsers;

    @Before
    public void setUp() throws Exception {
        controlledUsers = new ArrayList<>();
        controlledUsers.add(new Student(ALICE_ID, "alice", "P@ss123", FIXED_DATE, "alice@corp.com",
                "U00000001", "Computer Science", new ArrayList<>(), new ArrayList<>(), 0));
        controlledUsers.add(new Admin(BOB_ID, "bob", "Secure9", FIXED_DATE, "bob@corp.com", new ArrayList<>()));
        userList = createIsolatedUserList(controlledUsers);
    }

    private UserList createIsolatedUserList(ArrayList<User> users) throws Exception {
        Constructor<UserList> constructor = UserList.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        UserList isolated = constructor.newInstance();

        Field usersField = UserList.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(isolated, users);

        Field singletonField = UserList.class.getDeclaredField("userlist");
        singletonField.setAccessible(true);
        singletonField.set(null, isolated);
        return isolated;
    }

    @Test
    public void searchUserReturnsMatchingUserWhenUsernameExists() {
        User found = userList.searchUser("alice");
        assertNotNull(found);
        assertEquals("alice", found.getUsername());
    }

    @Test
    public void searchUserReturnsNullWhenUsernameMissing() {
        assertNull(userList.searchUser("charlie"));
    }

    @Test
    public void searchUserReturnsNullWhenUsernameIsNull() {
        assertNull(userList.searchUser(null));
    }

    @Test
    public void getUserReturnsMatchingUserWhenUuidExists() {
        User found = userList.getUser(ALICE_ID);
        assertNotNull(found);
        assertEquals("alice", found.getUsername());
    }

    @Test
    public void getUserReturnsNullWhenUuidMissing() {
        assertNull(userList.getUser(MISSING_ID));
    }

    @Test
    public void getUserReturnsNullWhenUuidIsNull() {
        assertNull(userList.getUser(null));
    }

    @Test
    public void checkForUserReturnsTrueWhenUsernameAndPasswordMatch() {
        assertTrue(userList.checkForUser("alice", "P@ss123"));
    }

    @Test
    public void checkForUserReturnsFalseWhenPasswordIsWrong() {
        assertFalse(userList.checkForUser("alice", "wrong"));
    }

    @Test
    public void checkForUserReturnsFalseWhenUsernameMissing() {
        assertFalse(userList.checkForUser("charlie", "P@ss123"));
    }

    @Test
    public void loginReturnsUserWhenCredentialsValid() {
        User loggedIn = userList.login("alice", "P@ss123");
        assertNotNull(loggedIn);
        assertEquals("alice", loggedIn.getUsername());
    }

    @Test
    public void loginReturnsNullWhenCredentialsInvalid() {
        assertNull(userList.login("alice", "badpass"));
    }

    @Test
    public void isAdminReturnsTrueForAdminRoleUser() {
        User admin = new User(BOB_ID, "bob", "Secure9", FIXED_DATE, "bob@corp.com", "Admin");
        assertTrue(userList.isAdmin(admin));
    }

    @Test
    public void isAdminReturnsFalseForNonAdminUser() {
        User student = new User(ALICE_ID, "alice", "P@ss123", FIXED_DATE, "alice@corp.com", "Student");
        assertFalse(userList.isAdmin(student));
    }

    @Test
    public void isAdminThrowsNullPointerExceptionWhenUserIsNull() {
        assertThrows(NullPointerException.class, () -> userList.isAdmin(null));
    }

    @Test
    public void getUsersReturnsUnderlyingListReference() {
        assertSame(controlledUsers, userList.getUsers());
    }

    @Test
    public void saveReturnsTrueWhenUsersListIsValid() {
        assertTrue(userList.save());
    }

    @Test
    public void saveReturnsFalseWhenUsersListIsNull() throws Exception {
        Field usersField = UserList.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(userList, null);
        assertFalse(userList.save());
    }
}
