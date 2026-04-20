package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.corporate.App;
import com.model.Admin;
import com.model.InterviewApplication;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private Text text_rau_u_one;

    @FXML
    private Text text_rau_u_two;

    @FXML
    private Text text_rau_u_three;

    @FXML
    private Text text_rau_um_one;

    @FXML
    private Text text_rau_um_two;

    @FXML
    private Text text_rau_um_three;

    private Admin currentUser;
    private InterviewApplication app;


    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/settings.fxml"));
        Parent root = loader.load();
        SettingsController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    @FXML
    private void goToCreateQuestion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/createQuestion.fxml"));
        Parent root = loader.load();
        CreateQuestionController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    @FXML
    private void goToManageUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/manageUsers.fxml"));
            Parent root = loader.load();
            ManageUsersController controller = loader.getController();
            controller.setUser(currentUser);
            controller.setInterviewApplication(app);
            App.setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void setUser(User user) {
        this.currentUser = (Admin) user;
        btn_manage_users.setOnAction(this::goToManageUsers);
        displayWelcome(currentUser.getUsername());

        // setting date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM, d, yyyy", Locale.ENGLISH);
        String formattedDate = LocalDate.now().format(formatter);
        setDate(formattedDate);

        // setting account summary
        int num_q = currentUser.getQuestionsMade().size() - 1;
        setNumQ(num_q);
        int num_users = currentUser.getUsers().size();
        setNumUsers(num_users);

        // recently added users
        text_rau_u_one.setText(currentUser.getUsers().get(num_users - 1).getUsername());
        text_rau_u_two.setText(currentUser.getUsers().get(num_users - 2).getUsername());
        text_rau_u_three.setText(currentUser.getUsers().get(num_users - 3).getUsername());
        text_rau_um_one.setText(currentUser.getUsers().get(num_users - 1).getEmail());
        text_rau_um_two.setText(currentUser.getUsers().get(num_users - 2).getEmail());
        text_rau_um_three.setText(currentUser.getUsers().get(num_users - 3).getEmail());
    }

    @FXML
    public void setDate(String date) {
        text_dash_date.setText(date);
    }

    @FXML
    public void setNumQ(int num_q) {
        text_questions_created.setText(Integer.toString(num_q));
    }

    @FXML
    public void setNumUsers(int num_users) {
        text_total_users.setText(Integer.toString(num_users));
    }

}
