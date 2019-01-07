package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.game.GameSessionBean;
import com.ex.services.GameManagerService;

/**
 * Author: Joseph Ross
 * StartGameConnectController
 * -> This tells the game session to connect the players before starting the game
 * -> The game is in this state when the game state is 1
 * */
@Controller
@RequestMapping("/start-game-connect")
@CrossOrigin(origins = "*")
public class StartGameConnectController {
	
	private static Logger logger = Logger.getLogger(StartGameController.class);
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public int startGameConnect(HttpServletRequest req, HttpServletResponse resp) {
		
		// Get game by key
		StringBuffer gameKey = new StringBuffer(req.getParameter("key"));
		GameManagerService manager = GameManagerService.getInstance();
		GameSessionBean game = manager.getGameByKey(gameKey);
		
		//Game is ready to start
		game.setState(1);
		
		logger.trace("Game started with key: " + gameKey.toString());
		
		// Something needs to be returned
		return 0;
	}

}
