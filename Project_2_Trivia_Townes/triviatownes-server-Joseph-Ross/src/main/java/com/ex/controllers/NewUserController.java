package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Joseph Ross
 * NewUserController
 * -> This is called every time the client visits the create game page or landing page
 * -> A new game session is created every time a user sends a request
 * -> If the user had an existing session it is deleted and a new one is made
 */
@Controller
@RequestMapping("/new-user")
@CrossOrigin(origins = "*")
public class NewUserController {
	
	private static Logger logger = Logger.getLogger(NewUserController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public HttpServletResponse newUser(HttpServletResponse resp, HttpServletRequest req) {
		
		//No matter what create a new session
		HttpSession session = req.getSession(false);
		if (session == null) {
			logger.trace("Creating New Game Session");
		    session = req.getSession(true);
		} else {
			logger.trace("Deleting old game session and creating new one");
			session.invalidate();
			session = req.getSession(true);
			
		}
		return null;
	}
	

}
