package com.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import com.corporate.App;
import com.model.Course;
import com.model.Question;
import com.model.QuestionList;
import com.model.Student;
import com.model.User;

import javafx.event.ActionEvent;
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

    private Student currentUser;


    public void setUser(User user) {
        this.currentUser = (Student) user;
        displayWelcome(user.getUsername());
        // setting date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM, d, yyyy", Locale.ENGLISH);
        String formattedDate = LocalDate.now().format(formatter);
        setDate(formattedDate);
        // setting account summary
        int num_q = currentUser.getQuestionsAnswered().size();
        setNumQ(num_q);
        ArrayList<Course> courses = new ArrayList<>();
        courses.addAll(currentUser.getCoursesTaken());
        setCourses(courses);
        setMajor(currentUser.getMajor());
        setStreak(currentUser.getStreak());

        //recent activity
        int recent = currentUser.getQuestionsAnswered().size() - 1;
        if(recent == 1) {
            QuestionList ql = QuestionList.getInstance();
            Question q = ql.getQuestion(currentUser.getQuestionsAnswered().get(recent));
            rct_1_title.setText(q.getTitle());
            String type = q.getType().toString();
            String noenumtype = type.substring(type.indexOf('.') + 1);
            rct_1_type.setText(noenumtype);
            String diff = q.getDifficulty().toString();
            String noenumdiff = diff.substring(diff.indexOf('.') + 1);
            rct_1_diff.setText(noenumdiff);
            rct_date_1.setText("Date Completed"); // need to implement
            rct_2_title.setVisible(false);
            rct_2_type.setVisible(false);
            rct_2_diff.setVisible(false);
            rct_date_2.setVisible(false);
        } else if(recent > 1) {
            QuestionList ql = QuestionList.getInstance();
            Question q = ql.getQuestion(currentUser.getQuestionsAnswered().get(recent));
            Question q2 = ql.getQuestion(currentUser.getQuestionsAnswered().get(recent - 1));
            rct_1_title.setText(q.getTitle());
            String type = q.getType().toString();
            String noenumtype = type.substring(type.indexOf('.') + 1);
            rct_1_type.setText(noenumtype);
            String diff = q.getDifficulty().toString();
            String noenumdiff = diff.substring(diff.indexOf('.') + 1);
            rct_1_diff.setText(noenumdiff);
            rct_date_1.setText("Date Completed"); // need to implement
            rct_2_title.setText(q2.getTitle());
            String type2 = q2.getType().toString();
            String noenumtype2 = type2.substring(type2.indexOf('.') + 1);
            rct_2_type.setText(noenumtype2);
            String diff2 = q2.getDifficulty().toString();
            String noenumdiff2 = diff2.substring(diff2.indexOf('.') + 1);
            rct_2_diff.setText(noenumdiff2);
            rct_date_2.setText("Date Completed"); // need to implement
        } else if(recent == 0) {
            rct_1_title.setVisible(false);
            rct_1_type.setVisible(false);
            rct_1_diff.setVisible(false);
            rct_date_1.setVisible(false); 
            rct_2_title.setVisible(false);
            rct_2_type.setVisible(false);
            rct_2_diff.setVisible(false);
            rct_date_2.setVisible(false);
        }
        // setting suggested
        if(recent == 0 || recent < 3) { // maybe change numbers?

        } else if(recent >= 3 || recent < 10) {

        } else if(recent >= 10) {
            
        }
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        App.setRoot("dash");
    }

    @FXML
    private void goToSettings(ActionEvent event) throws IOException {
        App.setRoot("settings");
    }

    @FXML
    private void goToSearch(ActionEvent event) throws IOException {
        App.setRoot("search");
    }

    @FXML
    public void displayWelcome(String username) {
        welcomeMessage.setText("Welcome, " + username + "!");
    }

    @FXML
    public void setDate(String date) {
        date_text.setText(date);
    }

    @FXML
    public void setNumQ(int num) {
        num_q_text.setText(Integer.toString(num));
    }

    @FXML
    public void setCourses(ArrayList<Course> courses) {
         String courseStr = "";

    for (Object o : courses) {
        Course c;

        if (o instanceof Course) {
            c = (Course) o;
        } else {
            String raw = o.toString();

            // Remove enum prefix if present
            if (raw.contains(".")) {
                raw = raw.substring(raw.lastIndexOf('.') + 1);
            }

            c = Course.valueOf(raw);
        }

        String noenum = c.toString().substring(c.toString().indexOf('.') + 1);
        courseStr += noenum + "\n";
    }

    crs_text.setText(courseStr);

       // String courseStr = "";
        //for(Course c : courses) { // error here
        //    String course = c.toString();
        //    String noenum = course.substring(course.indexOf('.') + 1);
        //    courseStr += noenum + "\n";
       // }
       // crs_text.setText(courseStr);
    }

    @FXML
    public void setMajor(String major) {
        mjr_text.setText(major);
    }

    @FXML
    public void setStreak(int streak) {
        stk_num_text.setText(Integer.toString(streak));
    }
}
