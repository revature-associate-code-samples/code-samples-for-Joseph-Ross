package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ex.game.GameSessionBean;
import com.ex.game.PlayerBean;
import com.ex.messages.JoinLobbyMessage;
import com.ex.services.GameManagerService;

/**
 * Author: Joseph Ross
 * JoinLobbyController
 * -> This controller is used for joining an existing game session
 * -> The game session lobby key is sent as a parameter for joing the game
 * -> If the supplied game key is not associated with an existing game session or
 * 		is already full the client is notified and doesn't join the game
 * */

@RestController
@RequestMapping("/join-waiting")
@CrossOrigin(origins = "*")
public class JoinLobbyController {
	
	private static Logger logger = Logger.getLogger(JoinLobbyController.class);
	
	static int count = 0;
        
	@CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JoinLobbyMessage joinGame(HttpServletRequest req, HttpServletResponse resp) {
		
		// Create the message object used to tell the client if they can join the game
		JoinLobbyMessage message = new JoinLobbyMessage();
		
		// Save the sent key
		String key = req.getParameter("lobbyKey");
		
		logger.trace("Player is attemping to join lobby identified by key: " + key);
		
		// Get a game manager instance
		GameManagerService gm = GameManagerService.getInstance();
		
		// Attempt to get the game
		GameSessionBean game = gm.getGameByKey(new StringBuffer(key));
		
		// If game doesn't exist or is already full
		if(game == null || game.currentPlayers.size() == game.getMaxPlayers()) {
			message.setHasError(1); // 1 indicates the client could not join the game
			message.setError(new StringBuffer("lobby is full or does not exist"));
			resp.setStatus(HttpServletResponse.SC_CONFLICT);
		
		// Else the player is connected to the game
		} else {
			
			//Create the new player
			PlayerBean newPlayer = new PlayerBean();
			game.count = game.count + 1; // Increment the player id counter
			newPlayer.setPlayerId(game.count);
			newPlayer.setUsername(new StringBuffer(req.getParameter("username")));
			game.addPlayer(newPlayer);
			
			//Populate the information the client needs to play to the game
			message.setUserId(newPlayer.getPlayerId());
			message.setLobbyId(game.getJoinKey());
	        message.setCategory(game.getCategory());
	        message.setLobbyName(game.getName());
	        message.setQuestions(game.numberOfQuestions);
	        message.setScope(game.getScope());
		}
		
		// Send the client the information it needs to decide what to do
		return message;
	}
}
