package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DataWriterTest {
    /*
    +---------------------------+------------------------------------------------+
    | Test                      | Reasoning                                      |
    +---------------------------+------------------------------------------------+
    | testStudentJson           | Student role serializes all expected keys      |
    | testAdminJson             | Admin branch adds questionsMade                |
    | testEditorJson            | Editor branch adds questionsMade               |
    | testSectionNoFile         | Null section file becomes JSON null            |
    | testSectionWithFile       | Non-null file uses File.toString()             |
    | testNullComment           | Null comment is handled correctly              |
    | testCommentReply          | Replies recurse through getCommentsJSON        |
    | testQuestionJson          | Question maps enums lists author id hints      |
    | testQuestionNoAuthor      | Missing author becomes JSON null               |
    | testSaveUsers             | saveUsers writes array user record to temp file|
    | testSaveQuestions         | saveQuestions writes array to temp file        |
    +---------------------------+------------------------------------------------+
     */
    private static final UUID ALICE_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID BOB_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final UUID EVE_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    private static final UUID QID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    private static final Date FIXED_DATE = new Date(0L);

    private static String originalUserFileName;
    private static String originalQuestionFileName;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void captureDataConstantPaths() throws Exception {
        originalUserFileName = readStaticStringField(DataConstants.class, "USER_FILE_NAME");
        originalQuestionFileName = readStaticStringField(DataConstants.class, "QUESTION_FILE_NAME");
    }

    @After
    public void restoreGlobalState() throws Exception {
        setStaticStringField(DataConstants.class, "USER_FILE_NAME", originalUserFileName);
        setStaticStringField(DataConstants.class, "QUESTION_FILE_NAME", originalQuestionFileName);
        Field userSingleton = UserList.class.getDeclaredField("userlist");
        userSingleton.setAccessible(true);
        userSingleton.set(null, null);
        Field questionSingleton = QuestionList.class.getDeclaredField("questionList");
        questionSingleton.setAccessible(true);
        questionSingleton.set(null, null);
    }

    private static String readStaticStringField(Class<?> clazz, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return (String) field.get(null);
    }

    private static void setStaticStringField(Class<?> clazz, String name, String value) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(null, value);
    }

    @Test
    public void testStudentJson() {
        ArrayList<UUID> solved = new ArrayList<>();
        solved.add(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE145);
        Student student = new Student(ALICE_ID, "alice", "P@ss123", FIXED_DATE, "alice@corp.com",
                "U00000001", "Computer Science", solved, courses, 7);
        JSONObject json = DataWriter.getUserJSON(student);
        assertEquals("alice", json.get("username"));
        assertEquals("P@ss123", json.get("password"));
        assertEquals(student.getBirthDate(), json.get("dateOfBirth"));
        assertEquals(ALICE_ID.toString(), json.get("id"));
        assertEquals("alice@corp.com", json.get("email"));
        assertEquals("Student", json.get("role"));
        assertNotNull(json.get("questionsSolved"));
        assertNotNull(json.get("coursesTaken"));
        assertEquals("U00000001", json.get("uscID"));
        assertEquals("Computer Science", json.get("major"));
        assertEquals(7L, ((Number) json.get("streak")).longValue());
    }

    @Test
    public void testAdminJson() {
        ArrayList<UUID> made = new ArrayList<>();
        made.add(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));
        Admin admin = new Admin(BOB_ID, "bob", "Secure9", FIXED_DATE, "bob@corp.com", made);
        JSONObject json = DataWriter.getUserJSON(admin);
        assertEquals("bob", json.get("username"));
        assertEquals("Admin", json.get("role"));
        assertNotNull(json.get("questionsMade"));
    }

    @Test
    public void testEditorJson() {
        ArrayList<UUID> made = new ArrayList<>();
        made.add(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"));
        Editor editor = new Editor(EVE_ID, "eve", "editPw", FIXED_DATE, "eve@corp.com", "Editor", made);
        JSONObject json = DataWriter.getUserJSON(editor);
        assertEquals("eve", json.get("username"));
        assertEquals("Editor", json.get("role"));
        assertNotNull(json.get("questionsMade"));
    }

    @Test
    public void testSectionNoFile() {
        Section section = new Section("sTitle", "sDesc", null, "code1");
        JSONObject json = DataWriter.getSectionsJSON(section);
        assertEquals("sTitle", json.get("title"));
        assertEquals("sDesc", json.get("description"));
        assertNull(json.get("file"));
        assertEquals("code1", json.get("code"));
    }

    @Test
    public void testSectionWithFile() {
        File f = new File("snippet.java");
        Section section = new Section("t", "d", f, "c");
        JSONObject json = DataWriter.getSectionsJSON(section);
        assertEquals(f.toString(), json.get("file"));
    }

    @Test
    public void testNullComment() {
        assertNull(DataWriter.getCommentsJSON(null));
    }

    @Test
    public void testCommentReply() {
        User author = new User(ALICE_ID, "alice", "pw", FIXED_DATE, "a@x.com", "Student");
        ArrayList<CommentTag> tags = new ArrayList<>();
        tags.add(CommentTag.DISCUSSION);
        ArrayList<Section> sections = new ArrayList<>();
        sections.add(new Section("st", "sd", null, "sc"));
        Comment reply = new Comment("rTitle", "rBody", author, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), 2.0, 1.0, false);
        ArrayList<Comment> replies = new ArrayList<>();
        replies.add(reply);
        Comment root = new Comment("root", "text", author, tags, sections, replies, 4.5, 2.0, true);
        JSONObject json = DataWriter.getCommentsJSON(root);
        assertEquals("root", json.get("title"));
        assertEquals("text", json.get("comment"));
        assertEquals(ALICE_ID.toString(), json.get("author"));
        assertEquals(4.5, ((Number) json.get("rating")).doubleValue(), 0.0);
        assertNotNull(json.get("tags"));
        assertNotNull(json.get("sections"));
        JSONArray jsonReplies = (JSONArray) json.get("replies");
        assertNotNull(jsonReplies);
        assertEquals(1, jsonReplies.size());
        JSONObject nested = (JSONObject) jsonReplies.get(0);
        assertEquals("rTitle", nested.get("title"));
    }

    @Test
    public void testQuestionJson() {
        User author = new User(ALICE_ID, "alice", "pw", FIXED_DATE, "a@x.com", "Student");
        ArrayList<Section> sections = new ArrayList<>();
        sections.add(new Section("sec", "desc", null, "x++"));
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("c1", "body", author, new ArrayList<>(), new ArrayList<>(), false));
        ArrayList<Discipline> disc = new ArrayList<>();
        disc.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE145);
        ArrayList<QuestionTag> qtags = new ArrayList<>();
        qtags.add(QuestionTag.WRITE_CODE);
        ArrayList<String> hints = new ArrayList<>();
        hints.add("think");
        Question q = new Question(QID, "Q title", "Q desc", sections, author, comments, 3.5, 2.0,
                QuestionType.TECHNICAL, disc, Difficulty.EASY, courses, true, qtags, hints, 0);
        JSONObject json = DataWriter.getQuestionsJSON(q);
        assertEquals("Q title", json.get("title"));
        assertEquals("Q desc", json.get("description"));
        assertEquals(QID.toString(), json.get("id"));
        assertEquals(ALICE_ID.toString(), json.get("author"));
        assertEquals(3.5, ((Number) json.get("rating")).doubleValue(), 0.0);
        assertEquals("TECHNICAL", json.get("type"));
        assertEquals("EASY", json.get("difficulty"));
        assertEquals(Boolean.TRUE, json.get("isInterviewMode"));
        assertNotNull(json.get("sections"));
        assertNotNull(json.get("comments"));
        assertNotNull(json.get("discipline"));
        assertNotNull(json.get("course"));
        assertNotNull(json.get("tags"));
        assertNotNull(json.get("hints"));
    }

    @Test
    public void testQuestionNoAuthor() {
        ArrayList<Section> sections = new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>();
        ArrayList<Discipline> disc = new ArrayList<>();
        disc.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE145);
        ArrayList<QuestionTag> qtags = new ArrayList<>();
        ArrayList<String> hints = new ArrayList<>();
        Question q = new Question(QID, "T", "D", sections, null, comments, null, 0.0,
                QuestionType.BEHAVIORAL, disc, Difficulty.MEDIUM, courses, false, qtags, hints, -1);
        JSONObject json = DataWriter.getQuestionsJSON(q);
        assertNull(json.get("author"));
        assertEquals("BEHAVIORAL", json.get("type"));
        assertEquals("MEDIUM", json.get("difficulty"));
    }

    @Test
    public void testSaveUsers() throws Exception {
        File out = tempFolder.newFile("users.json");
        setStaticStringField(DataConstants.class, "USER_FILE_NAME", out.getAbsolutePath());
        ArrayList<User> users = new ArrayList<>();
        users.add(new Student(ALICE_ID, "alice", "P@ss123", FIXED_DATE, "alice@corp.com",
                "U00000001", "Computer Science", new ArrayList<>(), new ArrayList<>(), 0));
        createIsolatedUserList(users);
        assertTrue(DataWriter.saveUsers());
        JSONParser parser = new JSONParser();
        JSONArray arr = (JSONArray) parser.parse(new FileReader(out));
        assertEquals(1, arr.size());
        JSONObject first = (JSONObject) arr.get(0);
        assertEquals("alice", first.get("username"));
    }

    @Test
    public void testSaveQuestions() throws Exception {
        File out = tempFolder.newFile("questions.json");
        setStaticStringField(DataConstants.class, "QUESTION_FILE_NAME", out.getAbsolutePath());
        User author = new User(ALICE_ID, "alice", "pw", FIXED_DATE, "a@x.com", "Student");
        ArrayList<Discipline> disc = new ArrayList<>();
        disc.add(Discipline.COMPSCI);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE146);
        ArrayList<QuestionTag> qtags = new ArrayList<>();
        Question q = new Question(QID, "Saved Q", "Saved body", new ArrayList<>(), author, new ArrayList<>(),
                null, 0.0, QuestionType.CONCEPTUAL, disc, Difficulty.HARD, courses, false, qtags,
                new ArrayList<>(), 0);
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(q);
        createIsolatedQuestionList(questions);
        assertTrue(DataWriter.saveQuestions());
        JSONParser parser = new JSONParser();
        JSONArray arr = (JSONArray) parser.parse(new FileReader(out));
        assertEquals(1, arr.size());
        JSONObject first = (JSONObject) arr.get(0);
        assertEquals("Saved Q", first.get("title"));
    }

    private UserList createIsolatedUserList(ArrayList<User> users) throws Exception {
        Constructor<UserList> constructor = UserList.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        UserList isolated = constructor.newInstance();
        Field usersField = UserList.class.getDeclaredField("users");
        usersField.setAccessible(true);
        usersField.set(isolated, users);
        Field singletonField = UserList.class.getDeclaredField("userlist");
        singletonField.setAccessible(true);
        singletonField.set(null, isolated);
        return isolated;
    }

    private QuestionList createIsolatedQuestionList(ArrayList<Question> questions) throws Exception {
        Constructor<QuestionList> constructor = QuestionList.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        QuestionList isolated = constructor.newInstance();
        Field listField = QuestionList.class.getDeclaredField("questions");
        listField.setAccessible(true);
        listField.set(isolated, questions);
        Field singletonField = QuestionList.class.getDeclaredField("questionList");
        singletonField.setAccessible(true);
        singletonField.set(null, isolated);
        return isolated;
    }
}
