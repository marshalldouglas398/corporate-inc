package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.corporate.App;
import com.model.Admin;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.User;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 * Controller for the manage users screen
 */
public class ManageUsersController {
    @FXML
    private TextField textfield_search_bottom;

    @FXML
    private VBox manage_users_list;

    private User currentUser;
    private InterviewApplication app;

    /**
     * Stores the shared application object for navigation and saving
     */
    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    /**
     * Stores the logged-in user and refreshes the card list when the manage screen is active
     */
    public void setUser(User user) {
        this.currentUser = user;
        if (this.app == null) {
            this.app = new InterviewApplication();
        }
        if (manage_users_list != null) {
            refreshUsers();
        }
    }

    /**
     * Logs out and returns to the login screen.
     */
    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    /**
     * Routes back to the correct dashboard for the current user role.
     */
    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        FXMLLoader loader;
        if (currentUser instanceof Admin) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        } else if (currentUser instanceof Editor) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dash.fxml"));
        }

        Parent root = loader.load();
        Object controller = loader.getController();
        if (controller instanceof DashAController) {
            DashAController dashA = (DashAController) controller;
            dashA.setUser(currentUser);
            dashA.setInterviewApplication(app);
        } else if (controller instanceof DashEController) {
            DashEController dashE = (DashEController) controller;
            dashE.setUser(currentUser);
            dashE.setInterviewApplication(app);
        } else if (controller instanceof DashController) {
            DashController dash = (DashController) controller;
            dash.setUser(currentUser);
            dash.setInterviewApplication(app);
        }
        App.setRoot(root);
    }

    /**
     * Opens the shared question search screen
     */
    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setUser(currentUser);
        App.setRoot(root);
    }

    /**
     * Opens the settings screen for the current user
     */
    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/settings.fxml"));
        Parent root = loader.load();
        SettingsController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    /**
     * Re-renders the card list using the current search field text
     */
    @FXML
    private void searchUsers(ActionEvent event) {
        refreshUsers();
    }

    /**
     * Clears the visible list and rebuilds it from the JSON-backed user list
     */
    @FXML
    private void refreshUsers() {
        manage_users_list.getChildren().clear();
        for (User user : getFilteredUsers()) {
            manage_users_list.getChildren().add(buildUserCard(user));
        }
    }

    /**
     * Opens the edit user screen for the clicked card
     */
    @FXML
    private void editUser(ActionEvent event) {
        try {
            User user = getUserFromEvent(event);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/editUsers.fxml"));
            Parent root = loader.load();
            EditUsersController controller = loader.getController();
            controller.setInterviewApplication(app);
            controller.setUser(currentUser);
            controller.setSelectedUser(user);
            App.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the clicked user and rebuilds the visible list
     */
    @FXML
    private void deleteUser(ActionEvent event) {
        User user = getUserFromEvent(event);
        if (currentUser instanceof Admin) {
            ((Admin) currentUser).deleteUser(user.getUsername());
        } else {
            replaceUser(user, null);
        }
        refreshUsers();
    }

    /**
     * Filters users by the current search field contents
     */
    private List<User> getFilteredUsers() {
        ArrayList<User> users = currentUser instanceof Admin
            ? new ArrayList<>(((Admin) currentUser).getUsers())
            : new ArrayList<>(UserList.getInstance().getUsers());

        String keyword = textfield_search_bottom.getText().trim().toLowerCase(Locale.ROOT);
        if (keyword.isBlank()) {
            return users;
        }

        ArrayList<User> filtered = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().toLowerCase(Locale.ROOT).contains(keyword)) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    /**
     * Builds one visible card for a user in the manage users list
     */
    private HBox buildUserCard(User user) {
        HBox card = new HBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMinHeight(86);
        card.setPrefHeight(86);
        card.setMaxWidth(Double.MAX_VALUE);
        card.getStyleClass().add("manage-user-card");

        StackPane avatar = new StackPane();
        avatar.setMinSize(52, 52);
        avatar.setPrefSize(52, 52);
        avatar.setMaxSize(52, 52);
        avatar.getStyleClass().add("manage-user-avatar");
        Label avatarLabel = new Label(user.getUsername().substring(0, 1).toUpperCase(Locale.ROOT));
        avatarLabel.getStyleClass().add("manage-user-avatar-label");
        avatar.getChildren().add(avatarLabel);

        VBox info = new VBox(4);
        info.getStyleClass().add("manage-user-info");
        Label name = new Label(user.getUsername());
        name.getStyleClass().add("manage-user-name");
        Label meta = new Label(user.getRole() + " | " + user.getEmail());
        meta.getStyleClass().add("manage-user-meta");
        info.getChildren().addAll(name, meta);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actions = new HBox(12);
        actions.setAlignment(Pos.CENTER_RIGHT);
        Button edit = buildActionButton("Edit");
        edit.setUserData(user.getID().toString());
        edit.setOnAction(this::editUser);
        Button delete = buildActionButton("Delete");
        delete.setUserData(user.getID().toString());
        delete.setOnAction(this::deleteUser);
        actions.getChildren().addAll(edit, delete);

        card.getChildren().addAll(avatar, info, spacer, actions);
        return card;
    }

    /**
     * Creates a standard card action button
     */
    private Button buildActionButton(String text) {
        Button button = new Button(text);
        button.setPrefHeight(34);
        button.setPrefWidth(92);
        button.getStyleClass().add("manage-user-action-button");
        return button;
    }

    /**
     * Reads the target user id from the clicked button.
     */
    private User getUserFromEvent(ActionEvent event) {
        Button button = (Button) event.getSource();
        String id = (String) button.getUserData();
        return UserList.getInstance().getUser(UUID.fromString(id));
    }

    /**
     * Replaces or removes a user in the singleton user list and saves the file
     */
    private void replaceUser(User oldUser, User newUser) {
        UserList userList = UserList.getInstance();
        User storedUser = userList.getUser(oldUser.getID());
        userList.getUsers().remove(storedUser);
        if (newUser != null) {
            userList.getUsers().add(newUser);
        }
        userList.save();
    }

}
