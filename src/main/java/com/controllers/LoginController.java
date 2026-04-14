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
        InterviewApplication app = new InterviewApplication();
        if(app.login(username, password) == null) {
            lbl_error.setText("Invalid username or password");
            lbl_error.setVisible(true);
            return;
        }
        User currentUser = app.login(username, password);
        String role = currentUser.getRole();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/dash.fxml"));
        Parent root = loader.load();
        if(role.equals("Admin")) {
            App.setRoot("dashA");
        } else if(role.equals("Editor")) {
            App.setRoot("dashE");
        } else if(role.equals("Student")) {
            App.setRoot("dash");
        }
        DashController controller = loader.getController();
        controller.setUser(currentUser);
    }

    @FXML
    void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create");
    }
}