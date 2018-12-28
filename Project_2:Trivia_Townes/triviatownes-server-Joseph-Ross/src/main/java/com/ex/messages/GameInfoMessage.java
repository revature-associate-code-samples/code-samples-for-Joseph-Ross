package com.ex.messages;

import java.util.ArrayList;

import com.ex.game.GameSessionBean;
import com.ex.game.PlayerBean;
import com.ex.game.QuestionBean;

/**
 * Author: Joseph Ross
 * GameInfoMessage
 * -> All the info the client need about the current game state
 * */
public class GameInfoMessage {
	
	// Data fields that store similar data fields in the game session
	private StringBuffer category;
	private int players;
	private int maxPlayers;
	private StringBuffer name;
	private StringBuffer scope;
	private StringBuffer difficulty;
	private StringBuffer key;
	private QuestionBean currentQuestion;
	private int numberOfAnswers;
	private long currentCountDown;
	private ArrayList<PlayerBean> topScores;
	private int numberOfQuestions;
	private int currentQuestionNumber;
	public int status;
	
	// Save the information the client needs from the game sesson object
	public GameInfoMessage(GameSessionBean game) {

		this.category = game.category;
		this.players = game.currentPlayers.size();
		this.maxPlayers = game.maxPlayers;
		this.name = game.name;
		this.scope = game.scope;
		this.difficulty = game.difficulty;
		this.key = game.joinKey;
		this.currentQuestion = game.currentQuestion;
		this.numberOfAnswers = game.currentAnswerCounter;
		this.currentCountDown = game.currentTime;
		this.numberOfQuestions = game.numberOfQuestions;
		this.currentQuestionNumber = game.currentQuestionIndex + 1;
		this.status = game.state;
	}
	
	public GameInfoMessage() {
		
	}
	
	/**
	 * The rest of the methods are getters and setters
	 * */
	public synchronized QuestionBean getCurrentQuestion() {
		return currentQuestion;
	}

	public synchronized void setCurrentQuestion(QuestionBean currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public synchronized int getNumberOfAnswers() {
		return numberOfAnswers;
	}

	public synchronized void setNumberOfAnswers(int numberOfAnswers) {
		this.numberOfAnswers = numberOfAnswers;
	}

	public synchronized long getCurrentCountDown() {
		return currentCountDown;
	}

	public synchronized void setCurrentCountDown(long currentCountDown) {
		this.currentCountDown = currentCountDown;
	}

	public synchronized ArrayList<PlayerBean> getTopScores() {
		return topScores;
	}

	public synchronized void setTopScores(ArrayList<PlayerBean> topScores) {
		this.topScores = topScores;
	}

	public synchronized int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public synchronized void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public synchronized int getCurrentQuestionNumber() {
		return currentQuestionNumber;
	}

	public synchronized void setCurrentQuestionNumber(int currentQuestionNumber) {
		this.currentQuestionNumber = currentQuestionNumber;
	}
	
	public StringBuffer getKey() {
		return key;
	}
	public void setKey(StringBuffer key) {
		this.key = key;
	}
	public synchronized StringBuffer getDifficulty() {
		return difficulty;
	}
	public synchronized void setDifficulty(StringBuffer difficulty) {
		this.difficulty = difficulty;
	}
	public synchronized StringBuffer getCategory() {
		return category;
	}
	public synchronized void setCategory(StringBuffer category) {
		this.category = category;
	}
	public synchronized int getPlayers() {
		return players;
	}
	public synchronized void setPlayers(int players) {
		this.players = players;
	}
	public synchronized int getMaxPlayers() {
		return maxPlayers;
	}
	public synchronized void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public synchronized StringBuffer getName() {
		return name;
	}
	public synchronized void setName(StringBuffer name) {
		this.name = name;
	}
	public synchronized StringBuffer getScope() {
		return scope;
	}
	public synchronized void setScope(StringBuffer scope) {
		this.scope = scope;
	}

}
