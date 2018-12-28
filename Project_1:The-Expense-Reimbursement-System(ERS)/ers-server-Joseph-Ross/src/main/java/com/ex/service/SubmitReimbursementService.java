package com.ex.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.ex.dao.UserDao;
import com.ex.exception.EmptyInputStringException;
import com.ex.exception.InvalidCharactersException;
import com.ex.exception.InvalidDollarAmountException;
import com.ex.exception.InvalidStringInputException;
import com.ex.exception.InvalidTypeException;
import com.ex.pojos.ReimbursementEntry;
import com.ex.pojos.User;

public class SubmitReimbursementService {
	
	public static void submitReimbursement(User user, HttpServletRequest req) throws InvalidCharactersException, InvalidDollarAmountException, EmptyInputStringException, InvalidStringInputException, InvalidTypeException, SQLException {
		
		//Store the inputs
		String amount = req.getParameter("amount");
		String desc = req.getParameter("descr");
		String type = req.getParameter("type");
		
		//Run the inputs through the validator, exception will be thrown if anything goes wrong
		ValidatorService.checkDollarAmount(amount);
		ValidatorService.checkDesc(desc);
		ValidatorService.checkType(type);
		
		//Create the reimbursement
		ReimbursementEntry re = new ReimbursementEntry();
		re.setAmount(Double.parseDouble(amount));
		re.setDesc(desc);
		re.setReimbTypeId(Integer.parseInt(type));
		
		UserDao.SubmitReimburement(user, re);
	}

}
