package com.ex.services;

import java.util.ArrayList;

import com.ex.game.GameSessionBean;
import com.ex.messages.GameInfoMessage;

/**
 * Author: Joseph Ross
 * -> This class is used for accessing and storing references to game sessions
 * -> This class follows the lazy singleton design pattern
 * */
public class GameManagerService {
	
	//This stores the instance of the singleton 
	private static GameManagerService instance;
	
	//The stores the active game sessions
	public ArrayList<GameSessionBean> gameList;
	
	//Overrides the default public constructor
	private GameManagerService() {}
	
	//Lazy get singleton instance method
	public static GameManagerService getInstance() { 
		
		//If instance is null make one (Lazy implementation)
		if (instance == null) { 
			
			//remove overhead by using synchronized block
			synchronized (GameManagerService.class){
			
				//Check if instance is null again in synchronized block
				if(instance == null) {
					instance = new GameManagerService();
					instance.gameList = new ArrayList<GameSessionBean>();
				}
			} 
		}
		return instance; 
	}
	
	/**
	 * Creates a new game and adds it to the list of games
	 * returns the index of the game
	 */
	synchronized public int createGame() {
		
		//Create the new game instance
		GameSessionBean temp = new GameSessionBean();
		
		//Set the instance id that corresponds to the index in the list
		temp.setInstanceId(gameList.size());
		
		//Add the instance to the list
		gameList.add(temp);
		
		//Return the index of the instance
		return gameList.size() - 1;
	}
	
	/**
	 * Get game by id
	 * */
	synchronized public GameSessionBean getGame(int gameIndex) {
		return gameList.get(gameIndex);
	}
	
	/**
	 * Get game by key
	 * */
	synchronized public GameSessionBean getGameByKey(StringBuffer key) {
		
		//Loop through each game
		for(int i = 0; i < this.gameList.size(); i++) {
			
			//Find and return the game
			if(gameList.get(i).getJoinKey().toString().equals(key.toString())) {
				return gameList.get(i);
			}
		}
		
		//Returns null if no game with that key was found
		return null;
	}
	
	/**
	 * Used to get a list of games for a specific category that need additional players
	 * Only public lobbies are returned
	 * */
	synchronized public ArrayList<GameInfoMessage> getGameSessionsInfo(String category) {
		
		//Creates a new list for storing the game info objects
		ArrayList<GameInfoMessage> serverList = new ArrayList<GameInfoMessage>();
		
		//Loops through each of the games
		for(int i = 0; i < gameList.size(); i++) {
			
			//Don't check null values
			if(gameList.get(i) == null) continue;
			
			//Don't returns games that aren't in the waiting state
			if(gameList.get(i).getState() != 0) continue;
			
			//Don't return games that are private (private games can only be accessed by key)
			if(gameList.get(i).getScope().toString().equals("true")) continue;
			
			//Only return games of the requested category
			if(category.equals("all") || category.equals(gameList.get(i).getCategory().toString().toLowerCase())) {
				
				//Create a game info object and store information about a real game
				GameInfoMessage temp = new GameInfoMessage();
				temp.setCategory(gameList.get(i).getCategory());
				temp.setName(gameList.get(i).getName());
				temp.setDifficulty(gameList.get(i).getDifficulty());
				temp.setPlayers(gameList.get(i).getCurrentPlayers().size());
				temp.setMaxPlayers(gameList.get(i).getMaxPlayers());
				temp.setScope(gameList.get(i).getScope());
				temp.setKey(gameList.get(i).getJoinKey());
				
				//Add it to the list that will be returned
				serverList.add(temp);
			}
		}
		return serverList;
	}
}