package com.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TestDataLoader {
    private static final String USER_FILE_NAME = "json/users.json";
    private static final String QUESTION_FILE_NAME = "json/questions.json";

    /*
        Test: Getting a question with full information
        Reasoning: Need to verify that DataLoader loads the top level question fields correctly
    */
    @Test
    public void testGetQuestionsRand() {
        try { // We're reading and writing files in this test, so we need to catch exceptions and fail the test if they happen
            // Save the real file contents so the test can put them back when it is done
            String originalUsersJSON = "";
            String originalQuestionsJSON = "";
            FileReader userReader = new FileReader(USER_FILE_NAME);
            FileReader questionReader = new FileReader(QUESTION_FILE_NAME);
            int userCharacter = userReader.read();
            int questionCharacter = questionReader.read();

            // Read the entire contents of both files into strings so the test can restore them later
            while (userCharacter != -1) {
                originalUsersJSON += (char) userCharacter;
                userCharacter = userReader.read();
            }

            while (questionCharacter != -1) {
                originalQuestionsJSON += (char) questionCharacter;
                questionCharacter = questionReader.read();
            }

            userReader.close();
            questionReader.close();

            // Build the users that this question should point to when DataLoader reads the files.
            Editor editor = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
            Student student = new Student("testStudent", "studentPassword", new Date(2000000000L), "student@test.com", "1234567890", "Computer Science");
            student.setStreak(2);

            // Save those users into the user json file.
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().add(editor);
            UserList.getInstance().getUsers().add(student);
            UserList.getInstance().save();

            // Build the full question data that should be loaded back from the file.
            ArrayList<Section> questionSections = new ArrayList<Section>();
            questionSections.add(new Section("Question Section", "Question Section Description", null, "System.out.println(\"question\");"));

            ArrayList<Section> commentSections = new ArrayList<Section>();
            commentSections.add(new Section("Comment Section", "Comment Section Description", null, "System.out.println(\"comment\");"));

            ArrayList<CommentTag> replyTags = new ArrayList<CommentTag>();
            replyTags.add(CommentTag.DISCUSSION);
            ArrayList<Comment> replies = new ArrayList<Comment>();
            replies.add(new Comment("Reply Title", "This is a reply", editor, replyTags, new ArrayList<Section>(), new ArrayList<Comment>(), 3.0, 1.0, true));

            ArrayList<CommentTag> commentTags = new ArrayList<CommentTag>();
            commentTags.add(CommentTag.SOLUTION);
            commentTags.add(CommentTag.HINT);
            ArrayList<Comment> comments = new ArrayList<Comment>();
            comments.add(new Comment("First Comment", "This is the first comment", student, commentTags, commentSections, replies, 4.0, 1.0, false));

            ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
            disciplines.add(Discipline.COMPSCI);
            disciplines.add(Discipline.COMPENG);

            ArrayList<Course> courses = new ArrayList<Course>();
            courses.add(Course.CSCE247);

            ArrayList<QuestionTag> questionTags = new ArrayList<QuestionTag>();
            questionTags.add(QuestionTag.WRITE_CODE);
            questionTags.add(QuestionTag.OPTIMIZE);

            ArrayList<String> hints = new ArrayList<String>();
            hints.add("testHint1");
            hints.add("testHint2");

            // Save the question into the question json file.
            Question question = new Question(UUID.fromString("33333333-3333-3333-3333-333333333333"), "testTitle", "testDescription", questionSections, editor, comments, 4.5, 1.0, QuestionType.TECHNICAL, disciplines, Difficulty.MEDIUM, courses, true, questionTags, hints, 25);

            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().add(question);
            QuestionList.getInstance().save();

            // Load the question back through DataLoader so the test checks the real loader code.
            ArrayList<Question> loadedQuestions = DataLoader.getQuestions();

            // Restore the original json files so this test does not leave fake data behind.
            FileWriter restoreUsersWriter = new FileWriter(USER_FILE_NAME);
            restoreUsersWriter.write(originalUsersJSON);
            restoreUsersWriter.close();

            FileWriter restoreQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            restoreQuestionsWriter.write(originalQuestionsJSON);
            restoreQuestionsWriter.close();

            // Reload the original data into the singleton lists so later tests start clean.
            ArrayList<User> restoredUsers = DataLoader.getUsers();
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().addAll(restoredUsers);

            ArrayList<Question> restoredQuestions = DataLoader.getQuestions();
            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().addAll(restoredQuestions);

            // Check that everything was correct
            assertTrue(loadedQuestions.size() == 1);
            assertTrue(loadedQuestions.get(0).getId().toString().equals("33333333-3333-3333-3333-333333333333"));
            assertTrue(loadedQuestions.get(0).getTitle().equals("testTitle"));
            assertTrue(loadedQuestions.get(0).getDescription().equals("testDescription"));
            assertTrue(loadedQuestions.get(0).getAuthor() != null);
            assertTrue(loadedQuestions.get(0).getAuthor().getUsername().equals("testEditor"));
            assertTrue(loadedQuestions.get(0).getHints().size() == 2);
            assertTrue(loadedQuestions.get(0).getHints().get(0).equals("testHint1"));
            assertTrue(loadedQuestions.get(0).getType() == QuestionType.TECHNICAL);
            assertTrue(loadedQuestions.get(0).getDiscipline().size() == 2);
            assertTrue(loadedQuestions.get(0).getDiscipline().get(0) == Discipline.COMPSCI);
            assertTrue(loadedQuestions.get(0).getDiscipline().get(1) == Discipline.COMPENG);
            assertTrue(loadedQuestions.get(0).getDifficulty() == Difficulty.MEDIUM);
            assertTrue(loadedQuestions.get(0).getCourse().size() == 1);
            assertTrue(loadedQuestions.get(0).getCourse().get(0) == Course.CSCE247);
            assertTrue(loadedQuestions.get(0).isInterviewMode());
            assertTrue(loadedQuestions.get(0).getTag().size() == 2);
            assertTrue(loadedQuestions.get(0).getTag().get(0) == QuestionTag.WRITE_CODE);
            assertTrue(loadedQuestions.get(0).getTag().get(1) == QuestionTag.OPTIMIZE);
            assertTrue(loadedQuestions.get(0).getRating() == 4.5);
            assertTrue(loadedQuestions.get(0).getNumRatings() == 1.0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Test: Getting a question with nested sections, comments, and replies
        Reasoning: Need to verify that DataLoader handles the recursive question data correctly
    */
    @Test
    public void testGetQuestionsNestedData() {
        try {
            // Save the real file contents so the test can restore them later.
            String originalUsersJSON = "";
            String originalQuestionsJSON = "";
            FileReader userReader = new FileReader(USER_FILE_NAME);
            FileReader questionReader = new FileReader(QUESTION_FILE_NAME);
            int userCharacter = userReader.read();
            int questionCharacter = questionReader.read();

            // Read the entire contents of both files into strings so the test can restore them later
            while (userCharacter != -1) {
                originalUsersJSON += (char) userCharacter;
                userCharacter = userReader.read();
            }

            while (questionCharacter != -1) {
                originalQuestionsJSON += (char) questionCharacter;
                questionCharacter = questionReader.read();
            }

            userReader.close();
            questionReader.close();

            // Build the users that will be referenced by the comment and reply.
            Editor editor = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
            Student student = new Student("testStudent", "studentPassword", new Date(2000000000L), "student@test.com", "1234567890", "Computer Science");
            student.setStreak(2);

            // Save those users first so DataLoader can resolve author ids when it loads the question.
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().add(editor);
            UserList.getInstance().getUsers().add(student);
            UserList.getInstance().save();

            // Build the nested sections, comments, and replies for the question.
            ArrayList<Section> questionSections = new ArrayList<Section>();
            questionSections.add(new Section("Question Section", "Question Section Description", null, "System.out.println(\"question\");"));

            ArrayList<Section> commentSections = new ArrayList<Section>();
            commentSections.add(new Section("Comment Section", "Comment Section Description", null, "System.out.println(\"comment\");"));

            ArrayList<CommentTag> replyTags = new ArrayList<CommentTag>();
            replyTags.add(CommentTag.DISCUSSION);
            ArrayList<Comment> replies = new ArrayList<Comment>();
            replies.add(new Comment("Reply Title", "This is a reply", editor, replyTags, new ArrayList<Section>(), new ArrayList<Comment>(), 3.0, 1.0, true));

            ArrayList<CommentTag> commentTags = new ArrayList<CommentTag>();
            commentTags.add(CommentTag.SOLUTION);
            commentTags.add(CommentTag.HINT);
            ArrayList<Comment> comments = new ArrayList<Comment>();
            comments.add(new Comment("First Comment", "This is the first comment", student, commentTags, commentSections, replies, 4.0, 1.0, false));

            ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
            disciplines.add(Discipline.COMPSCI);
            disciplines.add(Discipline.COMPENG);

            ArrayList<Course> courses = new ArrayList<Course>();
            courses.add(Course.CSCE247);

            ArrayList<QuestionTag> questionTags = new ArrayList<QuestionTag>();
            questionTags.add(QuestionTag.WRITE_CODE);
            questionTags.add(QuestionTag.OPTIMIZE);

            ArrayList<String> hints = new ArrayList<String>();
            hints.add("testHint1");
            hints.add("testHint2");

            // Save the full nested question into the question json file.
            Question question = new Question(UUID.fromString("33333333-3333-3333-3333-333333333333"), "testTitle", "testDescription", questionSections, editor, comments, 4.5, 1.0, QuestionType.TECHNICAL, disciplines, Difficulty.MEDIUM, courses, true, questionTags, hints, 25);

            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().add(question);
            QuestionList.getInstance().save();

            // Load it back through DataLoader to check the recursive parsing logic.
            ArrayList<Question> loadedQuestions = DataLoader.getQuestions();

            // Restore the original json files.
            FileWriter restoreUsersWriter = new FileWriter(USER_FILE_NAME);
            restoreUsersWriter.write(originalUsersJSON);
            restoreUsersWriter.close();

            FileWriter restoreQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            restoreQuestionsWriter.write(originalQuestionsJSON);
            restoreQuestionsWriter.close();

            // Put the original file data back into the singleton lists.
            ArrayList<User> restoredUsers = DataLoader.getUsers();
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().addAll(restoredUsers);

            ArrayList<Question> restoredQuestions = DataLoader.getQuestions();
            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().addAll(restoredQuestions);

            // Check the question section data.
            assertTrue(loadedQuestions.size() == 1);
            assertTrue(loadedQuestions.get(0).getSections().size() == 1);
            assertTrue(loadedQuestions.get(0).getSections().get(0).getTitle().equals("Question Section"));
            assertTrue(loadedQuestions.get(0).getSections().get(0).getDescription().equals("Question Section Description"));
            assertTrue(loadedQuestions.get(0).getSections().get(0).getCode().equals("System.out.println(\"question\");"));

            // Check the main comment data.
            assertTrue(loadedQuestions.get(0).getComments().size() == 1);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getTitle().equals("First Comment"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getComment().equals("This is the first comment"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getAuthor() != null);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getAuthor().getUsername().equals("testStudent"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getTags().size() == 2);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getTags().get(0) == CommentTag.SOLUTION);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getTags().get(1) == CommentTag.HINT);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getRating() == 4.0);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getNumRatings() == 1.0);
            assertTrue(!loadedQuestions.get(0).getComments().get(0).isAuthor());

            // Check the comment section data.
            assertTrue(loadedQuestions.get(0).getComments().get(0).getSections().size() == 1);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getSections().get(0).getTitle().equals("Comment Section"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getSections().get(0).getDescription().equals("Comment Section Description"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getSections().get(0).getCode().equals("System.out.println(\"comment\");"));

            // Check the reply data.
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().size() == 1);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getTitle().equals("Reply Title"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getComment().equals("This is a reply"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getAuthor() != null);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getAuthor().getUsername().equals("testEditor"));
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getTags().size() == 1);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getTags().get(0) == CommentTag.DISCUSSION);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getRating() == 3.0);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).getNumRatings() == 1.0);
            assertTrue(loadedQuestions.get(0).getComments().get(0).getReplies().get(0).isAuthor());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Test: Getting a question with missing optional information
        Reasoning: Need to verify the default values DataLoader builds when json leaves fields out
    */
    @Test
    public void testGetQuestionsEmpty() {
        try {
            // Save the real file contents so the test can restore them later.
            String originalUsersJSON = "";
            String originalQuestionsJSON = "";
            FileReader userReader = new FileReader(USER_FILE_NAME);
            FileReader questionReader = new FileReader(QUESTION_FILE_NAME);
            int userCharacter = userReader.read();
            int questionCharacter = questionReader.read();

            while (userCharacter != -1) {
                originalUsersJSON += (char) userCharacter;
                userCharacter = userReader.read();
            }

            while (questionCharacter != -1) {
                originalQuestionsJSON += (char) questionCharacter;
                questionCharacter = questionReader.read();
            }

            userReader.close();
            questionReader.close();

            // Save an empty user list because this test does not need any authors.
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().save();

            // Build a question with missing values so DataLoader has to fill in defaults.
            Question emptyQuestion = new Question((UUID) null, null, null, null, null, null, null, null, null, null, null, null, false, null, null, -1);

            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().add(emptyQuestion);
            QuestionList.getInstance().save();

            // Load the question back through DataLoader.
            ArrayList<Question> loadedQuestions = DataLoader.getQuestions();

            // Restore the original json files.
            FileWriter restoreUsersWriter = new FileWriter(USER_FILE_NAME);
            restoreUsersWriter.write(originalUsersJSON);
            restoreUsersWriter.close();

            FileWriter restoreQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            restoreQuestionsWriter.write(originalQuestionsJSON);
            restoreQuestionsWriter.close();

            // Put the original file data back into the singleton lists.
            ArrayList<User> restoredUsers = DataLoader.getUsers();
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().addAll(restoredUsers);

            ArrayList<Question> restoredQuestions = DataLoader.getQuestions();
            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().addAll(restoredQuestions);

            // Check that DataLoader replaced missing values with its default empty values.
            assertTrue(loadedQuestions.size() == 1);
            assertTrue(loadedQuestions.get(0).getTitle().equals(""));
            assertTrue(loadedQuestions.get(0).getDescription().equals(""));
            assertTrue(loadedQuestions.get(0).getAuthor() == null);
            assertTrue(loadedQuestions.get(0).getHints() != null);
            assertTrue(loadedQuestions.get(0).getHints().size() == 0);
            assertTrue(loadedQuestions.get(0).getType() == null);
            assertTrue(loadedQuestions.get(0).getDiscipline() != null);
            assertTrue(loadedQuestions.get(0).getDiscipline().size() == 0);
            assertTrue(loadedQuestions.get(0).getDifficulty() == null);
            assertTrue(loadedQuestions.get(0).getCourse() != null);
            assertTrue(loadedQuestions.get(0).getCourse().size() == 0);
            assertTrue(loadedQuestions.get(0).getSections() != null);
            assertTrue(loadedQuestions.get(0).getSections().size() == 0);
            assertTrue(loadedQuestions.get(0).getComments() != null);
            assertTrue(loadedQuestions.get(0).getComments().size() == 0);
            assertTrue(loadedQuestions.get(0).getTag() != null);
            assertTrue(loadedQuestions.get(0).getTag().size() == 0);
            assertTrue(loadedQuestions.get(0).getRating() == null);
            assertTrue(loadedQuestions.get(0).getNumRatings() == 0.0);
            assertTrue(!loadedQuestions.get(0).isInterviewMode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Test: Getting users with different roles
        Reasoning: Need to verify that DataLoader builds the correct subclass for each role
    */
    @Test
    public void testGetUsersRand() {
        try {
            // Save the real file contents so the test can restore them later.
            String originalUsersJSON = "";
            String originalQuestionsJSON = "";
            FileReader userReader = new FileReader(USER_FILE_NAME);
            FileReader questionReader = new FileReader(QUESTION_FILE_NAME);
            int userCharacter = userReader.read();
            int questionCharacter = questionReader.read();

            while (userCharacter != -1) {
                originalUsersJSON += (char) userCharacter;
                userCharacter = userReader.read();
            }

            while (questionCharacter != -1) {
                originalQuestionsJSON += (char) questionCharacter;
                questionCharacter = questionReader.read();
            }

            userReader.close();
            questionReader.close();

            // Build one student, one editor, and one admin to test each role path in DataLoader.
            Student student = new Student("testStudent", "studentPassword", new Date(3000000000L), "student@test.com", "1234567890", "Computer Science");
            student.setStreak(5);

            Editor editor = new Editor("testEditor", "editorPassword", new Date(4000000000L), "editor@test.com", "Editor");
            Admin admin = new Admin("testAdmin", "adminPassword", new Date(5000000000L), "admin@test.com");

            // Save those users and clear the question file because this test only cares about users.
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().add(student);
            UserList.getInstance().getUsers().add(editor);
            UserList.getInstance().getUsers().add(admin);
            UserList.getInstance().save();

            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().save();

            // Load the users back through DataLoader.
            ArrayList<User> loadedUsers = DataLoader.getUsers();

            // Restore the original json files.
            FileWriter restoreUsersWriter = new FileWriter(USER_FILE_NAME);
            restoreUsersWriter.write(originalUsersJSON);
            restoreUsersWriter.close();

            FileWriter restoreQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            restoreQuestionsWriter.write(originalQuestionsJSON);
            restoreQuestionsWriter.close();

            // Put the original file data back into the singleton lists.
            ArrayList<User> restoredUsers = DataLoader.getUsers();
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().addAll(restoredUsers);

            ArrayList<Question> restoredQuestions = DataLoader.getQuestions();
            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().addAll(restoredQuestions);

            // Check the student values.
            assertTrue(loadedUsers.size() == 3);
            assertTrue(loadedUsers.get(0) instanceof Student);
            assertTrue(loadedUsers.get(0).getPassword().equals("studentPassword"));
            assertTrue(loadedUsers.get(0).getBirthDate().equals(student.getBirthDate()));
            assertTrue(loadedUsers.get(0).getEmail().equals("student@test.com"));
            assertTrue(loadedUsers.get(0).getRole().equals("Student"));
            assertTrue(((Student) loadedUsers.get(0)).getUSCID().equals("1234567890"));
            assertTrue(((Student) loadedUsers.get(0)).getMajor().equals("Computer Science"));
            assertTrue(((Student) loadedUsers.get(0)).getQuestionsAnswered().size() == 0);
            assertTrue(((Student) loadedUsers.get(0)).getCoursesTaken().size() == 0);
            assertTrue(((Student) loadedUsers.get(0)).getStreak() == 5);

            // Check the editor values.
            assertTrue(loadedUsers.get(1) instanceof Editor);
            assertTrue(loadedUsers.get(1).getPassword().equals("editorPassword"));
            assertTrue(loadedUsers.get(1).getBirthDate().equals(editor.getBirthDate()));
            assertTrue(loadedUsers.get(1).getEmail().equals("editor@test.com"));
            assertTrue(loadedUsers.get(1).getRole().equals("Editor"));
            assertTrue(((Editor) loadedUsers.get(1)).getQuestionsMade().size() == 0);
            assertTrue(loadedUsers.get(1).isEditor());

            // Check the admin values.
            assertTrue(loadedUsers.get(2) instanceof Admin);
            assertTrue(loadedUsers.get(2).getPassword().equals("adminPassword"));
            assertTrue(loadedUsers.get(2).getBirthDate().equals(admin.getBirthDate()));
            assertTrue(loadedUsers.get(2).getEmail().equals("admin@test.com"));
            assertTrue(loadedUsers.get(2).getRole().equals("Admin"));
            assertTrue(((Admin) loadedUsers.get(2)).getQuestionsMade().size() == 0);
            assertTrue(loadedUsers.get(2).isAdmin());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Test: Getting the actual values inside the user lists
        Reasoning: Need to verify that DataLoader loads UUID and Course values correctly, not just the list sizes
    */
    @Test
    public void testGetUsersListValues() {
        try {
            // Save the real file contents so the test can restore them later.
            String originalUsersJSON = "";
            String originalQuestionsJSON = "";
            FileReader userReader = new FileReader(USER_FILE_NAME);
            FileReader questionReader = new FileReader(QUESTION_FILE_NAME);
            int userCharacter = userReader.read();
            int questionCharacter = questionReader.read();

            while (userCharacter != -1) {
                originalUsersJSON += (char) userCharacter;
                userCharacter = userReader.read();
            }

            while (questionCharacter != -1) {
                originalQuestionsJSON += (char) questionCharacter;
                questionCharacter = questionReader.read();
            }

            userReader.close();
            questionReader.close();

            // Build users with actual UUID and Course data inside their lists.
            Student student = new Student("testStudent", "studentPassword", new Date(3000000000L), "student@test.com", "1234567890", "Computer Science");
            student.getQuestionsAnswered().add(UUID.fromString("33333333-3333-3333-3333-333333333333"));
            student.getCoursesTaken().add(Course.CSCE247);
            student.setStreak(5);

            Editor editor = new Editor("testEditor", "editorPassword", new Date(4000000000L), "editor@test.com", "Editor");
            editor.getQuestionsMade().add(UUID.fromString("33333333-3333-3333-3333-333333333333"));

            Admin admin = new Admin("testAdmin", "adminPassword", new Date(5000000000L), "admin@test.com");
            admin.getQuestionsMade().add(UUID.fromString("44444444-4444-4444-4444-444444444444"));

            // Save those users and clear the question file because this test only cares about user list values.
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().add(student);
            UserList.getInstance().getUsers().add(editor);
            UserList.getInstance().getUsers().add(admin);
            UserList.getInstance().save();

            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().save();

            // Load the users back through DataLoader.
            ArrayList<User> loadedUsers = DataLoader.getUsers();

            // Restore the original json files.
            FileWriter restoreUsersWriter = new FileWriter(USER_FILE_NAME);
            restoreUsersWriter.write(originalUsersJSON);
            restoreUsersWriter.close();

            FileWriter restoreQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            restoreQuestionsWriter.write(originalQuestionsJSON);
            restoreQuestionsWriter.close();

            // Put the original file data back into the singleton lists.
            ArrayList<User> restoredUsers = DataLoader.getUsers();
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().addAll(restoredUsers);

            ArrayList<Question> restoredQuestions = DataLoader.getQuestions();
            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().addAll(restoredQuestions);

            // Check the actual values inside the loaded UUID and Course lists.
            assertTrue(loadedUsers.size() == 3);
            assertTrue(((Student) loadedUsers.get(0)).getQuestionsAnswered().get(0).equals(UUID.fromString("33333333-3333-3333-3333-333333333333")));
            assertTrue(((Student) loadedUsers.get(0)).getCoursesTaken().get(0) == Course.CSCE247);
            assertTrue(((Editor) loadedUsers.get(1)).getQuestionsMade().get(0).equals(UUID.fromString("33333333-3333-3333-3333-333333333333")));
            assertTrue(((Admin) loadedUsers.get(2)).getQuestionsMade().get(0).equals(UUID.fromString("44444444-4444-4444-4444-444444444444")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Test: Converting a comment to json should keep isQuestionAuthor
        Reasoning: Need to verify that DataWriter does not lose the comment author flag
    */
    @Test
    public void testCommentJSONKeepsIsQuestionAuthor() {
        // Build a reply that marks the author as the question author.
        ArrayList<CommentTag> replyTags = new ArrayList<CommentTag>();
        replyTags.add(CommentTag.DISCUSSION);
        Comment reply = new Comment("Reply Title", "This is a reply", null, replyTags, new ArrayList<Section>(), new ArrayList<Comment>(), 3.0, 1.0, true);

        // Convert it to json using DataWriter.
        String replyJSON = DataWriter.getCommentsJSON(reply).toJSONString();

        // Check that the flag is still present in the json text.
        assertTrue(replyJSON.contains("\"isQuestionAuthor\":true"));
    }

    /*
        Test: Converting a question to json should keep the interview time
        Reasoning: Need to verify that DataWriter does not lose the question time value
    */
    @Test
    public void testQuestionJSONKeepsTime() {
        // Build the enum lists needed for a real Question object.
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);

        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        // Create an interview question with a time limit.
        Question question = new Question(UUID.fromString("88888888-8888-8888-8888-888888888888"), "timeQuestion", "timeDescription", new ArrayList<Section>(), null, new ArrayList<Comment>(), null, 0.0, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, true, new ArrayList<QuestionTag>(), new ArrayList<String>(), 25);

        // Convert it to json using DataWriter.
        String questionJSON = DataWriter.getQuestionsJSON(question).toJSONString();

        // Check that the time value is still present in the json text.
        assertTrue(questionJSON.contains("\"time\":25"));
    }

    /*
        Test: Getting questions with invalid json
        Reasoning: Need to verify what DataLoader does when the question file is malformed
    */
    @Test
    public void testGetQuestionsBadJSON() {
        try {
            // Save the real file contents so the test can restore them later.
            String originalUsersJSON = "";
            String originalQuestionsJSON = "";
            FileReader userReader = new FileReader(USER_FILE_NAME);
            FileReader questionReader = new FileReader(QUESTION_FILE_NAME);
            int userCharacter = userReader.read();
            int questionCharacter = questionReader.read();

            while (userCharacter != -1) {
                originalUsersJSON += (char) userCharacter;
                userCharacter = userReader.read();
            }

            while (questionCharacter != -1) {
                originalQuestionsJSON += (char) questionCharacter;
                questionCharacter = questionReader.read();
            }

            userReader.close();
            questionReader.close();

            // Write a bad question file on purpose to trigger the error path in DataLoader.
            FileWriter badUsersWriter = new FileWriter(USER_FILE_NAME);
            badUsersWriter.write("[]");
            badUsersWriter.close();

            FileWriter badQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            badQuestionsWriter.write("{ bad json }");
            badQuestionsWriter.close();

            // Load the malformed question file.
            ArrayList<Question> loadedQuestions = DataLoader.getQuestions();

            // Restore the original json files.
            FileWriter restoreUsersWriter = new FileWriter(USER_FILE_NAME);
            restoreUsersWriter.write(originalUsersJSON);
            restoreUsersWriter.close();

            FileWriter restoreQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            restoreQuestionsWriter.write(originalQuestionsJSON);
            restoreQuestionsWriter.close();

            // Put the original file data back into the singleton lists.
            ArrayList<User> restoredUsers = DataLoader.getUsers();
            UserList.getInstance().getUsers().clear();
            UserList.getInstance().getUsers().addAll(restoredUsers);

            ArrayList<Question> restoredQuestions = DataLoader.getQuestions();
            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().addAll(restoredQuestions);

            // The current behavior is to return null when the question json is invalid.
            assertTrue(loadedQuestions == null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
