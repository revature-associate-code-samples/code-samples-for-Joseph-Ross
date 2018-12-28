package com.ex.controllers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.game.GameSessionBean;
import com.ex.game.PlayerBean;
import com.ex.messages.GameInfoMessage;
import com.ex.messages.PlayerChatMessage;
import com.ex.messages.WaitingMessage;
import com.ex.services.GameManagerService;

/**
 * WebSocketConnectionController
 * -> This class is used for receiving web socket communications from the waiting page and the game page
 * */
@Controller
@CrossOrigin(origins = "*")
public class WebSocketConnectionController {
	
	/**
	 * Receives all game chat messages sent by a user in a specific game
	 * and sends the message to all connected users in that game with a time stamp
	 * */
	@MessageMapping("{lobbyId}/recive-chat-game")
	@SendTo("/send-game-update/{lobbyId}/send-chat")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public PlayerChatMessage getActiveGameMessage(@Payload PlayerChatMessage userResponse) {
		
		userResponse.setTime(new StringBuffer(DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now())));
		return userResponse;
	}
	
	/**
	 * Receives all game chat messages sent by a user in a specific waiting lobby
	 * and sends the message to all connected users in that lobby with a time stamp
	 * */
	@MessageMapping("{lobbyId}/recive-chat")
	@SendTo("/waiting/{lobbyId}/send-chat")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public PlayerChatMessage getWatingLobbyMessage(@Payload PlayerChatMessage userResponse) {
		
		userResponse.setTime(new StringBuffer(DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now())));
		return userResponse;
	}
	
	/**
	 * The leader sends this request to the game session
	 * The controller sends a game update to all the clients listening
	 * */
	@MessageMapping("{gameKey}/get-game-data")
	@SendTo("/send-game-update/{gameKey}/get-game-data")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public GameInfoMessage activeGameUpdate(@DestinationVariable String gameKey,SimpMessageHeaderAccessor headerAccessor) {
		
		//Game Manager Service to access current game
		GameManagerService gm = GameManagerService.getInstance();
		
		//Get Game Session Wtih Key
		GameSessionBean game = gm.getGameByKey(new StringBuffer(gameKey));
		
		//If the game isn't over send a game update
		if(game.getState() != 2) game.updateGame();
		
		//Sort the players
		game.getTopThreePlayers();
		
		//Used to send the clients all information about the game
		GameInfoMessage gameInfo = new GameInfoMessage(game);
		
		//If the game is in the 2 second waiting period in between each round tell the clients everyone has answered
		if(game.getState() == 3) gameInfo.setNumberOfAnswers(gameInfo.getPlayers());
		
		//Store a list of connected users in order of highest points
		gameInfo.setTopScores(game.getCurrentPlayers());
		
		//Send a message to all listening clients with all updated game info
		return gameInfo;
	}
	
	/*
	 * Used to tell each client
	 * how many players are in the same waiting lobby and when to navigate to the game screen
	 * */
	@MessageMapping("{lobbyId}/{userId}/update-waiting")
	@SendTo("/waiting/{lobbyId}/{userId}/send-waiting")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public WaitingMessage waitinglobbyUpdate(@DestinationVariable String lobbyId, @DestinationVariable String userId, SimpMessageHeaderAccessor headerAccessor) {
		
		//Game Manager Service to access current game
		GameManagerService gm = GameManagerService.getInstance();
		
		//Store a reference to the current game retried by game key
		GameSessionBean game = gm.getGameByKey(new StringBuffer(lobbyId));
		
		//Get game status (0 for waiting and 1 for game is starting)
		ArrayList<PlayerBean> playerList = game.getCurrentPlayers();
		int status = game.getState();
		
		//Create the message with the waiting lobby info and store information about game
		WaitingMessage wm = new WaitingMessage();
		wm.setPlayers(playerList);
		wm.setStatus(status);
		
		return wm;
	}
	
}
