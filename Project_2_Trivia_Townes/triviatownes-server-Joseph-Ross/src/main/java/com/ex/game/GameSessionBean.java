package com.ex.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;

import com.ex.controllers.StartGameController;
import com.ex.data.HighScorePlayerBean;
import com.ex.services.LeaderboardService;
import com.ex.services.OpenTDBService;

/**
 * Author: Joseph Ross
 * GameSessionBean
 * -> This object acts as the actual game instance on the server
 * */
public class GameSessionBean {
	
	private static Logger logger = Logger.getLogger(StartGameController.class);
	
	public QuestionBean currentQuestion;
	
	public int currentAnswers;
	
	public int currentQuestionIndex;

	// Used for game timer
	public long startTime;
	public long currentTime;
	
	public int instanceId;
	
	public StringBuffer joinKey;
	
	public int numberOfQuestions;
	
	//0 = Waiting, 1 = Playing game, 2 = End of Game, 3 = Loading next question
	public int state;
		
	public StringBuffer category;
	
	public StringBuffer difficulty;
	
	public StringBuffer name;
	
	public StringBuffer scope;
	
	//Game round time count down start
	long roundTime = 20;
	
	//Loading question time count down start
	long waitTime = 2;
	
	//Player id counter
	public int count = 50;

	public ArrayList<PlayerBean> currentPlayers;
	
	public int maxPlayers;
	
	// Tracks how many people have answered during a round
	public int currentAnswerCounter;
	
	public ArrayList<QuestionBean> Questions;
	
	/**
	 * Starts the game by getting questions from API service
	 * Starts the 20 second round countdown
	 * */
	public synchronized void startGame() {
		
		//Store questions
		this.Questions = OpenTDBService.getQuestions(this.numberOfQuestions, this.category.toString().toLowerCase());
		
		logger.trace(Questions);
		logger.trace(this.numberOfQuestions);
		logger.trace(this.category.toString());
		
		//Set the currentQuestion
		this.currentQuestion = this.Questions.get(this.currentQuestionIndex);
		
		//Cut off more players from joining
		this.maxPlayers = this.currentPlayers.size();
		
		// Reset timer to 20 seconds,
		// This happens last to prevent processing time to take up game time
		this.resetTimer();
	}
	
	/*
	 * This method is called every 0.5 seconds
	 * It updates the state of the game
	 * After this is called the clients are sent the updated game state
	 * */
	public synchronized void updateGame() {
		
		//If the timer ran out or all the players answered load the next question
		if(this.getCurrentTime() == 0 || this.getCurrentPlayers().size() == this.getCurrentAnswerCounter()) {
			
			//If game is over
			if(this.Questions.size() == this.currentQuestionIndex + 1) {
				
				//End game state
				this.state = 2;
				
				//Add all the player scores to the database
				LeaderboardService ls = new LeaderboardService();
				for (PlayerBean pb : currentPlayers)
				{
					HighScorePlayerBean hb = new HighScorePlayerBean(pb.getUsername().toString(),pb.getScore(),pb.getMaxStreak(),pb.getRightAnswers());
					ls.save(hb);
				}
				
				return;
			}
			
			//If the current round is over start the 2 second countdown
			if(this.state == 1) {

				// The loading new question state
				this.state = 3;
				
				// Reset the answer timer
				this.currentAnswerCounter = 0;
				
				//Set the current timer to 2 seconds;
				this.setStartTime(System.currentTimeMillis());
				long now = System.currentTimeMillis();
				this.setCurrentTime(this.waitTime - ((now - this.getStartTime())/1000));
			} else {
				
			//Else set the game in the playing state, load the new question, and set the timer to 20 seconds
				this.state = 1;
				logger.trace("else");

			//Get the new question
			this.loadNewQuestion();
			
			//Reset timer to 20 seconds
			this.resetTimer();
			}
		}
		
		//Game is still going on, update time
		//Set current time
		long now = System.currentTimeMillis();
		if(this.state == 3) {
			this.setCurrentTime(this.waitTime - ((now - this.getStartTime())/1000));
		} else {
			this.setCurrentTime(this.roundTime - ((now - this.getStartTime())/1000));
		}
		
		//Just in case this happens for some reason
		if(this.getCurrentTime() < 0) this.setCurrentTime(0);
	}

	/**
	 * Loads the next question so the players can start answering it
	 * */
	public synchronized void loadNewQuestion() {
		
		this.currentQuestionIndex += 1;
		
		// Set the next question as the current question
		this.currentQuestion = this.Questions.get(this.currentQuestionIndex);
		
		// Reset the answer timer
		this.currentAnswerCounter = 0;
	}
	
	/**
	 * When a player answers a question tell them what place they got an increment the counter
	 * */
	public synchronized int getAnswerPosition() {
		this.setCurrentAnswerCounter(this.getCurrentAnswerCounter() + 1);
		return this.getCurrentAnswerCounter();
	}
	
