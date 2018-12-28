package com.ex.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ex.pojos.User;
import com.ex.service.AuthenticatorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ex.exception.EmptyInputStringException;
import com.ex.exception.InvalidCharactersException;
import com.ex.exception.InvalidStringInputException;
import com.ex.exception.InvalidUsernameAndPasswordException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		logger.trace("A user sent a request for login credential conformation");
		
		try {
			User user = (User) AuthenticatorService.checkCreds(req);
			
			//Because this will be sent to the client the userid is removed
			user.setUserId(0);
			
			//Create the writer for sending response
			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/json");
			
			//Create the mapper for converting response object to JSON
			ObjectMapper mapper = new ObjectMapper();
			
			//Convert sessionStatus object to JSON and send it
			writer.write(mapper.writeValueAsString(user));
			
		} catch (InvalidUsernameAndPasswordException e) {
			
			logger.trace("Invalid user login attempt");
			
			//401 means bad login info
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials");
			
		} catch (InvalidCharactersException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
			
		} catch (EmptyInputStringException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
		
		} catch (SQLException e) {
			
			logger.trace("An unknown sql error has occured");
			
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unknown error occurred");
			
		} catch (InvalidStringInputException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
		}
			
		
	}
	
}
