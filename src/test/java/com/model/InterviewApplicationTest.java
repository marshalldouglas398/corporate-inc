package com.model;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;



public class InterviewApplicationTest {
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
        Question question = questions.getLast();
        boolean result = interviewApp.deleteQuestion(question);
        assertTrue(result);
    }

    @Test
    public void editorDeletesQuestion() {
        User editor = interviewApp.login("editor", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.getLast();
        boolean result = interviewApp.deleteQuestion(question);
        assertTrue(result);
    }

    @Test
    public void studentDeletesQuestion() {
        User student = interviewApp.login("student", "password");
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        Question question = questions.getLast();
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
}

