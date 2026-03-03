package com.model;

import java.util.ArrayList;
import java.util.UUID;


public class QuestionList {
	private static QuestionList questionList;
	private ArrayList<Question> questions;

	private QuestionList() {
		this.questions = new ArrayList<>();
	}

	public static QuestionList getInstance() {
		if (questionList == null) {
			questionList = new QuestionList();
		}
		return questionList;
	}
	
	public ArrayList<Question> filterQuestion(ArrayList<Question> allQuestions, QuestionType qtype, Discipline d, Difficulty diff, Course c, QuestionTag qt) {
		ArrayList<Question> filteredQuestions = new ArrayList<>();
		for (Question question : allQuestions) {
			if (question.getType().equals(qtype) && question.getDiscipline().equals(d) && question.getDifficulty().equals(diff) && question.getCourse().equals(c) && question.getTag().equals(qt)) {
				filteredQuestions.add(question);
			}
		}
		return filteredQuestions;
	}
	
	public boolean addQuestion(String title, User author, ArrayList<String> hints, QuestionType type, ArrayList<Discipline> discipline, Difficulty difficulty, ArrayList<Course> course) {
		Question question = new Question(title, author, hints, type, discipline, difficulty, course);
		questions.add(question);
		return true;
	}
	
	public Question getQuestion(UUID id) {
		for (Question question : questions) {
			if (question.getId().equals(id)) {
				return question;
			}
		}
		return null;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public boolean save() {
		// to do
		return true;
	}
}