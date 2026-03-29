package com.model;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class InterviewApplicationTest {
    /* Ainsley Weaver first half of interview Application test
    +-------------------------------------------------------+------------------------------------------------+
    | Test                                                  | Reasoning                                      |
    +-------------------------------------------------------+------------------------------------------------+
    |testLoginValid                                         | Student can log in with valid credentials      |
    |testLoginInvalidUsername                               | Empty username should not be allowed           |
    |testLoginInvalidPassword	                            | Wrong password should not be accepted          |
    |testLogoutValid	                                    | Student should successfully logout             |
    |testLogoutInvalidUser	                                | Null user should not be allowed to log out     |
    |testCreateAccountValid	                                | Valid user account is created                  |
    |testCreateAccountEmptyUsername	                        | Empty username should not be allowed           |
    |testCreateAccountInvalidUsername	                    | Username with spaces should not be accepted    |
    |testCreateAccountEmptyPassword	                        | Empty password should not be accepted          |
    |testCreateAccountInvalidPassword	                    | Password with "." shouldn't be accepted        |
    |testCreateAccountEmptyDateOfBirth	                    | Empty date of birth shouldn't be accepted      |
    |testCreateAccountInvalidDateOfBirth	                | Invalid date of birth shouldn't be accepted    |
    |testCreateAccountEmptyEmail	                        | Empty email should not be accepted             |
    |testCreateAccountInvalidEmail	                        | Email w/o domain shouldn't be accepted         |
    |testCreateAccountEmptyUSCID	                        | Empty USCID should not be accepted             |
    |testCreateAccountInvalidUSCID	                        | USCID with just letters shouldn't be accepted  |
    |testCreateAccountEmptyMajor	                        | Empty major should not be accepted             |
    |testCreateAccountInvalidMajor	                        | Unknown major shouldn't be accepted            |
    |testCreateAccountDuplicateUsername	                    | Duplicate username shouldn't be accepted       |
    |testCreateAccountDuplicateEmail	                    | Duplicate email shouldn't be accepted          |
    |testCreateAccountDuplicateUSCID	                    | Duplicate USCID shouldn't be accepted          |
    |testDeleteUserValidUser	                            | Admin should be able to delete users           |
    |testDeleteUserInvalidUser	                            | Student shouldn't be able to delete users      |
    |testEditUser	                                        | Admin should be able to edit users             |
    |testEditUserInvalidUser	                            | Student shouldn't be able to edit users        |
    |testFilterQuestionValid	                            | Questionlist should be filted for TECHNICAL    |
    |testFilterQuestionEmptySearch	                        | Questionlist should be filtered by empty search|
    |testFilterQuestionNoMatches	                        | Questionlist should return empty if no matches |
    |testFindUserValid	                                    | User should be found if it exists              |
    |testFindUserInvalid	                                | User should not be found if it doesn't exist   |
    |testFindUserEmptyUsername	                            | Cannot find user with empty username           |
    |testFindUserNullUsername	                            | Null user should return nothing                |
    +--------------------------------------------------------------------------------------------------------+
    */

    /* Eden Moore second half of interview Application test
    +-------------------------------------------------------+------------------------------------------------+
    | Test                                                  | Reasoning                                      |
    +-------------------------------------------------------+------------------------------------------------+
    |adminAddsQuestion                                      | Admins are able to add questions               |
    |editorAddsQuestion	                                    | Editors are able to add questions              |
    |studentAddsQuestion	                                | students should be unable to add questions     |
    |editorAddsNullQuestion	                                | null questions should not be added into list   |
    |editorAddsBlankQuestion	                            | blank questions should not be added to list    |
    |editorAddsExistingQuestion	                            | duplicate questions should not be added to list|
    |editorAddsQuestionWithBlankTitle	                    | questions are required to have titles          |
    |editorAddsQuestionWithBlankDescription	                | questions are required to have descriptions    |
    |editorAddsQuestionWithNoQuestionType	                | questions are required to have a type          |
    |editorAddsQuestionWithNoDiscipline	                    | questions are required to have a discipline    |
    |editorAddsQuestionWithNoDifficulty	                    | questions are required to have a difficulty    |
    |editorAddsQuestionWithNoCourse                         | question are required to have courses related  |
    |adminDeletesQuestion	                                | admins can delete questions                    |
    |editorDeletesQuestion                                  | editors can delete questions                   |
    |studentDeletesQuestion		                            | students should be unable to delete questions  |
    |studentRatesQWithValidNumber                           | students should be able to rate questions      |
    |studentRatesQWithNegativeNumber	                    | students cannot rate with negatives            |
    |studentRatesQWithNumberGreatorThan5	                | students must rate with a number 1-5           |
    |studentRatesQuestionPreviouslyRated	                | students cannot re-rate a question             |
    |studentRatesCWithValidNumber                           | students should be able to rate comments       |
    |studentRatesCWithNegativeNumber	                    | student cannot rate with negatives             |
    |studentRatesCWithNumberGreatorThan5	                | students must rate with a number 1-5           |
    |studentRatesCommentPreviouslyRated	                    | students cannot re-rate comments               |
    |studentToEditor                                        | students can be promoted to editors            |
    |editorToEditor	                                        | editors cannot be promoted to editors          |
    |adminToEditor                                          | admins cannot be demoted to editors            |
    |studentSearchingCorrectKeyword	                        | should be able to find existing key terms      |
    |studentSearchingKeywordNonExistant	                    | should return blank if there are none          |
    |studentSearchingKeywordWeirdCasing                     | search should be case-insensitive              |
    +--------------------------------------------------------------------------------------------------------+
    */

    private InterviewApplication interviewApp;
    
    @Before
    public void setup() {
        interviewApp = new InterviewApplication();
    }
     
    /*
    *   TESTING LOGIN()
    */
    @Test
    public void testLoginValid() {
        User user = interviewApp.login("student", "password");
        assertTrue(user != null);
    }

    @Test
    public void testLoginInvalidUsername() {
        User user = interviewApp.login("", "password");
        assertTrue(user == null);
    }

    @Test
    public void testLoginInvalidPassword() {
        User user = interviewApp.login("student", "invalidpassword");
        assertTrue(user == null);
    }

    /*
    *   TESTING LOGOUT()
    */
    @Test
    public void testLogoutValid() {
        User user = interviewApp.login("student", "password");
        boolean result = interviewApp.logout(user);
        assertTrue(result);
    }

    @Test
    public void testLogoutInvalidUser() {
        boolean result = interviewApp.logout(null);
        assertTrue(result);
    }

    /*
    *   TESTING CREATEACCOUNT()
    */
    @Test
    public void testCreateAccountValid() {
        User user = interviewApp.createAccount("userA", "password123", new Date(), "a@example.com", "0123456789", "Computer Science");
        assertTrue(user != null);
    }

    @Test
    public void testCreateAccountEmptyUsername() {
        User user = interviewApp.createAccount("", "password123", new Date(), "b@example.com", "5139491005", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidUsername() {
        User user = interviewApp.createAccount("invalid username", "password123", new Date(), "c@example.com", "4124015439", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyPassword() {
        User user = interviewApp.createAccount("userD", "", new Date(), "d@example.com", "3523028369", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidPassword() {
        User user = interviewApp.createAccount("userE", ".", new Date(), "e@example.com", "3456789012", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyDateOfBirth() {
        User user = interviewApp.createAccount("userF", "password123", null, "F@example.com", "5933723305", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidDateOfBirth() {
        User user = interviewApp.createAccount("userG", "password123", new Date(System.currentTimeMillis() + 100000), "g@example.com", "8184727436", "Computer Science");
        assertTrue(user == null);
    }


    @Test
    public void testCreateAccountEmptyEmail() {
        User user = interviewApp.createAccount("userH", "password123", new Date(), "", "8519955095", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidEmail() {
        User user = interviewApp.createAccount("userI", "password123", new Date(), "invalidemail", "6954741211", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyUSCID() {
        User user = interviewApp.createAccount("userJ", "password123", new Date(), "j@example.com", "", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidUSCID() {
        User user = interviewApp.createAccount("userK", "password123", new Date(), "k@example.com", "invaliduscid", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountEmptyMajor() {
        User user = interviewApp.createAccount("userL", "password123", new Date(), "l@example.com", "7551533767", "");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountInvalidMajor() {
        User user = interviewApp.createAccount("userM", "password123", new Date(), "m@example.com", "9827774125", "Invalid Major");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountDuplicateUsername() {
        User user = interviewApp.createAccount("student", "password", new Date(), "duplicatestudent@example.com", "9293241797", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountDuplicateEmail() {
        User user = interviewApp.createAccount("testuser", "password", new Date(), "student@example.com", "7533476999", "Computer Science");
        assertTrue(user == null);
    }

    @Test
    public void testCreateAccountDuplicateUSCID() {
        User user = interviewApp.createAccount("testuser", "password", new Date(), "testuser@example.com", "123456890", "Computer Science");
        assertTrue(user == null);
    }

    /*
    *   TESTING DELETEUSER()
    */
   @Test
    public void testDeleteUserValidUser() {
        interviewApp.login("admin", "password");
        User newUser = interviewApp.createAccount("deleteMe", "password", new Date(), "deleteMe@example.com", "3456789012", "Computer Science");
        boolean result = interviewApp.deleteUser(newUser);      // Should be valid, admin is deleting account
        assertTrue(result);
    }

    @Test
    public void testDeleteUserInvalidUser() {
        interviewApp.login("student", "password");
        User newUser = interviewApp.createAccount("deleteMe", "password", new Date(), "deleteMe@example.com", "3456789012", "Computer Science");
        boolean result = interviewApp.deleteUser(newUser);   // Should NOT be valid, student is deleting account
        assertTrue(result);
    }

    /*
    *   TESTING EDITUSER()
    *   this entire method should probably be revamped
    *   currently, you need to make a new user to add to the list, and the old is deleted
    *   there has to be a way to just change a certain piece of info in the user...
    */
    @Test
    public void testEditUser() {                      
        interviewApp.login("admin", "password"); 
        User newUser = interviewApp.createAccount("editMe", "password", new Date(), "editMe@example.com", "3456789012", "Computer Science");
        interviewApp.editUser(new Student("editMe", "newpassword", new Date(), "editMe@example.com", "3456789012", "Computer Science"));
        assertTrue(newUser.getPassword().equals("newpassword"));
    }

    @Test
    public void testEditUserInvalidUser() {
        interviewApp.login("student", "password");
        interviewApp.createAccount("editMe", "password", new Date(), "editMe@example.com", "3456789012", "Computer Science");
        Boolean editedUser = interviewApp.editUser(new Student("editMe", "newpassword", new Date(), "editMe@example.com", "3456789012", "Computer Science"));
        assertFalse(editedUser);
    }


    /*
    *   TESTING FILTERQUESTION()
    */
    @Test
    public void testFilterQuestionValid() {
        interviewApp.login("student", "password");
        ArrayList<Question> questionList = QuestionList.getInstance().getQuestions();
        ArrayList<Question> filteredQuestions = interviewApp.filterQuestion(questionList, QuestionType.TECHNICAL, null, null, null, null);
        assertTrue(!filteredQuestions.isEmpty());
        assertTrue(filteredQuestions.get(0).getType().equals(QuestionType.TECHNICAL));
        assertTrue(filteredQuestions.get(filteredQuestions.size()-1).getType().equals(QuestionType.TECHNICAL));
    }

    @Test
    public void testFilterQuestionEmptySearch() {
        interviewApp.login("student", "password");
        ArrayList<Question> questionList = QuestionList.getInstance().getQuestions();
        ArrayList<Question> filteredQuestions = interviewApp.filterQuestion(questionList, null, null, null, null, null);
        assertTrue(!filteredQuestions.isEmpty());
    }

    @Test
    public void testFilterQuestionNoMatches() {
        interviewApp.login("student", "password");
        ArrayList<Question> questionList = QuestionList.getInstance().getQuestions();
        ArrayList<Question> filteredQuestions = interviewApp.filterQuestion(questionList, QuestionType.BEHAVIORAL, Discipline.CYBERSEC, Difficulty.HARD, Course.CSCE146, QuestionTag.FILL_IN_THE_BLANK);
        assertTrue(filteredQuestions.isEmpty());
    }

    /*
    *   TESTING FINDUSER()
    */
    @Test
    public void testFindUserValid() {
        interviewApp.login("admin", "password");
        User user = interviewApp.findUser("student");
        assertTrue(user != null);
    }

    @Test
    public void testFindUserInvalid() {
        interviewApp.login("admin", "password");
        User user = interviewApp.findUser("nonexistentuser");
        assertTrue(user == null);
    }

    @Test
    public void testFindUserEmptyUsername() {
        interviewApp.login("admin", "password");
        User user = interviewApp.findUser("");
        assertTrue(user == null);
    }

    @Test
    public void testFindUserNullUsername() {
        interviewApp.login("admin", "password");
        User user = interviewApp.findUser(null);
        assertTrue(user == null);
    }

   
    // testing add question
    @Test
    public void adminAddsQuestion() {
        User admin = interviewApp.login("admin", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPENG);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE146);
        boolean result = interviewApp.addQuestion("admin question", admin, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "this is an admin question");
        assertTrue(result);
    }

    @Test
    public void editorAddsQuestion() {
        User editor = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPENG);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE146);
        boolean result = interviewApp.addQuestion("editor question", editor, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "this is an editor question");
        assertTrue(result);
    }

    @Test
    public void studentAddsQuestion() {
        User student = interviewApp.login("student", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPENG);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE146);
        boolean result = interviewApp.addQuestion("student question", student, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "this is a student question");
        assertFalse(result);
    }

    @Test
    public void editorAddsNullQuestion() {
        User editor = interviewApp.login("editor", "password");
        boolean result = interviewApp.addQuestion(null, editor, null, null, null, null, null, null);
        assertFalse(result);
    }

    @Test
    public void editorAddsBlankQuestion() {
        User editor = interviewApp.login("editor", "password");
        boolean result = interviewApp.addQuestion("", editor, null, null, null, null, null, "");
        assertFalse(result);
    }

    @Test
    public void editorAddsExistingQuestion() {
        User editor = interviewApp.login("editor", "password");
        boolean result = interviewApp.addQuestion("admin question", editor, null, null, null, null, null, "this is an admin question");
        assertFalse(result);
    }

    @Test
    public void editorAddsQuestionWithBlankTitle() {
        User editor = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE240);
        boolean result = interviewApp.addQuestion("", editor, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "this quesiton has a blank title");
        assertFalse(result);
    }

    @Test
    public void editorAddsQuestionWithBlankDescription() {
        User editor = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE145);
        boolean result = interviewApp.addQuestion("this question has a blank description", editor, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "");
        assertFalse(result);
    }

    @Test
    public void editorAddsQuestionWithNoQuestionType() {
        User editor = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE145);
        boolean result = interviewApp.addQuestion("no qtype", editor, hints, null, disciplines, Difficulty.EASY, courses, "no qtype");
        assertFalse(result);
    }

    @Test
    public void editorAddsQuestionWithNoDiscipline() {
        User editor = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE145);
        boolean result = interviewApp.addQuestion("no disipline", editor, hints, QuestionType.TECHNICAL, null, Difficulty.EASY, courses, "no discipline");
        assertFalse(result);
    }

    @Test
    public void editorAddsQuestionWithNoDifficulty() {
         User editor = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE145);
        boolean result = interviewApp.addQuestion("no difficulty", editor, hints, QuestionType.TECHNICAL, disciplines, null, courses, "no difficulty");
        assertFalse(result);
    }

    @Test
    public void editorAddsQuestionWithNoCourse() {
        User editor = interviewApp.login("editor", "password");
        ArrayList<String> hints = new ArrayList<>();
        hints.add("");
        ArrayList<Discipline> disciplines = new ArrayList<>();
        disciplines.add(Discipline.COMPSCI);
        boolean result = interviewApp.addQuestion("no courses", editor, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, null, "no courses");
        assertFalse(result);
    }

    // testing delete question

    @Test
    public void adminDeletesQuestion() {
        User admin = interviewApp.login("admin", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.get(questions.size() - 1);
        boolean result = interviewApp.deleteQuestion(question);
        assertTrue(result);
    }

    @Test
    public void editorDeletesQuestion() {
        User editor = interviewApp.login("editor", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.get(questions.size() - 1);
        boolean result = interviewApp.deleteQuestion(question);
        assertTrue(result);
    }

    @Test
    public void studentDeletesQuestion() {
        User student = interviewApp.login("student", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.get(questions.size() - 1);
        boolean result = interviewApp.deleteQuestion(question);
        assertFalse(result);
    }

    // testing rate question

    @Test
    public void studentRatesQWithValidNumber() {
        interviewApp.login("student", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.get(0);
        boolean result = interviewApp.rateQuestion(question, 4.0);
        assertTrue(result);
    }

    @Test
    public void studentRatesQWithNegativeNumber() {
        interviewApp.login("student", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.get(1);
        boolean result = interviewApp.rateQuestion(question, -1.0);
        assertFalse(result);
    }

    @Test
    public void studentRatesQWithNumberGreatorThan5() {
        interviewApp.login("student", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.get(2);
        boolean result = interviewApp.rateQuestion(question, 6.0);
        assertFalse(result);
    }

    @Test
    public void studentRatesQuestionPreviouslyRated() {
        interviewApp.login("student", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.get(0);
        boolean result = interviewApp.rateQuestion(question, 3.0);
        assertFalse(result);
    }

    // testing rate comment

    @Test
    public void studentRatesCWithValidNumber() {
        interviewApp.login("student", "password");
        UserList userlist = UserList.getInstance();
        ArrayList<User> users = userlist.getUsers();
        User author = users.get(0);
        ArrayList<CommentTag> ct = new ArrayList();
        ct.add(CommentTag.DISCUSSION);
        Comment c = new Comment("this is a comment", "wow please rate me", author, ct, new ArrayList(), true);
        boolean result = interviewApp.rateComment(c, 5.0);
        assertTrue(result);
    }

    @Test
    public void studentRatesCWithNegativeNumber() {
        interviewApp.login("student", "password");
        UserList userlist = UserList.getInstance();
        ArrayList<User> users = userlist.getUsers();
        User author = users.get(1);
        ArrayList<CommentTag> ct = new ArrayList();
        ct.add(CommentTag.DISCUSSION);
        Comment c = new Comment("2nd comment", "wow please rate me", author, ct, new ArrayList(), true);
        boolean result = interviewApp.rateComment(c, -1.0);
        assertFalse(result);
    }

    @Test
    public void studentRatesCWithNumberGreatorThan5() {
        interviewApp.login("student", "password");
        UserList userlist = UserList.getInstance();
        ArrayList<User> users = userlist.getUsers();
        User author = users.get(2);
        ArrayList<CommentTag> ct = new ArrayList();
        ct.add(CommentTag.DISCUSSION);
        Comment c = new Comment("3rd comment", "wow please rate me", author, ct, new ArrayList(), true);
        boolean result = interviewApp.rateComment(c, 6.0);
        assertFalse(result);
    }
    
    @Test
    public void studentRatesCommentPreviouslyRated() {
        interviewApp.login("student", "password");
        UserList userlist = UserList.getInstance();
        ArrayList<User> users = userlist.getUsers();
        User author = users.get(0);
        ArrayList<CommentTag> ct = new ArrayList();
        ct.add(CommentTag.DISCUSSION);
        Comment c = new Comment("4th", "wow please rate me", author, ct, new ArrayList(), true);
        interviewApp.rateComment(c, 5.0);
        boolean result = interviewApp.rateComment(c, 3.0);
        assertFalse(result);
    }

    @Test
    public void studentRatesOwnComment() {
        User student = interviewApp.login("student", "password");
        ArrayList<CommentTag> ct = new ArrayList();
        ct.add(CommentTag.DISCUSSION);
        Comment c = new Comment("my own comment", "hehehe", student, ct, new ArrayList(), true);
        boolean result = interviewApp.rateComment(c, 4.0);
        assertFalse(result);
    }

    //testing toEditor

    @Test
    public void studentToEditor() {
        User student = interviewApp.login("student", "password");
        boolean result = interviewApp.toEditor(student);
        assertTrue(result);
    }

    @Test
    public void editorToEditor() {
        User editor = interviewApp.login("editor", "password");
        boolean result = interviewApp.toEditor(editor);
        assertFalse(result);
    }

    @Test
    public void adminToEditor() {
        User admin = interviewApp.login("admin", "password");
        boolean result = interviewApp.toEditor(admin);
        assertFalse(result);
    }
      // testing search questions

    @Test
    public void studentSearchingCorrectKeyword() {
        interviewApp.login("student", "password");
        ArrayList<Question> results = interviewApp.searchQuestions("Binary");
        boolean content = !results.isEmpty();
        assertTrue(content);
    }

    @Test
    public void studentSearchingKeywordNonExistant() {
        interviewApp.login("student", "password");
        ArrayList<Question> results = interviewApp.searchQuestions("potato chip");
        boolean content = !results.isEmpty();
        assertFalse(content);
    }

    @Test
    public void studentSearchingKeywordWeirdCasing() {
        interviewApp.login("student", "password");
        ArrayList<Question> results = interviewApp.searchQuestions("BiNaRy");
        boolean content = !results.isEmpty();
        assertTrue(content);
    }

   

