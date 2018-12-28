package com.ex.controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.messages.GameInfoMessage;
import com.ex.services.GameManagerService;
import com.google.gson.Gson;

/**
 * Author: Joseph Ross
 * Lobby Update Controller
 * -> Sends a list of all game lobbies in the waiting state
 * -> The client can use this information to attempt to join a game
 * */
@RestController
@RequestMapping(value="/lobby-data")
@CrossOrigin(origins = "*")
public class LobbyUpdateController {
	
	private static Logger logger = Logger.getLogger(LobbyUpdateController.class);
	
	@RequestMapping(value="/{category}/data",
					method=RequestMethod.GET)
	@CrossOrigin(origins = "*")
	@ResponseBody
	public String sendLobbyData(@PathVariable String category) {
		
		// Save the category
		category = category.toLowerCase();
		
		// Game the game manager service
		GameManagerService gm = GameManagerService.getInstance();
		
		// Each item in the list has information about a specific game of the requested category in the waiting state
		// If the category is 'all' then all games in waiting state as sent
		ArrayList<GameInfoMessage> payload = gm.getGameSessionsInfo(category.toLowerCase());
		
		// Convert data to JSON and send to client
		String json = new Gson().toJson(payload).toString();
		String fJson = "{\"data\":" + json + "}";
		return fJson;
	}
}