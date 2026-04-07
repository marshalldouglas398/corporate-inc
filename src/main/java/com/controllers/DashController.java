package com.controllers;

import java.io.IOException;

import com.corporate.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DashController {
    @FXML
    private Button btn_logout;
    
    @FXML
    private Text welcomeMessage;

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");

    }
}
