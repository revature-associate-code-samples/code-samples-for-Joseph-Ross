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
 * CreateGameController
 * -> This is used to create a new game session
 * -> Each game takes a category, #seats, #questions, #name, #scope
 * -> The lobby leader calls this controller, so they are the first player added to the game
 * */

@RestController
@RequestMapping("/create-game")
@CrossOrigin(origins = "*")
public class CreateGameController {
	
	private static Logger logger = Logger.getLogger(CreateGameController.class);
	
	//This counter is used to assign unique game session keys. The key is used to identify a specific session
	static int count = 1185;
    
	/**
	 * This request is given the variables used to init a game
	 * -> Each game takes a category, #seats, #questions, #name, #scope
	 * */
	@CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JoinLobbyMessage createGame(HttpServletRequest req, HttpServletResponse resp) {
		
		//Get the instance of the game manager service
        GameManagerService service = GameManagerService.getInstance();
        
        //Create a new game and save the game index
        int id = service.createGame();
        
        //Save the game session bean instance
        GameSessionBean gs = service.getGame(id);
        
        //Set all the required parameters recived from client
        gs.setJoinKey(new StringBuffer(count + ""));
        gs.setCategory(new StringBuffer(req.getParameter("category").toLowerCase()));
        gs.setMaxPlayers(new Integer(req.getParameter("seats")));
        gs.numberOfQuestions = new Integer(req.getParameter("questions"));
        gs.setName(new StringBuffer(req.getParameter("name")));
        gs.setScope(new StringBuffer(req.getParameter("private")));
        
        //Create the first player in the game (the person who created the game)
        StringBuffer username = new StringBuffer(req.getParameter("username"));
        PlayerBean player = new PlayerBean();
        gs.count = gs.count + 1; //Increment game player id counter
        player.setUsername(username);
        player.setPlayerId(gs.count);
        gs.addPlayer(player); //Add the player to the game
        
        //Create the message object to be sent back to the client with all the information it needs
        JoinLobbyMessage message = new JoinLobbyMessage();
        message.setUserId(player.getPlayerId());
        message.setLobbyId(new StringBuffer(count + ""));
        message.setCategory(new StringBuffer(req.getParameter("category")));
        message.setLobbyName(new StringBuffer(req.getParameter("name")));
        message.setQuestions(new Integer(req.getParameter("questions")));
        
        //Increment the game key counter
        count = count + 1;

        return message;
    }
}
