package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DashEController {
    @FXML
    private Button btn_logout;
    
    @FXML
    private Text welcomeMessage;

    private Editor currentUser;

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");

    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        App.setRoot("search");
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        App.setRoot("settings");
    }

}
