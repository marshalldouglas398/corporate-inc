package com.controllers;

import java.io.IOException;
import java.lang.classfile.Label;

import com.corporate.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    private void back() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void goToCreate() throws IOException {
        App.setRoot("create");
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String username = txt_username.getText();
        String password = txt_password.getText();
        System.out.println("Your name is " + username); 
        System.out.println("Your password is " + password);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/dash.fxml"));
        Parent root = loader.load();

        App.setRoot("dash");
        DashController controller = loader.getController();
        controller.displayWelcome(username);
    }
}
