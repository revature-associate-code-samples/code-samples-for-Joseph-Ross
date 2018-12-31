package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.revature.db.objects.Account;
import com.revature.db.objects.Customer;
import com.revature.exception.accountdao.AccountInformationUpdatedAfterLastRetriveException;
import com.revature.exception.accountdao.InvalidTransactionTypeException;
import com.revature.exception.accountdao.WithdrawAmountExceedsBalanceException;
import com.revature.util.ConnectionFactory;

public class AccountDao {

	
	public static void addAccount(Customer user, Account account) throws SQLException {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			//Insert the new account
			String sql = "INSERT INTO account(userId,accountType,balance) VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUserId());
			ps.setString(2, account.getAccountType());
			ps.setDouble(3, account.getAccountBalance());
			ps.executeQuery();
		}
	}
	
	//Returns all accounts for a specific user 
	public static ArrayList<Account> getAssociatedAccounts(Customer user) throws SQLException {
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			//Get user accounts
			String sql = "SELECT * FROM ACCOUNT WHERE USERID = " + user.getUserId();
			Statement s = conn.createStatement();
			
			
			ResultSet rs =  (ResultSet) s.executeQuery(sql);
			
			//Add accounts to a Collection
			while(rs.next()) {
				accounts.add(new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(1)));
			}
			
			//Use function to get number of accounts with that user id
			sql = "{? = call TESTY(?)}";
			CallableStatement cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setInt(2, user.getUserId());
			cs.execute();
			
			//Store result of callableStatement
			int result = cs.getInt(1);
			
			//If the result of the function and the size of the list are different then an error happened
			if(result != accounts.size()) throw new SQLException();
			
		}
		return accounts;
	}
	
	//Used to deposit or withdraw cash
	public static void changeBalance(Account acc, double amount, String transactionType) throws WithdrawAmountExceedsBalanceException, SQLException, AccountInformationUpdatedAfterLastRetriveException, InvalidTransactionTypeException {
		
		double newBalance = 0;
		
		if(transactionType == "withdraw") newBalance = acc.getAccountBalance() - amount;
		else if(transactionType == "deposit") newBalance = acc.getAccountBalance() + amount;
		else throw new InvalidTransactionTypeException();
		
		if(newBalance < 0) throw new WithdrawAmountExceedsBalanceException();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
		
			conn.setAutoCommit(false);
			String sql = "UPDATE ACCOUNT SET BALANCE = ? WHERE ACCOUNTID = ? AND BALANCE = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, newBalance);
			ps.setInt(2, acc.getAccountId());
			ps.setDouble(3, acc.getAccountBalance());
			
			int rowsEffected = ps.executeUpdate();
			
			System.out.println(rowsEffected);
			
			conn.commit();
			
			if(rowsEffected == 0) throw new AccountInformationUpdatedAfterLastRetriveException();
		}
	}
}
