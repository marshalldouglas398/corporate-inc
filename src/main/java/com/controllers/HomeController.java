package com.controllers;

import java.io.IOException;

import com.corporate.App;

import javafx.fxml.FXML;

public class HomeController {

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("login");
    }
}
