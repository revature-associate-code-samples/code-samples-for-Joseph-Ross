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
import com.ex.exception.InvalidDollarAmountException;
import com.ex.exception.InvalidStringInputException;
import com.ex.exception.InvalidTypeException;
import com.ex.exception.InvalidUsernameAndPasswordException;
import com.ex.pojos.User;
import com.ex.service.AuthenticatorService;
import com.ex.service.SubmitReimbursementService;

@WebServlet("/submitReimbursementRequest")
public class SubmitReimbursementRequest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SubmitReimbursementRequest.class);

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		logger.trace("A user sent a request for submitting a reimbursement record");
		
		try {
			User user = (User) AuthenticatorService.checkCreds(req);
			
			SubmitReimbursementService.submitReimbursement(user, req);
			
			//Everything went good
			resp.setStatus(HttpServletResponse.SC_OK);
			
		} catch (InvalidUsernameAndPasswordException e) {
			
			logger.trace("Invalid user login attempt after initial login. This is suspicious");
			
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
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unknown error occurred");
			
		} catch (InvalidStringInputException e) {
		
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
			
		} catch (InvalidDollarAmountException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
			
		} catch (InvalidTypeException e) {
			
			logger.trace("In invalid request was sent. The client has been compromised");
			
			//Client has been hacked!
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Client has been tampered with!");
			
		}
	}
}
