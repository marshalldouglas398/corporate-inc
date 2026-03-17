package com.model;

import java.util.ArrayList;
import java.util.UUID;
/**
 * Class that represents a list of all questions
 */

public class QuestionList {
	private static QuestionList questionList;
	private ArrayList<Question> questions;

	private QuestionList() {
		this.questions = new ArrayList<>();
	}
/**
 * Gets the instance of the QuestionList class
 * @return The instance of the QuestionList class
 */
	public static QuestionList getInstance() {
		if (questionList == null) {
			questionList = new QuestionList();
			questionList.questions = DataLoader.getQuestions();
		}
		return questionList;
	}
	/**
	 * Filters the list of questions based on the given parameters
	 * @param allQuestions the list of all questions to filter from
	 * @param qtype the type of question to filter by
	 * @param d the discipline to filter by
	 * @param diff the difficulty to filter by
	 * @param c the course to filter by
	 * @param qt the question tag to filter by
	 * @return a list of questions that match the given parameters
	 */
	public ArrayList<Question> filterQuestion(ArrayList<Question> allQuestions, QuestionType qtype, Discipline d, Difficulty diff, Course c, QuestionTag qt) {
		ArrayList<Question> filteredQuestions = new ArrayList<>();
		for (Question question : allQuestions) {
			boolean matches = true;

			if (qtype != null && !question.getType().equals(qtype)) {
				matches = false;
			}
			if (d != null && !question.getDiscipline().equals(d)) {
				matches = false;
			}
			if (diff != null && !question.getDifficulty().equals(diff)) {
				matches = false;
			}
			if (c != null && !question.getCourse().equals(c)) {
				matches = false;
			}
			if (qt != null && !question.getTag().equals(qt)) {
				matches = false;
			}

			if (matches) {
				filteredQuestions.add(question);
			}
		}
		return filteredQuestions;
	}
	/**
	 * Adds a question to the list of questions
	 * @param title the title of the question
	 * @param author the author of the queston
	 * @param hints the hints for the question
	 * @param type the type of the question
	 * @param discipline the discipline of the question
	 * @param difficulty the difficulty of the question
	 * @param course the course of the question
	 * @return true if the question was added successfully
	 */
	public boolean addQuestion(String title, User author, ArrayList<String> hints, QuestionType type, ArrayList<Discipline> discipline, Difficulty difficulty, ArrayList<Course> course, int numSections) {
		Question question = new Question(title, author, hints, type, discipline, difficulty, course, numSections);
		questions.add(question);
		return true;
	}
	/**
	 * Searches for a question in the list of questions based on the given id
	 * @param id the id of the question to search for
	 * @return the question if found, null otherwise
	 */
	public Question getQuestion(UUID id) {
		for (Question question : questions) {
			if (question.getId().equals(id)) {
				return question;
			}
		}
		return null;
	}
/**
 * Saves the list of questions to the data file
 * @return true if the save was successful, false otherwise
 */
	public boolean save() {
		return DataWriter.saveQuestions();
	}
/**
 * Gets the list of questions
 * @return the list of questions
 */
	public ArrayList<Question> getQuestions() {
		return this.questions;
	}
}