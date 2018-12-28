package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.game.GameSessionBean;
import com.ex.services.GameManagerService;

/**
 * Author: Joseph Ross
 * StartGameController
 * -> This is called after all the clients have had a chance to connect.
 * -> The game session is signaled to start and send each client the first question
 * */
@Controller
@RequestMapping("/start-game")
@CrossOrigin(origins = "*")
public class StartGameController {
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public int startGame(HttpServletRequest req, HttpServletResponse resp) {
		
		// Get the game session by key
		StringBuffer gameKey = new StringBuffer(req.getParameter("key"));
		GameManagerService manager = GameManagerService.getInstance();
		GameSessionBean game = manager.getGameByKey(gameKey);
		
		//start the game
		game.startGame();
		
		return 0;
	}
}
