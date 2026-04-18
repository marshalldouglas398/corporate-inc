package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.InterviewApplication;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    public TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private Button btn_login;

    @FXML
    private Label lbl_error;

    @FXML
    private Button createButton;

    private InterviewApplication app;
    @FXML
    private void back() throws IOException {
        App.setRoot("home");
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String username = txt_username.getText();
        String password = txt_password.getText();
        System.out.println("Your name is " + username); 
        System.out.println("Your password is " + password);
        app = new InterviewApplication();
        if(app.login(username, password) == null) {
            lbl_error.setText("Invalid username or password");
            lbl_error.setVisible(true);
            return;
        }
        User currentUser = app.login(username, password);
        String role = currentUser.getRole();
        FXMLLoader loader = null;
        if(role.equals("Admin")) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        } else if(role.equals("Editor")) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
        } else if(role.equals("Student")) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dash.fxml"));
        }
        Parent root = loader.load();
        if(role.equals("Student")) {
            DashController dash = loader.getController();
            dash.setUser(currentUser);
            dash.setInterviewApplication(app);
        } 
        if(role.equals("Admin")) {
            DashAController dashA = loader.getController();
            dashA.setUser(currentUser);
        }
        if(role.equals("Editor")) {
            DashEController dashE = loader.getController();
           dashE.setUser(currentUser);
           dashE.setInterviewApplication(app);
        }
        App.setRoot(root);
    }

    @FXML
    void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create");
    }

    
}