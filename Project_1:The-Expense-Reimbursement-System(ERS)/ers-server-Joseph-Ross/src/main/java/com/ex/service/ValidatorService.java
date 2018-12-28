package com.ex.service;

import com.ex.exception.InvalidCharactersException;
import com.ex.exception.InvalidDollarAmountException;
import com.ex.exception.InvalidStringInputException;
import com.ex.exception.InvalidTypeException;

import org.apache.commons.lang3.StringUtils;

import com.ex.exception.EmptyInputStringException;

public class ValidatorService {
	
	public static void checkStringInput(String input) throws InvalidCharactersException, EmptyInputStringException, InvalidStringInputException {
		
		if(input.equals(null) || input.length() == 0) throw new EmptyInputStringException();
		
		String test = input.replaceAll( "[^a-zA-Z0-9 ]" , "" );
		if(!test.equals(input)) throw new InvalidCharactersException();
		
		if(input.length() > 50) throw new InvalidStringInputException();
	}
	
	public static void checkDollarAmount(String input) throws InvalidCharactersException, InvalidDollarAmountException, EmptyInputStringException {
		
		if(input.equals(null) || input.length() == 0) throw new EmptyInputStringException();
		
		String test = input.replaceAll( "[^0-9. ]" , "");
		if(!test.equals(input)) throw new InvalidCharactersException();
		
		int matches = StringUtils.countMatches(input, '.');
		if(matches > 1) throw new InvalidDollarAmountException();
		
		//Should not throw exception but it can
		try {
			Double.parseDouble(input);
		} catch (NumberFormatException e) {
			throw new InvalidDollarAmountException();
		}
		
	}
	
	public static void checkType(String type) throws InvalidTypeException {
		
		if(type == null) throw new InvalidTypeException();
		
		switch(type) {
		case "1":
			return;
		case "2":
			return;
		case "3":
			return;
		case "4":
			return;
		default:
			throw new InvalidTypeException();
		}
		
	}
	
	public static void checkDesc(String input) throws EmptyInputStringException, InvalidCharactersException, InvalidStringInputException {
		
		if(input.equals(null) || input.length() == 0) throw new EmptyInputStringException();
		
		String test = input.replaceAll( "[^a-zA-Z0-9-., ]" , "" );
		if(!test.equals(input)) throw new InvalidCharactersException();
		
		if(input.length() > 250) throw new InvalidStringInputException();
		
	}

}
