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
import javafx.scene.control.Label;

public class EditorQController {
    @FXML
    private Button btn_home;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_settings;

    @FXML
    private Label welcomeMessage;

    @FXML
    private Label text_dash_date;
    

    private User currentUser;
    private Editor editor;
    private Admin admin;
    private InterviewApplication app;


    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");
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

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/settings.fxml"));
        Parent root = loader.load();
        SettingsController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    public void setUser(User user) {
        this.currentUser = user;
        if(currentUser.getRole().equals("Admin")) {
            this.admin = (Admin) user;
        } else if(currentUser.getRole().equals("Editor")) {
            this.editor = (Editor) user;
        } else {
            System.out.println("Error: User role is not Admin or Editor");
        }         
    }

    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        FXMLLoader loader = null;
        if(admin != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        } else if(editor != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
        } else {
            System.out.println("Error: No valid user role found for dashboard navigation");
        }
        Parent root = loader.load();
        if(admin != null) {
            DashAController dashA = loader.getController();
            dashA.setUser(currentUser);
            dashA.setInterviewApplication(app);
        } else if(editor != null) {
            DashEController dashE = loader.getController();
           dashE.setUser(currentUser);
           dashE.setInterviewApplication(app);
        } else {
            System.out.println("Error: No valid user role found for dashboard controller setup");
        }
        App.setRoot(root);
    }

    @FXML
    public void setDate(String date) {
        text_dash_date.setText(date);
    }
}
