package com.ex.messages;

/**
 * Author: Joseph Ross
 * JoinLobbyMessage
 * -> Information client needs to join or get rejected from lobby
 * */
public class JoinLobbyMessage {
	
	//Information client needs to join lobby
	int userId;
	StringBuffer lobbyId;
	int hasError;
	StringBuffer error; //If they cannot join lobby this tells them why
	StringBuffer scope;
	StringBuffer lobbyName;
	StringBuffer category;
	int questions;
	
	/**
	 * Getters and setters
	 * */
	public synchronized StringBuffer getScope() {
		return scope;
	}
	public synchronized void setScope(StringBuffer scope) {
		this.scope = scope;
	}
	public synchronized StringBuffer getLobbyName() {
		return lobbyName;
	}
	public synchronized void setLobbyName(StringBuffer lobbyName) {
		this.lobbyName = lobbyName;
	}
	public synchronized StringBuffer getCategory() {
		return category;
	}
	public synchronized void setCategory(StringBuffer category) {
		this.category = category;
	}
	public synchronized int getQuestions() {
		return questions;
	}
	public synchronized void setQuestions(int questions) {
		this.questions = questions;
	}
	public synchronized int getUserId() {
		return userId;
	}
	public synchronized void setUserId(int userId) {
		this.userId = userId;
	}
	public synchronized StringBuffer getLobbyId() {
		return lobbyId;
	}
	public synchronized void setLobbyId(StringBuffer lobbyId) {
		this.lobbyId = lobbyId;
	}
	public synchronized int getHasError() {
		return hasError;
	}
	public synchronized void setHasError(int hasError) {
		this.hasError = hasError;
	}
	public synchronized StringBuffer getError() {
		return error;
	}
	public synchronized void setError(StringBuffer error) {
		this.error = error;
	}

}
