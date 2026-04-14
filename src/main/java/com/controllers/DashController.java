package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class DashController {
    // Header Buttons
    @FXML
    private Button q_btn;

    @FXML
    private Button h_btn;

    @FXML
    private Button btn_logout;

    @FXML
    private Button stg_btn;

    // Welcome Section
    @FXML
    private Text welcomeMessage;

    @FXML
    private Text date_text;

    // Challenge Section
    @FXML
    private Text chll_title;

    @FXML
    private Text chll_desc;

    @FXML
    private Text chll_type;

    @FXML
    private Text chll_diff;

    @FXML
    private Button cll_btn;

    //Account Summary Section

    @FXML
    private Text num_q_text;

    @FXML
    private Text mjr_text;

    @FXML
    private Text crs_text;

    // Streaks

    @FXML
    private Text stk_num_text;

    // Suggested Problems

    @FXML
    private Text sug_1_title;

    @FXML
    private Text sug_1_type;

    @FXML 
    private Text sug_1_diff;

    @FXML
    private Button sug_1_start_btn;

    @FXML
    private Text sug_2_title;

    @FXML
    private Text sug_2_type;

    @FXML
    private Text sug_2_diff;

    @FXML
    private Button sug_2_start_btn;

     @FXML
    private Text sug_3_title;

    @FXML
    private Text sug_3_type;

    @FXML 
    private Text sug_3_diff;

    @FXML
    private Button sug_3_start_btn;

    // Recent Activity(Starts off invisible)

    @FXML
    private Text rct_1_title;

    @FXML
    private Text rct_1_type;

    @FXML
    private Text rct_1_diff;

    @FXML
    private Text rct_date_1;

    @FXML
    private Text rct_2_title;

    @FXML
    private Text rct_2_type;

    @FXML
    private Text rct_2_diff;

    @FXML
    private Text rct_date_2;

    private User currentUser;


    public void setUser(User user) {
        this.currentUser = user;
        displayWelcome(user.getUsername());
    }
    
    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");
    }
}
