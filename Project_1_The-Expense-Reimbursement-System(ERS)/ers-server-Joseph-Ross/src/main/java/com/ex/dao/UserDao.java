package com.ex.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ex.pojos.ReimbursementEntry;
import com.ex.pojos.User;
import com.ex.util.ConnectionFactory;

import com.ex.exception.InvalidUsernameAndPasswordException;
import com.ex.exception.UserNotAuthorizedForThisRequestException;

public class UserDao {
	
	private static Logger logger = Logger.getLogger(UserDao.class);
	
	public static User loginAttempt(String username, String password) throws SQLException, InvalidUsernameAndPasswordException {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			String query = "SELECT ERS_USERS_ID, ERS_USERNAME, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID FROM ERS_USERS WHERE ERS_USERNAME = ? and ERS_PASSWORD = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = (ResultSet) ps.executeQuery();
			
			if(!rs.next()) {
				throw new InvalidUsernameAndPasswordException();
			}
			
			//Create the user object
			User user = new User();
			user.setUserId(rs.getInt(1));
			user.setUsername(rs.getString(2));
			user.setFirstName(rs.getString(3));
			user.setLastName(rs.getString(4));
			user.setEmail(rs.getString(5));
			user.setRole(rs.getInt(6));
			
			return user;
		}
	}
	
	public static ArrayList<ReimbursementEntry> getAllSubmittedReimburements(User user) throws SQLException{
		
		ArrayList<ReimbursementEntry> reimbRecords = new ArrayList<ReimbursementEntry>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			String query = "SELECT REIMB_ID,REIMB_AMOUNT,REIMB_SUBMITTED,REIMB_RESOLVED,REIMB_DESCRIPTION,ERS_USERS.ERS_USERNAME,REIMB_TYPE,REIMB_STATUS FROM ERS_REIMBURSEMENT JOIN ERS_USERS ON ERS_USERS.ERS_USERS_ID = ERS_REIMBURSEMENT.REIMB_AUTHOR JOIN ERS_REIMBURSEMENT_TYPE ON ERS_REIMBURSEMENT.REIMB_TYPE_ID = ERS_REIMBURSEMENT_TYPE.REIMB_TYPE_ID JOIN ERS_REIMBURSEMENT_STATUS ON ERS_REIMBURSEMENT.RERIMB_STATUS_ID = ERS_REIMBURSEMENT_STATUS.REIMB_STATUS_ID WHERE REIMB_AUTHOR = " + user.getUserId();
			
			Statement stm = conn.createStatement();
			
			ResultSet rs = stm.executeQuery(query);
			
			logger.trace("Recived Results");
			
			while(rs.next()) {
				ReimbursementEntry temp = new ReimbursementEntry();
				temp.setAmount(rs.getDouble(2));
				temp.setTimeSubmitted(rs.getString(3));
				temp.setTimeResolved(rs.getString(4));
				temp.setDesc(rs.getString(5));
				temp.setUsername(rs.getString(6));
				temp.setType(rs.getString(7));
				temp.setStatus(rs.getString(8));
				reimbRecords.add(temp);
			}
		}
		return reimbRecords;
	}
	
	public static ArrayList<ReimbursementEntry> getAllReimburements(User user) throws SQLException, UserNotAuthorizedForThisRequestException{
		
		//Check to see if this user is a manager before sending them all the sensitive informations
		if(user.getRole() != 2) throw new UserNotAuthorizedForThisRequestException();
		
		ArrayList<ReimbursementEntry> reimbRecords = new ArrayList<ReimbursementEntry>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			String query = "SELECT REIMB_ID,REIMB_AMOUNT,REIMB_SUBMITTED,REIMB_RESOLVED,REIMB_DESCRIPTION,ERS_USERS.ERS_USERNAME,REIMB_TYPE,REIMB_STATUS FROM ERS_REIMBURSEMENT JOIN ERS_USERS ON ERS_USERS.ERS_USERS_ID = ERS_REIMBURSEMENT.REIMB_AUTHOR JOIN ERS_REIMBURSEMENT_TYPE ON ERS_REIMBURSEMENT.REIMB_TYPE_ID = ERS_REIMBURSEMENT_TYPE.REIMB_TYPE_ID JOIN ERS_REIMBURSEMENT_STATUS ON ERS_REIMBURSEMENT.RERIMB_STATUS_ID = ERS_REIMBURSEMENT_STATUS.REIMB_STATUS_ID";
			
			Statement stm = conn.createStatement();
			
			ResultSet rs = stm.executeQuery(query);
			
			while(rs.next()) {
				ReimbursementEntry temp = new ReimbursementEntry();
				temp.setReimbId(rs.getInt(1));
				temp.setAmount(rs.getDouble(2));
				temp.setTimeSubmitted(rs.getString(3));
				temp.setTimeResolved(rs.getString(4));
				temp.setDesc(rs.getString(5));
				temp.setUsername(rs.getString(6));
				temp.setType(rs.getString(7));
				temp.setStatus(rs.getString(8));

				reimbRecords.add(temp);
			}
		}
		return reimbRecords;
	}
	
	public static void SubmitReimburement(User user, ReimbursementEntry record) throws SQLException {
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String query = "{ call INSERT_ERS_REIMBURSEMENT(?,?,?,?) }";
			CallableStatement cs = conn.prepareCall(query);
			cs.setDouble(1, record.getAmount());
			cs.setString(2, record.getDesc());
			cs.setInt(3, user.getUserId());
			cs.setInt(4, record.getReimbTypeId());
			
			cs.execute();
			
			
		}
		
	}
	
	public static void SubmitReimburementDecision(User user, int reimburementId, int statusId) throws SQLException, UserNotAuthorizedForThisRequestException {
		
		if(user.getRole() != 2) throw new UserNotAuthorizedForThisRequestException();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String query = "{ call SET_MANAGER_REIMB_DECISION(?,?,?) }";
			CallableStatement cs = conn.prepareCall(query);
			cs.setInt(1,reimburementId);
			cs.setInt(2, user.getUserId());
			cs.setInt(3, statusId);
			cs.execute();
		}
	}
}
