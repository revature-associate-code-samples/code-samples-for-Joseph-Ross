package com.ex.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ex.exception.EmptyInputStringException;
import com.ex.exception.InvalidCharactersException;
import com.ex.exception.InvalidDecisionException;
import com.ex.exception.InvalidReimbIdException;
import com.ex.exception.InvalidStringInputException;
import com.ex.exception.InvalidUsernameAndPasswordException;
import com.ex.exception.UserNotAuthorizedForThisRequestException;
import com.ex.pojos.User;
import com.ex.service.AuthenticatorService;
import com.ex.service.managerReimbursementDecisionService;

@WebServlet("/managerReimbursementDecision")
public class ManagerReimbursementDecision extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ManagerReimbursementDecision.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		logger.trace("A manger sent a request to settle a reimbursement record");
		
		try {
			User user = AuthenticatorService.checkCreds(req);
			
			managerReimbursementDecisionService.submitDecision(user, req);
			
			resp.setStatus(HttpServletResponse.SC_OK);
			
		} catch (InvalidUsernameAndPasswordException e) {
			
			logger.trace("Invalid manager login attempt after initial login. This is suspicious");
			
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
			
			//Unknown Database Error
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		} catch (InvalidStringInputException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
		
		} catch (InvalidDecisionException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
		
		} catch (InvalidReimbIdException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
		
		} catch (UserNotAuthorizedForThisRequestException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
		
		}
	}
}
