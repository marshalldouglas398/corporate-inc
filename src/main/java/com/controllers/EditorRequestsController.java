package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import com.corporate.App;
import com.model.Admin;
import com.model.InterviewApplication;
import com.model.Student;
import com.model.User;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class EditorRequestsController {
    @FXML
    private TextField textfield_editor_requests_search;

    @FXML
    private VBox editor_requests_list;

    private Admin currentUser;
    private InterviewApplication app;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    public void setUser(User user) {
        this.currentUser = (Admin) user;
        if(this.app == null) {
            this.app = new InterviewApplication();
        }
        if(editor_requests_list != null) {
            refreshRequests();
        }
    }

    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        Parent root = loader.load();
        DashAController controller = loader.getController();
        controller.setUser(currentUser);
        controller.setInterviewApplication(app);
        App.setRoot(root);
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/search.fxml"));
        Parent root = loader.load();
        SearchController controller = loader.getController();
        controller.setUser(currentUser);
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
    private void searchRequests(ActionEvent event) {
        refreshRequests();
    }

    @FXML
    private void refreshRequests() {
        editor_requests_list.getChildren().clear();
        for (Student student : getFilteredRequests()) {
            editor_requests_list.getChildren().add(buildRequestCard(student));
        }
    }

    @FXML
    private void rejectRequest(ActionEvent event) {
        Student student = getStudentFromEvent(event);
        student.setEditorRequest(false);
        app.saveUsers();
        refreshRequests();
    }

    @FXML
    private void acceptRequest(ActionEvent event) {
        Student student = getStudentFromEvent(event);
        student.setEditorRequest(false);
        app.toEditor(student);
        refreshRequests();
    }

    private ArrayList<Student> getFilteredRequests() {
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<User> users = UserList.getInstance().getUsers();

        for(User user : users) {
            if(user instanceof Student) {
                Student student = (Student) user;
                if(student.hasRequestedEditor()) {
                    students.add(student);
                }
            }
        }

        String keyword = textfield_editor_requests_search.getText().trim().toLowerCase(Locale.ROOT);
        if(keyword.isBlank()) {
            return students;
        }

        ArrayList<Student> filteredStudents = new ArrayList<>();
        for(Student student : students) {
            if(student.getUsername().toLowerCase(Locale.ROOT).contains(keyword)) {
                filteredStudents.add(student);
            }
        }
        return filteredStudents;
    }

    private HBox buildRequestCard(Student student) {
        HBox card = new HBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMinHeight(86);
        card.setPrefHeight(86);
        card.setMaxWidth(Double.MAX_VALUE);
        card.getStyleClass().add("editor-request-card");

        StackPane avatar = new StackPane();
        avatar.setMinSize(52, 52);
        avatar.setPrefSize(52, 52);
        avatar.setMaxSize(52, 52);
        avatar.getStyleClass().add("editor-request-avatar");

        Label avatarLabel = new Label(student.getUsername().substring(0, 1).toUpperCase(Locale.ROOT));
        avatarLabel.getStyleClass().add("editor-request-avatar-label");
        avatar.getChildren().add(avatarLabel);

        VBox info = new VBox(4);
        info.getStyleClass().add("editor-request-info");

        Label name = new Label(student.getUsername());
        name.getStyleClass().add("editor-request-name");

        Label meta = new Label(student.getRole() + " | " + student.getEmail());
        meta.getStyleClass().add("editor-request-meta");
        info.getChildren().addAll(name, meta);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actions = new HBox(12);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Button reject = buildActionButton("Reject");
        reject.setUserData(student.getID().toString());
        reject.setOnAction(this::rejectRequest);

        Button accept = buildActionButton("Accept");
        accept.setUserData(student.getID().toString());
        accept.setOnAction(this::acceptRequest);

        actions.getChildren().addAll(reject, accept);
        card.getChildren().addAll(avatar, info, spacer, actions);
        return card;
    }

    private Button buildActionButton(String text) {
        Button button = new Button(text);
        button.setPrefHeight(34);
        button.setPrefWidth(92);
        button.getStyleClass().add("editor-request-action-button");
        return button;
    }

    private Student getStudentFromEvent(ActionEvent event) {
        Button button = (Button) event.getSource();
        String id = (String) button.getUserData();
        return (Student) UserList.getInstance().getUser(UUID.fromString(id));
    }
}
