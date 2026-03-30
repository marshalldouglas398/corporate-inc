package com.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TestQuestionList {
    private static final String QUESTION_FILE_NAME = "json/questions.json";

    /*
        Test: Adding a question
        Reasoning: Need to verify that QuestionList actually stores a new question and keeps its data
    */
    @Test
    public void testAddQuestion() {
        // Save the current question list so the test can put it back when it is done.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build the data that addQuestion needs.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        hints.add("testHint2");
        hints.add("testHint3");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);
        disciplines.add(Discipline.COMPENG);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);
        courses.add(Course.CSCE240);

        // Clear the list so the test controls exactly what is inside it.
        QuestionList.getInstance().getQuestions().clear();

        // Add the question and save the result before restoring the original list.
        boolean wasAdded = QuestionList.getInstance().addQuestion("testTitle", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.MEDIUM, courses, "testDescription");
        Question addedQuestion = QuestionList.getInstance().getQuestions().get(0);

        // Put the original questions back so later tests start with the original data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check that the new question was added with the correct values.
        assertTrue(wasAdded);
        assertTrue(addedQuestion.getTitle().equals("testTitle"));
        assertTrue(addedQuestion.getAuthor().getUsername().equals("testEditor"));
        assertTrue(addedQuestion.getHints().size() == 3);
        assertTrue(addedQuestion.getHints().get(0).equals("testHint1"));
        assertTrue(addedQuestion.getHints().get(1).equals("testHint2"));
        assertTrue(addedQuestion.getHints().get(2).equals("testHint3"));
        assertTrue(addedQuestion.getType() == QuestionType.TECHNICAL);
        assertTrue(addedQuestion.getDiscipline().size() == 2);
        assertTrue(addedQuestion.getDiscipline().get(0) == Discipline.COMPSCI);
        assertTrue(addedQuestion.getDiscipline().get(1) == Discipline.COMPENG);
        assertTrue(addedQuestion.getDifficulty() == Difficulty.MEDIUM);
        assertTrue(addedQuestion.getCourse().size() == 2);
        assertTrue(addedQuestion.getCourse().get(0) == Course.CSCE247);
        assertTrue(addedQuestion.getCourse().get(1) == Course.CSCE240);
        assertTrue(addedQuestion.getDescription().equals("testDescription"));
    }

    /*
        Test: Getting a question that exists
        Reasoning: Need to verify that QuestionList can find a question by id
    */
    @Test
    public void testGetQuestionRand() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build two questions so the test can search for one of them.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        Question question1 = new Question("questionOne", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "descriptionOne");
        Question question2 = new Question("questionTwo", author, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "descriptionTwo");

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question1);
        QuestionList.getInstance().getQuestions().add(question2);

        // Look up the second question by id.
        Question loadedQuestion = QuestionList.getInstance().getQuestion(question2.getId());

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check that the correct question was found.
        assertTrue(loadedQuestion != null);
        assertTrue(loadedQuestion.getId().equals(question2.getId()));
        assertTrue(loadedQuestion.getTitle().equals("questionTwo"));
    }

    /*
        Test: Getting a question that does not exist
        Reasoning: Need to verify that QuestionList returns null when the id is missing
    */
    @Test
    public void testGetQuestionNone() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build one question so the search has data, but use a different id for the lookup.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        Question question = new Question("questionOne", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "descriptionOne");

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question);

        // Search for an id that is not in the list.
        Question loadedQuestion = QuestionList.getInstance().getQuestion(UUID.fromString("99999999-9999-9999-9999-999999999999"));

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check that QuestionList returned null for the missing id.
        assertTrue(loadedQuestion == null);
    }

    /*
        Test: Filtering with no filters
        Reasoning: Need to verify that QuestionList returns everything when no filter values are given
    */
    @Test
    public void testFilterQuestionNone() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build four questions so the test can check the first, middle, and last values in the filtered list.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        Question question1 = new Question("questionOne", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "descriptionOne");
        Question question2 = new Question("questionTwo", author, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "descriptionTwo");
        Question question3 = new Question("questionThree", author, hints, QuestionType.CONCEPTUAL, disciplines, Difficulty.MEDIUM, courses, "descriptionThree");
        Question question4 = new Question("questionFour", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.HARD, courses, "descriptionFour");

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question1);
        QuestionList.getInstance().getQuestions().add(question2);
        QuestionList.getInstance().getQuestions().add(question3);
        QuestionList.getInstance().getQuestions().add(question4);

        // Run the filter with all null values.
        ArrayList<Question> filteredQuestions = QuestionList.getInstance().filterQuestion(QuestionList.getInstance().getQuestions(), null, null, null, null, null);

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check the size and the order of the returned list.
        assertTrue(filteredQuestions.size() == 4);
        assertTrue(filteredQuestions.get(0).getTitle().equals("questionOne"));
        assertTrue(filteredQuestions.get(1).getTitle().equals("questionTwo"));
        assertTrue(filteredQuestions.get(2).getTitle().equals("questionThree"));
        assertTrue(filteredQuestions.get(3).getTitle().equals("questionFour"));
    }

    /*
        Test: Filtering by question type
        Reasoning: Need to verify that QuestionList filters question types correctly
    */
    @Test
    public void testFilterQuestionType() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build four questions so the test can check the first and last technical matches.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        Question question1 = new Question("technicalQuestionOne", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "descriptionOne");
        Question question2 = new Question("behavioralQuestionOne", author, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "descriptionTwo");
        Question question3 = new Question("technicalQuestionTwo", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.MEDIUM, courses, "descriptionThree");
        Question question4 = new Question("conceptualQuestion", author, hints, QuestionType.CONCEPTUAL, disciplines, Difficulty.EASY, courses, "descriptionFour");

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question1);
        QuestionList.getInstance().getQuestions().add(question2);
        QuestionList.getInstance().getQuestions().add(question3);
        QuestionList.getInstance().getQuestions().add(question4);

        // Filter for only technical questions.
        ArrayList<Question> filteredQuestions = QuestionList.getInstance().filterQuestion(QuestionList.getInstance().getQuestions(), QuestionType.TECHNICAL, null, null, null, null);

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check the size and the order of the technical questions that were returned.
        assertTrue(filteredQuestions.size() == 2);
        assertTrue(filteredQuestions.get(0).getTitle().equals("technicalQuestionOne"));
        assertTrue(filteredQuestions.get(1).getTitle().equals("technicalQuestionTwo"));
    }

    /*
        Test: Filtering by difficulty
        Reasoning: Need to verify that QuestionList filters difficulty correctly
    */
    @Test
    public void testFilterQuestionDifficulty() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build four questions so the test can check the first and last hard matches.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        Question question1 = new Question("easyQuestion", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "descriptionOne");
        Question question2 = new Question("hardQuestionOne", author, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "descriptionTwo");
        Question question3 = new Question("mediumQuestion", author, hints, QuestionType.CONCEPTUAL, disciplines, Difficulty.MEDIUM, courses, "descriptionThree");
        Question question4 = new Question("hardQuestionTwo", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.HARD, courses, "descriptionFour");

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question1);
        QuestionList.getInstance().getQuestions().add(question2);
        QuestionList.getInstance().getQuestions().add(question3);
        QuestionList.getInstance().getQuestions().add(question4);

        // Filter for only hard questions.
        ArrayList<Question> filteredQuestions = QuestionList.getInstance().filterQuestion(QuestionList.getInstance().getQuestions(), null, null, Difficulty.HARD, null, null);

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check the size and the order of the hard questions that were returned.
        assertTrue(filteredQuestions.size() == 2);
        assertTrue(filteredQuestions.get(0).getTitle().equals("hardQuestionOne"));
        assertTrue(filteredQuestions.get(1).getTitle().equals("hardQuestionTwo"));
    }

    /*
        Test: Filtering by discipline
        Reasoning: Need to verify that QuestionList filters discipline correctly
    */
    @Test
    public void testFilterQuestionDiscipline() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build four questions so the test can check the first and last computer science matches.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");

        ArrayList<Discipline> disciplines1 = new ArrayList<Discipline>();
        disciplines1.add(Discipline.COMPSCI);
        ArrayList<Discipline> disciplines2 = new ArrayList<Discipline>();
        disciplines2.add(Discipline.COMPENG);

        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        Question question1 = new Question("compsciQuestionOne", author, hints, QuestionType.TECHNICAL, disciplines1, Difficulty.EASY, courses, "descriptionOne");
        Question question2 = new Question("compengQuestionOne", author, hints, QuestionType.BEHAVIORAL, disciplines2, Difficulty.HARD, courses, "descriptionTwo");
        Question question3 = new Question("compsciQuestionTwo", author, hints, QuestionType.CONCEPTUAL, disciplines1, Difficulty.MEDIUM, courses, "descriptionThree");
        Question question4 = new Question("compengQuestionTwo", author, hints, QuestionType.TECHNICAL, disciplines2, Difficulty.EASY, courses, "descriptionFour");

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question1);
        QuestionList.getInstance().getQuestions().add(question2);
        QuestionList.getInstance().getQuestions().add(question3);
        QuestionList.getInstance().getQuestions().add(question4);

        // Filter for only computer science questions.
        ArrayList<Question> filteredQuestions = QuestionList.getInstance().filterQuestion(QuestionList.getInstance().getQuestions(), null, Discipline.COMPSCI, null, null, null);

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check the size and the order of the computer science questions that were returned.
        assertTrue(filteredQuestions.size() == 2);
        assertTrue(filteredQuestions.get(0).getTitle().equals("compsciQuestionOne"));
        assertTrue(filteredQuestions.get(1).getTitle().equals("compsciQuestionTwo"));
    }

    /*
        Test: Filtering by course
        Reasoning: Need to verify that QuestionList filters course correctly
    */
    @Test
    public void testFilterQuestionCourse() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build four questions so the test can check the first and last CSCE247 matches.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);

        ArrayList<Course> courses1 = new ArrayList<Course>();
        courses1.add(Course.CSCE247);
        ArrayList<Course> courses2 = new ArrayList<Course>();
        courses2.add(Course.CSCE240);

        Question question1 = new Question("course247QuestionOne", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses1, "descriptionOne");
        Question question2 = new Question("course240QuestionOne", author, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses2, "descriptionTwo");
        Question question3 = new Question("course247QuestionTwo", author, hints, QuestionType.CONCEPTUAL, disciplines, Difficulty.MEDIUM, courses1, "descriptionThree");
        Question question4 = new Question("course240QuestionTwo", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses2, "descriptionFour");

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question1);
        QuestionList.getInstance().getQuestions().add(question2);
        QuestionList.getInstance().getQuestions().add(question3);
        QuestionList.getInstance().getQuestions().add(question4);

        // Filter for only CSCE247 questions.
        ArrayList<Question> filteredQuestions = QuestionList.getInstance().filterQuestion(QuestionList.getInstance().getQuestions(), null, null, null, Course.CSCE247, null);

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check the size and the order of the CSCE247 questions that were returned.
        assertTrue(filteredQuestions.size() == 2);
        assertTrue(filteredQuestions.get(0).getTitle().equals("course247QuestionOne"));
        assertTrue(filteredQuestions.get(1).getTitle().equals("course247QuestionTwo"));
    }

    /*
        Test: Filtering by tag
        Reasoning: Need to verify that QuestionList filters tags correctly
    */
    @Test
    public void testFilterQuestionTag() {
        // Save the current question list so the test can restore it later.
        ArrayList<Question> originalQuestions = new ArrayList<Question>();
        for (Question question : QuestionList.getInstance().getQuestions()) {
            originalQuestions.add(question);
        }

        // Build four questions so the test can check the first and last write code matches.
        Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
        ArrayList<String> hints = new ArrayList<String>();
        hints.add("testHint1");
        ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
        disciplines.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(Course.CSCE247);

        Question question1 = new Question("writeCodeQuestionOne", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "descriptionOne");
        question1.getTag().add(QuestionTag.WRITE_CODE);
        Question question2 = new Question("situationQuestionOne", author, hints, QuestionType.BEHAVIORAL, disciplines, Difficulty.HARD, courses, "descriptionTwo");
        question2.getTag().add(QuestionTag.SITUATION);
        Question question3 = new Question("writeCodeQuestionTwo", author, hints, QuestionType.CONCEPTUAL, disciplines, Difficulty.MEDIUM, courses, "descriptionThree");
        question3.getTag().add(QuestionTag.WRITE_CODE);
        Question question4 = new Question("situationQuestionTwo", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.EASY, courses, "descriptionFour");
        question4.getTag().add(QuestionTag.SITUATION);

        // Replace the shared list with only the test data.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().add(question1);
        QuestionList.getInstance().getQuestions().add(question2);
        QuestionList.getInstance().getQuestions().add(question3);
        QuestionList.getInstance().getQuestions().add(question4);

        // Filter for only write code questions.
        ArrayList<Question> filteredQuestions = QuestionList.getInstance().filterQuestion(QuestionList.getInstance().getQuestions(), null, null, null, null, QuestionTag.WRITE_CODE);

        // Restore the original list before the assertions run.
        QuestionList.getInstance().getQuestions().clear();
        QuestionList.getInstance().getQuestions().addAll(originalQuestions);

        // Check the size and the order of the write code questions that were returned.
        assertTrue(filteredQuestions.size() == 2);
        assertTrue(filteredQuestions.get(0).getTitle().equals("writeCodeQuestionOne"));
        assertTrue(filteredQuestions.get(1).getTitle().equals("writeCodeQuestionTwo"));
    }

    /*
        Test: Saving the question list
        Reasoning: Need to verify that QuestionList save actually writes the current questions to the json file
    */
    @Test
    public void testSave() {
        try {
            // Save the current question list and the current json file text so the test can restore them later.
            ArrayList<Question> originalQuestions = new ArrayList<Question>();
            for (Question question : QuestionList.getInstance().getQuestions()) {
                originalQuestions.add(question);
            }

            String originalQuestionsJSON = "";
            FileReader questionReader = new FileReader(QUESTION_FILE_NAME);
            int questionCharacter = questionReader.read();

            while (questionCharacter != -1) {
                originalQuestionsJSON += (char) questionCharacter;
                questionCharacter = questionReader.read();
            }

            questionReader.close();

            // Build one question and save it through QuestionList.
            Editor author = new Editor("testEditor", "editorPassword", new Date(1000000000L), "editor@test.com", "Editor");
            ArrayList<String> hints = new ArrayList<String>();
            hints.add("testHint1");
            ArrayList<Discipline> disciplines = new ArrayList<Discipline>();
            disciplines.add(Discipline.COMPSCI);
            ArrayList<Course> courses = new ArrayList<Course>();
            courses.add(Course.CSCE247);

            Question question = new Question("saveQuestion", author, hints, QuestionType.TECHNICAL, disciplines, Difficulty.MEDIUM, courses, "saveDescription");

            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().add(question);

            boolean wasSaved = QuestionList.getInstance().save();

            // Read the saved json file to see what QuestionList wrote.
            String savedQuestionsJSON = "";
            FileReader savedQuestionReader = new FileReader(QUESTION_FILE_NAME);
            int savedQuestionCharacter = savedQuestionReader.read();

            while (savedQuestionCharacter != -1) {
                savedQuestionsJSON += (char) savedQuestionCharacter;
                savedQuestionCharacter = savedQuestionReader.read();
            }

            savedQuestionReader.close();

            // Restore the original json file and the original question list.
            FileWriter restoreQuestionsWriter = new FileWriter(QUESTION_FILE_NAME);
            restoreQuestionsWriter.write(originalQuestionsJSON);
            restoreQuestionsWriter.close();

            QuestionList.getInstance().getQuestions().clear();
            QuestionList.getInstance().getQuestions().addAll(originalQuestions);

            // Check that save returned true and that the saved json contains the question title.
            assertTrue(wasSaved);
            assertTrue(savedQuestionsJSON.contains("saveQuestion"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
