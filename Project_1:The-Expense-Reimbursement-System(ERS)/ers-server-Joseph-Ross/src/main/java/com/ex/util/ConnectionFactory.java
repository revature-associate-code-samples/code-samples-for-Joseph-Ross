package com.ex.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
private static ConnectionFactory cf = null;
	
	private ConnectionFactory() {	
	}
	
	public static synchronized ConnectionFactory getInstance() {
			if(cf == null) cf = new ConnectionFactory();
			return cf;
	}
	
	/*
	 * Connection - one of the core interfaces in the JDBC API
	 * - manages our connection to (session with) the database
	 * - allows us to execute SQL statements and return results 
	 * - has information about DB tables, stored procedures, and 
	 * all other related db objects. 
	 */
	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		//String path = "C:/Users/Genesis/my_git_repos/1810-oct22/Week2/jdbc/bookstore-jdbc/src/main/resources/database.properties";
		String path = "C:/Users/joeth/Desktop/myGitRepos/1810-oct22/Joseph_Ross_Code/eclipse-workspaces/Week5Final/ProjectOne/src/main/resources/database.properties";
		
			try {
				prop.load(new FileReader(path));
				//the following line of code uses reflection and the 
				// .properties file in order to instantiate our driver
				//  class listed in the file
				Class.forName(prop.getProperty("driver"));
				/*
				 * The DriverManager provides a basic service for 
				 * managing a set of JDBC drivers. As part of its 
				 * initialization, the DriverManager class will 
				 * attempt to load the driver class referenced previously
				 */
				conn = DriverManager.getConnection(
						prop.getProperty("url"), 
						prop.getProperty("usr"), 
						prop.getProperty("pwd"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}

}
