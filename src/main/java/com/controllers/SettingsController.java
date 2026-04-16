package com.controllers;

import java.io.IOException;

import com.corporate.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SettingsController {
    @FXML
    private Button h_btn;
    
    @FXML
    private Button search_btn;

    @FXML
    private Button btn_logout;

    @FXML
    private Button stg_btn;
    
    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        App.setRoot("dash");
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        App.setRoot("search");
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }
}