	/**
	 * Reset the timer to 20 seconds
	 * */
	public synchronized void resetTimer() {
		//Set the current timer;
		this.setStartTime(System.currentTimeMillis());
		long now = System.currentTimeMillis();
		
		//Round time is 20 seconds
		this.setCurrentTime(this.roundTime - ((now - this.getStartTime())/1000));
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

	public synchronized int getCurrentAnswers() {
		return currentAnswers;
	}

	public synchronized void setCurrentAnswers(int currentAnswers) {
		this.currentAnswers = currentAnswers;
	}

	public synchronized int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

	public synchronized void setCurrentQuestionIndex(int currentQuestionIndex) {
		this.currentQuestionIndex = currentQuestionIndex;
	}

	public synchronized long getStartTime() {
		return startTime;
	}

	public synchronized void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public synchronized long getCurrentTime() {
		return currentTime;
	}

	public synchronized void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public synchronized int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public synchronized void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public synchronized int getCount() {
		return count;
	}

	public synchronized void setCount(int count) {
		this.count = count;
	}

	public synchronized int getCurrentAnswerCounter() {
		return currentAnswerCounter;
	}

	public synchronized void setCurrentAnswerCounter(int currentAnswerCounter) {
		this.currentAnswerCounter = currentAnswerCounter;
	}

	public static synchronized Comparator<PlayerBean> getSortByPointsComparator() {
		return sortByPointsComparator;
	}

	public static synchronized void setSortByPointsComparator(Comparator<PlayerBean> sortByPointsComparator) {
		GameSessionBean.sortByPointsComparator = sortByPointsComparator;
	}
	
	public StringBuffer getJoinKey() {
		return joinKey;
	}

	public void setJoinKey(StringBuffer joinKey) {
		this.joinKey = joinKey;
	}
	
	public void getTopThreePlayers(){
		Collections.sort(this.getCurrentPlayers(), sortByPointsComparator);
	}
	
	public static Comparator<PlayerBean> sortByPointsComparator = new Comparator<PlayerBean>() {         
	    @Override         
	    public int compare(PlayerBean player1, PlayerBean player2) {             
	      return (player1.getScore() > player1.getScore() ? -1 :                     
	              (player1.getScore() == player1.getScore() ? 0 : 1));           
	    }     
	 };
	
	public synchronized PlayerBean getPlayerById(int id) {
		for(int i = 0; i < this.currentPlayers.size(); i++) {
			if(this.currentPlayers.get(i).getPlayerId() == id) {
				return this.currentPlayers.get(i);
			}
		}
		return null;
	}
	
	public synchronized Boolean hasPlayers() {
		if(this.currentPlayers == null) return false;
		return true;
	}
	
	public synchronized void addDumbyData(){
		if(this.hasPlayers()) return;
		this.currentPlayers = new ArrayList<PlayerBean>();
		
		for(int i = 0; i < 5; i ++) {
			PlayerBean temp = new PlayerBean();
			temp.setUsername(new StringBuffer("Test " + i));
			this.currentPlayers.add(temp);
		}
	}
	
	public synchronized void removeUser(int key) {
		
		for(int i = 0; i < this.getCurrentPlayers().size(); i++) {
			
			if(this.getCurrentPlayers().get(i).getPlayerId() == key){
				this.getCurrentPlayers().remove(i);
				this.getCurrentPlayers().trimToSize();
			}
		}
	}
	
	public synchronized void addPlayer(PlayerBean p) {
		this.currentPlayers.add(p);
	}
	
	public synchronized int getInstanceId() {
		return instanceId;
	}


	public synchronized void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}


	public synchronized int getState() {
		return state;
	}


	public synchronized void setState(int state) {
		this.state = state;
	}


	public synchronized StringBuffer getGlobalChatBuffer() {
		return globalChatBuffer;
	}


	public synchronized void setGlobalChatBuffer(StringBuffer globalChatBuffer) {
		this.globalChatBuffer = globalChatBuffer;
	}


	public synchronized StringBuffer getCategory() {
		return category;
	}


	public synchronized void setCategory(StringBuffer category) {
		this.category = category;
	}


	public synchronized StringBuffer getDifficulty() {
		return difficulty;
	}


	public synchronized void setDifficulty(StringBuffer difficulty) {
		this.difficulty = difficulty;
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


	public synchronized ArrayList<PlayerBean> getCurrentPlayers() {
		return currentPlayers;
	}


	public synchronized void setCurrentPlayers(ArrayList<PlayerBean> currentPlayers) {
		this.currentPlayers = currentPlayers;
	}


	public synchronized int getMaxPlayers() {
		return maxPlayers;
	}


	public synchronized void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public int getNextAnswerPosition() {
		currentAnswerCounter = currentAnswerCounter + 1;
		return count;
	}
	
	public void resetNextAnswerPosition() {
		currentAnswerCounter = 0;
	}


	public synchronized ArrayList<QuestionBean> getQuestions() {
		return Questions;
	}


	public synchronized void setQuestions(ArrayList<QuestionBean> questions) {
		Questions = questions;
	}
	
	public GameSessionBean() {
		
		this.currentPlayers = new ArrayList<PlayerBean>();
		
	}
}