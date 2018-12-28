package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	/*
	 * Connections are the vital tool in JDBC
	 * We use the ConnectionFactory to establish a connection
	 * with the database
	 * 
	 * This will use a lazy singleton design pattern to return
	 * the same single connectionFactory instance each time one
	 * is requested so that we can montitor the amount of connections
	 * that our connecionFactory generates
	 * 
	 * In order to establish a connection, we need four things - Driver, DB location (URL), DB username, db password
	 * 
	 * */
	
	private static ConnectionFactory cf = null;
	
	private ConnectionFactory() {
		
	}
	
	public static synchronized ConnectionFactory getInstance() {
		if(cf == null) cf = new ConnectionFactory();
		return cf;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		Properties prop = new Properties();
		String path = "src/main/resources/database_properties";
		
		try {
			prop.load(new FileReader(path));
			//the following line of code uses reflection on the .properties
			// file in order to instantiate our driver
			// class listed in the file
			Class.forName(prop.getProperty("driver"));
			
			/*
			 * The DriverManager provides a basic service for managing a set of JDBC
			 * drivers. As part of its initialization, the DriverManger class will attempt to load the driver class referenced previously 
			 * 
			 * */
			conn = DriverManager.getConnection(
									prop.getProperty("url"),
									prop.getProperty("usr"),
									prop.getProperty("pwd")
			);
			
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