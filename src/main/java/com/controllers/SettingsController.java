package com.controllers;

import java.io.IOException;

import com.corporate.App;
import com.model.Admin;
import com.model.Course;
import com.model.Editor;
import com.model.InterviewApplication;
import com.model.Student;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private Button em_btn;

    @FXML
    private Button pass_btn;

    @FXML
    private Button crs_btn;

    @FXML
    private Button mjr_btn;

    @FXML
    private Button ed_btn;

    @FXML
    private TextField email_txt;

    @FXML
    private TextField pass_txt;

    @FXML
    private TextField crs_txt;

    @FXML
    private TextField mjr_txt;

    @FXML
    private Text act_txt;
    
    private User currentUser;
    private Student student = null;
    private Editor editor = null;
    private Admin admin = null;
    private InterviewApplication app;

    public void setInterviewApplication(InterviewApplication app) {
        this.app = app;
    }
    public void setUser(User user) {
        this.currentUser = user;
        if(currentUser.getRole().equals("Student")) {
            this.student = (Student) user;
        } else if(currentUser.getRole().equals("Editor")) {
            this.editor = (Editor) user;
            crs_btn.setVisible(false);
            mjr_btn.setVisible(false);
            crs_txt.setVisible(false);
            mjr_txt.setVisible(false);
            ed_btn.setVisible(false);
            act_txt.setVisible(false);
        } else if(currentUser.getRole().equals("Admin")) {
            this.admin = (Admin) user;
            crs_btn.setVisible(false);
            mjr_btn.setVisible(false);
            crs_txt.setVisible(false);
            mjr_txt.setVisible(false);
            ed_btn.setVisible(false);
            act_txt.setVisible(false);
        }
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
        } else if(admin != null) {
            DashAController dashA = loader.getController();
            dashA.setUser(currentUser);
            dashA.setInterviewApplication(app);
        } else if(editor != null) {
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
    private void logout() throws IOException {
        app.logout(currentUser);
        App.setRoot("login");
    }

    @FXML
    private void editEmail(ActionEvent event) throws IOException {
        String newEmail = email_txt.getText().trim();
        if (student != null) {
            student.resetEmail(newEmail);
        } else if (editor != null) {
            editor.resetEmail(newEmail);
        } else if (admin != null) {
            admin.resetEmail(newEmail);
        }
        app.saveUsers();
    }

    @FXML
    private void editPassword(ActionEvent event) throws IOException {
        String newPassword = pass_txt.getText().trim();
        if (student != null) {
            student.resetPassword(newPassword);
        } else if (editor != null) {
            editor.resetPassword(newPassword);
        } else if (admin != null) {
            admin.resetPassword(newPassword);
        }
        app.saveUsers();
    }

    @FXML
    private void editCourses(ActionEvent event) throws IOException {
        if(student != null) {
            String newCourse = crs_txt.getText().trim();
            Course c;
            if(newCourse.equals("CSCE145") || newCourse.equals("CSCE146") || newCourse.equals("CSCE240") || newCourse.equals("CSCE247") || newCourse.equals("CSCE350")) {
                c = Course.valueOf(newCourse);
                student.addCourse(c);
            }
        }
        app.saveUsers();
    }

    @FXML
    private void editMajor(ActionEvent event) throws IOException {
        if(student != null) {
            String newMajor = mjr_txt.getText().trim();
            student.setMajor(newMajor);
        }
        app.saveUsers();
    }

    @FXML
    private void requestEditor(ActionEvent event) throws IOException {
        if(student != null) {
            student.setEditorRequest(true);
            app.saveUsers();
        }
    }
}
