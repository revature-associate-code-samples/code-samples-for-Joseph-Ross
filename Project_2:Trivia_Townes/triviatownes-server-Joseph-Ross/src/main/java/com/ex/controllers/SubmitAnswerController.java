package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.game.GameSessionBean;
import com.ex.game.PlayerBean;
import com.ex.services.GameManagerService;

/**
 * Author: Joseph Ross
 * SubmitAnswerController
 * -> This is used for submitting an answer from the client during an active game session
 * */
@Controller
@RequestMapping("/game-update")
@CrossOrigin(origins = "*")
public class SubmitAnswerController {
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public int submitAnswer(HttpServletRequest req, HttpServletResponse resp) {
		
		//Store the answer info
		int playerId = Integer.parseInt(req.getParameter("playerId"));
		StringBuffer lobbyKey = new StringBuffer(req.getParameter("lobbyKey"));
		int points = Integer.parseInt(req.getParameter("points"));
		
		//Get the player
		GameManagerService gm = GameManagerService.getInstance();
		GameSessionBean game = gm.getGameByKey(lobbyKey);
		PlayerBean player = game.getPlayerById(playerId);
		
		//Set the answer position so other players know this user answered
		player.setCurrentAnswerPosition(game.getNextAnswerPosition());
		
		//Add the points to the score
		player.setScore(player.getScore() + points);
		
		//If player answered the question right
		if(points != 0) {
			player.setRightAnswers(player.getRightAnswers() + 1);
			player.setCurrentStreak(player.getCurrentStreak() + 1);
			if(player.getMaxStreak() < player.getCurrentStreak()) {
				player.setMaxStreak(player.getCurrentStreak());
			}
		//If player answered the question wrong
		} else {
			player.setWrongAnswers(player.getWrongAnswers() + 1);
			player.setCurrentStreak(0);
		}
		
		//Something needed to be returned so spring would work
		return 0;
	}

}
