package com.revature.main;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import com.revature.dao.AccountDao;
import com.revature.dao.CustomerDao;
import com.revature.db.objects.Account;
import com.revature.db.objects.Customer;
import com.revature.exception.accountdao.AccountInformationUpdatedAfterLastRetriveException;
import com.revature.exception.accountdao.InvalidTransactionTypeException;
import com.revature.exception.accountdao.WithdrawAmountExceedsBalanceException;
import com.revature.exception.customerdao.InvalidAccountCredentialsException;
import com.revature.exception.customerdao.UsernameAlreadyTakenException;
import com.revature.exception.input.IllegalCharactersInputException;
import com.revature.exception.input.NotEnoughCharactersInputException;
import com.revature.exception.input.TooManyCharactersInputException;
import com.revature.util.InputValidation;

public class AppDriver {
	
	Customer user;
	ArrayList<Account> accounts;
	Scanner sc;
	
	public void login() {
		
		Boolean loginSuccess = false;
		
		//While a successful login has yet to be made
		while(!loginSuccess) {
		
			System.out.println("Please Enter Your Username: ");
			String username = this.sc.nextLine();
			
			try {
				InputValidation.checkStringInput(username);
				
			} catch (TooManyCharactersInputException e) {
				System.out.println("Username Invalid: Usernames must be under 11 characters");
				continue;
				
			} catch (NotEnoughCharactersInputException e) {
				System.out.println("Username Invalid: Usernames must be over 3 characters");
				continue;
				
			} catch (IllegalCharactersInputException e) {
				System.out.println("Username Invalid: Usernames can only contain numbers and letters!");
				continue;
				
			}
			
			System.out.println("Please Enter Your Password: ");
			String password = sc.nextLine();
			
			try {
				InputValidation.checkStringInput(password);
				
			} catch (TooManyCharactersInputException e) {
				System.out.println("Password Invalid: Passwords must be under 11 characters");
				continue;
				
			} catch (NotEnoughCharactersInputException e) {
				System.out.println("Password Invalid: Passwords must be over 3 characters");
				continue;
				
			} catch (IllegalCharactersInputException e) {
				System.out.println("Password Invalid: Passwords can only contain numbers and letters!");
				continue;
				
			}
			
			try {
				this.user = CustomerDao.loginAttempt(new Customer(username, password));
				loginSuccess = true;
			} catch (InvalidAccountCredentialsException e) {
				System.out.println("Username and or password is incorrect, please try again");
				
			} catch (SQLException e) {
				System.out.println("An unknown error occured, please try login in again....");
				
			}
		}
	}
	
	public void createAccount() {
		
		Boolean accountCreated = false;
		Boolean passwordMade = false;
		Boolean usernameMade = false;
		
		String username = null;
		String password = null;
		
		while(!accountCreated) {
			
			System.out.println("Please enter a username: ");
			username = this.sc.nextLine();
			
			if(!usernameMade) {
				try {
					InputValidation.checkStringInput(username);
					usernameMade = true;
					
				} catch (TooManyCharactersInputException e) {
					System.out.println("Username Invalid: Usernames must be under 11 characters");
					continue;
					
				} catch (NotEnoughCharactersInputException e) {
					System.out.println("Username Invalid: Usernames must be over 3 characters");
					continue;
					
				} catch (IllegalCharactersInputException e) {
					System.out.println("Username Invalid: Usernames can only contain numbers and letters!");
					continue;
					
				}
			}
			
			if(!passwordMade) {
				System.out.println("Please enter a Password: ");
				password = sc.nextLine();
				
				try {
					InputValidation.checkStringInput(password);
					passwordMade = true;
					
				} catch (TooManyCharactersInputException e) {
					System.out.println("Password Invalid: Passwords must be under 11 characters");
					continue;
					
				} catch (NotEnoughCharactersInputException e) {
					System.out.println("Password Invalid: Passwords must be over 3 characters");
					continue;
					
				} catch (IllegalCharactersInputException e) {
					System.out.println("Password Invalid: Passwords can only contain numbers and letters!");
					continue;
			
				}
			}
			
			try {
				this.user = CustomerDao.addUser(new Customer(username, password));
				accountCreated = true;
				
			} catch (UsernameAlreadyTakenException e) {
				System.out.println("Error: Another customer already has this username. Please pick another!");
				System.out.println("Note: Your password is saved and will not need to be entered again.");
				usernameMade = false;
				
			} catch (SQLException e) {
				
				System.out.println("An unknown error occured. I'm sorry please restart the program and try again");
				System.exit(1);
			}
		}
	}
	
	public void loadAccounts() {
		try {
			this.accounts = AccountDao.getAssociatedAccounts(this.user);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("An unknown error occured. I'm sorry please restart the program and try again");
			System.exit(1);
		}
	}
	
	
	public void printAccounts() {
		
		this.loadAccounts();
		
		System.out.println("Accounts: ");
		System.out.println("Id\tType\tBalance");
		
		for(int i = 0; i < accounts.size(); i++) {
			System.out.println(i + "\t" + accounts.get(i).getAccountType() + "\t" + accounts.get(i).getAccountBalance());
		}
	}
	
