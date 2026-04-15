package com.controllers;

import java.io.IOException;

import com.corporate.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchController {
    @FXML
    private Button btn_questions;

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_settings;

    @FXML
    private Button btn_logout;

    @FXML
    private TextField textfield_search_field;
 
    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        //App.setRoot("questions");
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        App.setRoot("dash");
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        //App.setRoot("settings");
    }
}
