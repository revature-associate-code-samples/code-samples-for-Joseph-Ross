package com.ex.messages;

import java.util.ArrayList;

import com.ex.game.PlayerBean;

/**
 * Author: Joseph Ross
 * WaitingMessage
 * -> This is a update message for players in the wating lobby
 * -> This tells the client if the game is still in the waiting state
 * -> This tell the client who else is in the lobby
 * */
public class WaitingMessage {
	
	ArrayList<PlayerBean> players;
	int status = 0;
	public synchronized ArrayList<PlayerBean> getPlayers() {
		return players;
	}
	public synchronized void setPlayers(ArrayList<PlayerBean> players) {
		this.players = players;
	}
	public synchronized int getStatus() {
		return status;
	}
	public synchronized void setStatus(int status) {
		this.status = status;
	}

}
