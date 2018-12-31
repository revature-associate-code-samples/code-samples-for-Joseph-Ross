package com.ex.messages;

/**
 * Author: Joseph Ross
 * PlayerChatMessage
 * -> information about a message sent in game chat
 * */
public class PlayerChatMessage {
	
	StringBuffer username;
	StringBuffer message;
	StringBuffer time;
	int userId;
	
	public synchronized StringBuffer getTime() {
		return time;
	}
	public synchronized void setTime(StringBuffer time) {
		this.time = time;
	}
	public synchronized int getUserId() {
		return userId;
	}
	public synchronized void setUserId(int userId) {
		this.userId = userId;
	}
	public synchronized StringBuffer getUsername() {
		return username;
	}
	public synchronized void setUsername(StringBuffer username) {
		this.username = username;
	}
	public synchronized StringBuffer getMessage() {
		return message;
	}
	public synchronized void setMessage(StringBuffer message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "UserMessage [username=" + username + ", message=" + message + ", time=" + time + ", userId=" + userId
				+ "]";
	}
}