	public void createBankAccount() {
		
		Account newAccount = new Account();
		Boolean accountTypeSelected = false;
		Boolean accountBalanceSelected = false;
		
		while(!accountTypeSelected) {
			System.out.println("Select Account Type");
			System.out.println("Enter 1: for checking");
			System.out.println("Enter 2: for savings");
			
			String input = this.sc.nextLine();
			
			if(!input.replaceAll("[^1-2]", "").equals(input)) {
				System.out.println("Error Invalid Option: Please enter an option in the form of a number from 1 to 2!");
				continue;
			} else if (input.length() != 1) {
				System.out.println("Error Invalid Option: Multiple numbers detected as input. Please enter an option in the form of a number from 1 to 2!");
				continue;
			}
			
			switch(input){
				case "1":
					newAccount.setAccountType("C");
					accountTypeSelected = true;
					break;
				case "2":
					newAccount.setAccountType("S ");
					accountTypeSelected = true;
					break;
				default:
					System.out.println("Error Invalid Option: Please enter an option in the form of a number from 1 to 2!");
			}
		}
		
		while(!accountBalanceSelected) {
			System.out.println("Enter account deposit amount:");
			System.out.println("Examples: 900.00 34.56 0.56");
			
			String amount = sc.nextLine();
			
			try {
				NumberFormat.getCurrencyInstance(Locale.US).parse("$" + amount);
				newAccount.setAccountBalance(Double.parseDouble(amount));
				accountBalanceSelected = true;
			} catch (ParseException e) {
				System.out.println("Error: Dollar amount is not in correct form. see examples");
				continue;
			}
		}
		
		try {
			AccountDao.addAccount(this.user, newAccount);
		} catch (SQLException e) {
			System.out.println("An unknown error occured. I'm sorry please restart the program and try again");
			System.exit(1);
		}
		
	}
	
	public void changeBalance(String transType) {
		
		this.loadAccounts();
		this.printAccounts();
		
		Account target = null;
		Boolean accountSelected = false;
		Boolean amountBalanceSelected = false;
		double amount = 0;
		
		while(!accountSelected) {
			
			System.out.println("Enter the account id");
			String input = sc.nextLine();
			
			if(!input.replaceAll("[^0-9]{1,9}", "").equals(input)) {
				System.out.println("Error Invalid Option: Please enter an option in the form of a single number!");
				continue;
			}
			
			target = this.getAccount(Integer.parseInt(input));
			
			if(target == null) {
				System.out.println("That account does not exist!");
				continue;
			}
			
			accountSelected = true;
		}
			
		while(!amountBalanceSelected) {
			System.out.println("Enter amount:");
			System.out.println("Examples: 900.00 34.56 0.56");
			
			String amountString = sc.nextLine();
			
			try {
				NumberFormat.getCurrencyInstance(Locale.US).parse("$" + amountString);
				amount = Double.parseDouble(amountString);
				amountBalanceSelected = true;
			} catch (ParseException e) {
				System.out.println("Error: Dollar amount is not in correct form. see examples");
				continue;
			}
		}
			
		try {
			AccountDao.changeBalance(target, amount, transType);
		} catch (WithdrawAmountExceedsBalanceException e) {
			System.out.println("Error: Your balance is less than the withdraw amount. Aborting....");
		} catch (SQLException e) {
			System.out.println("An unknown error occured. Please restart program and try again.");
		} catch (AccountInformationUpdatedAfterLastRetriveException e) {
			System.out.println("Error: Your balance information was changed since last print. Aborting....");
			return;
		} catch (InvalidTransactionTypeException e) {
			System.out.println("Hello");
		}
		
		System.out.println("Account balance has been updated");
		
		this.loadAccounts();
	}
	
	public Account getAccount(int id) {
		
		if(id < accounts.size()) return accounts.get(id);
		
		return null;
	}

	public static void main(String[] args) {
		
		AppDriver app = new AppDriver();
		
		app.sc = new Scanner(System.in);
		
		System.out.println("Welcome to Ross Credit Union!");
		
		Boolean loggedIn = false;
		
		while(!loggedIn) {
			System.out.println("What would you like to do?");
			System.out.println("Enter 1: For creating account");
			System.out.println("Enter 2: For logging into an existing account");
			
			String input = app.sc.nextLine();
			
			if(!input.replaceAll("[^1-2]", "").equals(input)) {
				System.out.println("Error Invalid Option: Please enter an option in the form of a number from 1 to 2!");
				continue;
			} else if (input.length() != 1) {
				System.out.println("Error Invalid Option: Multiple numbers detected as input. Please enter an option in the form of a number from 1 to 2!");
				continue;
			}
			
			switch(input) {
				case "1":
					app.createAccount();
					loggedIn = true;
					break;
				case "2":
					app.login();
					loggedIn = true;
					break;
				default:
					System.out.println("Error Invalid Option: Please enter an option in the form of a number from 1 to 2!");
			}
		}
		
		System.out.println("Logged on as: " + app.user.getUsername());
		
		Boolean userIsDone = false;
		
		app.loadAccounts();
		
		//While user isn't done
		while(!userIsDone) {
		
			System.out.println("What would you like to do?");
			System.out.println("Enter 1: For showing open accounts and balances");
			System.out.println("Enter 2: For creating a new account");
			System.out.println("Enter 3: For withdraw money from account");
			System.out.println("Enter 4: For depositing money from account");
			System.out.println("Enter 5: For ending the program");
			
			String input = app.sc.nextLine();
			
			if(!input.replaceAll("[^1-5]", "").equals(input)) {
				System.out.println("Error Invalid Option: Please enter an option in the form of a number from 1 to 5!");
				continue;
			} else if (input.length() != 1) {
				System.out.println("Error Invalid Option: Multiple numbers detected as input. Please enter an option in the form of a number from 1 to 5!");
				continue;
			}
			
			switch(input) {
				case "1":
					app.printAccounts();
					break;
				case "2":
					app.createBankAccount();
					break;
				case "3":
					app.changeBalance("withdraw");
					break;
				case "4":
					app.changeBalance("deposit");
					break;
				case "5":
					userIsDone = true;
					break;
				default:
					System.out.println("Error Invalid Option: Please enter an option in the form of a number from 1 to 5!");
			}
		}
		System.out.println("Thank you for banking with Ross Credit Union! Have a great day!");
	}
}
