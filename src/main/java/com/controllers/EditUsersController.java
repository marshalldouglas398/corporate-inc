package com.controllers;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.corporate.App;
import com.model.Admin;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Student;
import com.model.User;
import com.model.UserList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditUsersController {

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

    private User currentUser;
    private Student student;
    private Editor editor;
    private Admin admin;
    private InterviewApplication app;
    private User selectedUser;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.student = null;
        this.editor = null;
        this.admin = null;

        if(user instanceof Student) {
            this.student = (Student) user;
        } else if(user instanceof Editor) {
            this.editor = (Editor) user;
        } else if(user instanceof Admin) {
            this.admin = (Admin) user;
        }
    }

    public void setSelectedUser(User user) {
        this.selectedUser = user;
        populateUser();
    }

    @FXML
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    private void goToDash(ActionEvent event) throws IOException {
        FXMLLoader loader = null;
        if(admin != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashA.fxml"));
        } else if(editor != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dashE.fxml"));
        } else if(student != null) {
           loader = new FXMLLoader(getClass().getResource("/com/corporate/dash.fxml"));
        }
        Parent root = loader.load();
        if(student != null) {
            DashController dash = loader.getController();
            dash.setUser(currentUser);
            dash.setInterviewApplication(app);
        }
        if(admin != null) {
            DashAController dashA = loader.getController();
            dashA.setUser(currentUser);
            dashA.setInterviewApplication(app);
        }
        if(editor != null) {
            DashEController dashE = loader.getController();
            dashE.setUser(currentUser);
            dashE.setInterviewApplication(app);
        }
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
    private void saveEditedUser(ActionEvent event) throws IOException {
        String username = getTrimmedText(txt_username);
        String password = getText(txt_username2);
        String email = getTrimmedText(txt_email);
        String uscID = getTrimmedText(txt_uscid);
        String major = getTrimmedText(txt_major);
        LocalDate birthDateValue = getBirthDate();

        if (username.isBlank() || password.isBlank() || email.isBlank() || birthDateValue == null) {
            setStatus("Please fill out every field.");
            return;
        }

        if (selectedUser instanceof Student && (uscID.isBlank() || major.isBlank())) {
            setStatus("Please fill out every field.");
            return;
        }

        Date dateOfBirth = Date.from(
            birthDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant()
        );

        User updatedUser = buildEditedUser(username, password, dateOfBirth, email, uscID, major);
        replaceUser(updatedUser);

        if (currentUser.getID().equals(selectedUser.getID())) {
            setUser(updatedUser);
        }

        goToManageUsers();
    }

    private void populateUser() {
        txt_username.setText(selectedUser.getUsername());
        txt_username2.setText(selectedUser.getPassword());
        txt_email.setText(selectedUser.getEmail());

        LocalDate birthDate = Instant.parse(selectedUser.getBirthDate())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        txt_username1.setText(String.format("%02d", birthDate.getMonthValue()));
        txt_username3.setText(String.format("%02d", birthDate.getDayOfMonth()));
        txt_username4.setText(Integer.toString(birthDate.getYear()));

        if(selectedUser instanceof Student) {
            Student editStudent = (Student) selectedUser;
            txt_uscid.setDisable(false);
            txt_major.setDisable(false);
            txt_uscid.setText(editStudent.getUSCID());
            txt_major.setText(editStudent.getMajor());
        } else {
            txt_uscid.setDisable(true);
            txt_major.setDisable(true);
            txt_uscid.setText("");
            txt_major.setText("");
        }
    }

    private User buildEditedUser(String username, String password, Date dateOfBirth, String email, String uscID, String major) {
        if(selectedUser instanceof Student) {
            Student editStudent = (Student) selectedUser;
            return new Student(
                editStudent.getID(),
                username,
                password,
                dateOfBirth,
                email,
                uscID,
                major,
                new ArrayList<>(editStudent.getQuestionsAnswered()),
                new ArrayList<>(editStudent.getCoursesTaken()),
                editStudent.getStreak(),
                editStudent.hasRequestedEditor()
            );
        } else if(selectedUser instanceof Editor) {
            Editor editEditor = (Editor) selectedUser;
            return new Editor(
                editEditor.getID(),
                username,
                password,
                dateOfBirth,
                email,
                editEditor.getRole(),
                new ArrayList<>(editEditor.getQuestionsMade())
            );
        } else {
            Admin editAdmin = (Admin) selectedUser;
            return new Admin(
                editAdmin.getID(),
                username,
                password,
                dateOfBirth,
                email,
                new ArrayList<>(editAdmin.getQuestionsMade())
            );
        }
    }

    private void replaceUser(User updatedUser) {
        if(admin != null) {
            admin.editUser(selectedUser.getUsername(), updatedUser);
        } else {
            UserList userList = UserList.getInstance();
            User oldUser = userList.searchUser(selectedUser.getUsername());
            userList.getUsers().remove(oldUser);
            userList.getUsers().add(updatedUser);
            userList.save();
        }
    }

    private void goToManageUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/corporate/manageUsers.fxml"));
        Parent root = loader.load();
        ManageUsersController controller = loader.getController();
        controller.setInterviewApplication(app);
        controller.setUser(currentUser);
        App.setRoot(root);
    }

    private void setStatus(String message) {
        lbl_status.setText(message);
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
}
