package com.controllers;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.corporate.App;
import com.model.InterviewApplication;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateController {

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_username1;

    @FXML
    private TextField txt_username2;

    @FXML
    private TextField txt_username3;

    @FXML
    private TextField txt_username4;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_uscid;

    @FXML
    private TextField txt_major;

    @FXML
    private Label lbl_status;

    @FXML
    private Button btn_create;

    private final InterviewApplication interviewApp = new InterviewApplication();

    @FXML
    private void initialize() {
        if (lbl_status != null) {
            lbl_status.setText("");
        }
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("login");
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        String username = getTrimmedText(txt_username);
        String password = getText(txt_username2);
        String email = getTrimmedText(txt_email);
        String uscID = getTrimmedText(txt_uscid);
        String major = getTrimmedText(txt_major);
        LocalDate birthDateValue = getBirthDate();

        if (username.isBlank() || password.isBlank() || email.isBlank()
                || uscID.isBlank() || major.isBlank() || birthDateValue == null) {
            setStatus("Please fill out every field.");
            return;
        }

        Date dateOfBirth = Date.from(
            birthDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant()
        );

        User user = interviewApp.createAccount(
            username,
            password,
            dateOfBirth,
            email,
            uscID,
            major
        );

        if (user == null) {
            setStatus("Could not create account. Username may already exist.");
            return;
        }

        clearFields();
        App.setRoot("login");
    }

    private void clearFields() {
        clearField(txt_username);
        clearField(txt_username1);
        clearField(txt_username2);
        clearField(txt_username3);
        clearField(txt_username4);
        clearField(txt_email);
        clearField(txt_uscid);
        clearField(txt_major);
        setStatus("");
    }

    private void setStatus(String message) {
        if (lbl_status != null) {
            lbl_status.setText(message);
        }
    }

    private LocalDate getBirthDate() {
        String monthText = getTrimmedText(txt_username1);
        String dayText = getTrimmedText(txt_username3);
        String yearText = getTrimmedText(txt_username4);

        if (monthText.isBlank() || dayText.isBlank() || yearText.isBlank()) {
            return null;
        }

        try {
            int month = Integer.parseInt(monthText);
            int day = Integer.parseInt(dayText);
            int year = Integer.parseInt(yearText);

            if (year < 100) {
                int currentYear = LocalDate.now().getYear() % 100;
                year += (year <= currentYear) ? 2000 : 1900;
            }

            return LocalDate.of(year, month, day);
        } catch (NumberFormatException | DateTimeException e) {
            return null;
        }
    }

    private String getText(TextField field) {
        if (field == null || field.getText() == null) {
            return "";
        }
        return field.getText();
    }

    private String getTrimmedText(TextField field) {
        return getText(field).trim();
    }

    private void clearField(TextField field) {
        if (field != null) {
            field.clear();
        }
    }
}
