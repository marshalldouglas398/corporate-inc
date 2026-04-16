package com.controllers;

import java.io.IOException;

import com.corporate.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DashAController {
    @FXML
    private Button btn_logout;
    
    @FXML
    private Text welcomeMessage;

    @FXML
    private Button btn_questions;
    
    @FXML
    private Button btn_home;

    @FXML
    private Button btn_settings;

    @FXML
    private Button btn_search;

    @FXML
    private Text text_dash_date;

    @FXML
    private Button btn_upgrade_requests;

    @FXML
    private Button btn_flagged_questions;

    @FXML
    private Button btn_add_questions;

    @FXML
    private Button btn_manage_users;

    @FXML
    private Text text_questions_created;

    @FXML
    private Text text_total_users;

    @FXML
    private Text text_ra_title_one;

    @FXML
    private Text text_ra_date_one;

    @FXML
    private Text text_ra_title_two;

    @FXML
    private Text text_ra_date_two;

    @FXML
    private Text text_ra_title_three;

    @FXML
    private Text text_ra_date_three;

    //private Admin currentUser;

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");
    }

    private void goToSearch(ActionEvent event) throws IOException {
        App.setRoot("search");
    }

    private void goToSettings(ActionEvent event) throws IOException {
        App.setRoot("settings");
    }

    /*
    public void setUser(Admin admin) {
        this.currentUser = admin;
        displayWelcome(admin.getUsername());
    }*/
}
