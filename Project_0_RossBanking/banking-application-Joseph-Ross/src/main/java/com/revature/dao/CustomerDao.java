package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.db.objects.Customer;
import com.revature.exception.customerdao.*;
import com.revature.util.ConnectionFactory;

public class CustomerDao {
	
	public static Customer addUser(Customer user) throws UsernameAlreadyTakenException, SQLException {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			String sql = "select * from customer where username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ResultSet rs = ps.executeQuery();
			
			//If rs.next() is true then the username must be taken
			if(rs.next()) throw new UsernameAlreadyTakenException();
			
			//Insert the new user
			sql = "INSERT INTO customer(username,password) VALUES (?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.executeQuery();
			
			//Get the user id
			sql = "select * from customer where username = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			rs = ps.executeQuery();
			
			//Save the id
			if(rs.next()) user.setUserId(rs.getInt(1));
			
		}
		return user;
	}

	public static Customer loginAttempt(Customer user) throws InvalidAccountCredentialsException, SQLException {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			//Check and see if username and passord combo is valid
			String sql = "select * from customer where username = ? and password = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			
			//If rs.next() is false then the username and password combination
			//is not the database
			if(rs.next()) user.setUserId(rs.getInt(1));
			else throw new InvalidAccountCredentialsException();
		}
		return user;
	}
}
