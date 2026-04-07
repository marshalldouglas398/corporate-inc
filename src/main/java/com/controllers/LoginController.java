package com.controllers;

import java.io.IOException;

import com.corporate.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private Button btn_login;

    @FXML
    private void back() throws IOException {
        App.setRoot("home");
    }

    @FXML
    void login(ActionEvent event) {
        String username = txt_username.getText();
        String password = txt_password.getText();
        System.out.println("Your name is " + username); 
        System.out.println("Your password is " + password);
    }
}