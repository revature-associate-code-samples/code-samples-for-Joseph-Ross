package com.ex.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.ex.dao.UserDao;
import com.ex.exception.EmptyInputStringException;
import com.ex.exception.InvalidDecisionException;
import com.ex.exception.InvalidReimbIdException;
import com.ex.exception.UserNotAuthorizedForThisRequestException;
import com.ex.pojos.User;

public class managerReimbursementDecisionService {

	public static void submitDecision(User user, HttpServletRequest req) throws InvalidDecisionException, EmptyInputStringException, InvalidReimbIdException, SQLException, UserNotAuthorizedForThisRequestException {
		
		
		//Save params
		String decision = req.getParameter("decision");
		String reimburementIdString = req.getParameter("reimbId");
		
		//Get and check decision id
		int decisionIndex = 0;
		if(decision == null) throw new EmptyInputStringException();
		if(decision.equals("2")) decisionIndex = 2;
		else if(decision.equals("3")) decisionIndex = 3;
		//Else client has been tampered with
		else throw new InvalidDecisionException();
		
		//Get and check reimbId
		int reimburementId = 0;
		if(reimburementIdString == null) throw new EmptyInputStringException();
		try {
			reimburementId = Integer.parseInt(reimburementIdString);
		} catch (NumberFormatException e) {
			throw new InvalidReimbIdException();
		}
		
		//After params are checked try the query
		UserDao.SubmitReimburementDecision(user, reimburementId, decisionIndex);
		
	}
	
}
