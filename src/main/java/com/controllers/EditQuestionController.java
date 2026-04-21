package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class EditQuestionController {
    @FXML
    private Button btn_home;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_settings;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_back;

    @FXML
    private Button addSectionButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    private User currentUser;
    private Admin admin;
    private Editor editor;
    private InterviewApplication app = new InterviewApplication();

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.admin = null;
        this.editor = null;
        if (user instanceof Admin) {
            this.admin = (Admin) user;
        } else if (user instanceof Editor) {
            this.editor = (Editor) user;
        }
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        goBackToRoleDashboard();
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        goBackToRoleDashboard();
    }

    @FXML
    private void cancelEdit(ActionEvent event) throws IOException {
        goBackToRoleDashboard();
    }

    @FXML
    private void saveChanges(ActionEvent event) throws IOException {
        goBackToRoleDashboard();
    }

    @FXML
    private void addSection(ActionEvent event) {
        // Layout-only page for now. Dynamic section editing is intentionally deferred.
    }

    @FXML
    private void logout() throws IOException {
        if (currentUser != null) {
            app.logout(currentUser);
        }
        App.setRoot("login");
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/settings.fxml"));
        Parent root = loader.load();
        SettingsController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    private void goBackToRoleDashboard() throws IOException {
        FXMLLoader loader;
        if (admin != null) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
            Parent root = loader.load();
            DashAController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
            App.setRoot(root);
        } else if (editor != null) {
            loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
            Parent root = loader.load();
            DashEController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
            App.setRoot(root);
        } else {
            App.setRoot("login");
        }
    }
}
