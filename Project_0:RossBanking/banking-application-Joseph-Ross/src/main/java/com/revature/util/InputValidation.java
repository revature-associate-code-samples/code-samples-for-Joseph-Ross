package com.revature.util;

import com.revature.exception.input.*;

//Used to check passwords and usernames
public class InputValidation {

	public static Boolean checkStringInput(String input) throws TooManyCharactersInputException, NotEnoughCharactersInputException, IllegalCharactersInputException {
		
		//The user is not allowed to enter input greater than 10 characters
		if(input.length() > 10) throw new TooManyCharactersInputException();
		
		//The user is not allowed to enter input less than 4 characters
		if(input.length() < 4) throw new NotEnoughCharactersInputException();
		
		/*
		 * User is not allowed to have anything but letters and numbers in their
		 * usernames and passwords. If we strip invalid chars away and the the result is
		 * not the same as the original, then the user entered invalid input
		 */
		if(!input.replaceAll("[^a-zA-Z0-9]", "").equals(input)) throw new IllegalCharactersInputException();
		
		//If all the checks pass then return true
		return true;
	}
}
