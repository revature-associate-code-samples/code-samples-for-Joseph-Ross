package com.ex.game;

import org.apache.log4j.Logger;

/**
 * Author: Joseph Ross
 * PlayerBean
 * -> This object acts as an instance of a player connected to a game
 * */
public class PlayerBean {
	
	private static Logger logger = Logger.getLogger(PlayerBean.class);
	
	//Thread safe username
	private StringBuffer username;
	
	// Unique player id
	private int playerId;
	
	private int currentAnswer;
	
	//the order in which the user answered the question
	private int currentAnswerPosition;
	
	private int score;
	
	// Max number of questions they got right in a row
	private int maxStreak;
	
	private int rightAnswers;
	
	private int wrongAnswers;
	
	// Used to track if the player is connected to the active game
	private Boolean connected = false;
	
	// Current number of questions they got right in a row
	private int currentStreak;
	
	/**
	 * The rest of the methods are getters and setters
	 * */
	public synchronized int getPlayerId() {
		return playerId;
	}

	public synchronized void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public synchronized Boolean isConnected() {
		return this.connected;
	}
	
	public synchronized void setConnected() {
		this.connected = true;
	}
	
	public synchronized int getCurrentStreak() {
		return currentStreak;
	}

	public synchronized void setCurrentStreak(int currentStreak) {
		this.currentStreak = currentStreak;
	}

	public synchronized StringBuffer getUsername() {
		return username;
	}

	public synchronized void setUsername(StringBuffer username) {
		this.username = username;
	}

	public synchronized int getCurrentAnswer() {
		return currentAnswer;
	}

	public synchronized void setCurrentAnswer(int currentAnswer) {
		this.currentAnswer = currentAnswer;
	}

	public synchronized int getCurrentAnswerPosition() {
		return currentAnswerPosition;
	}

	public synchronized void setCurrentAnswerPosition(int currentAnswerPosition) {
		this.currentAnswerPosition = currentAnswerPosition;
	}

	public synchronized int getScore() {
		return score;
	}

	public synchronized void setScore(int score) {
		this.score = score;
	}

	public synchronized int getMaxStreak() {
		return maxStreak;
	}

	public synchronized void setMaxStreak(int maxStreak) {
		this.maxStreak = maxStreak;
	}

	public synchronized int getRightAnswers() {
		return rightAnswers;
	}

	public synchronized void setRightAnswers(int rightAnswers) {
		this.rightAnswers = rightAnswers;
	}

	public synchronized int getWrongAnswers() {
		return wrongAnswers;
	}

	public synchronized void setWrongAnswers(int wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}
}
