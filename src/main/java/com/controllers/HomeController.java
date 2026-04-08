package com.controllers;

import java.io.IOException;

import com.corporate.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController {
    @FXML
    private Button primaryButton;
    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("login");
    }
}
