package com.btl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
	private static Connection connection;

	private ConnectionDatabase() {
	}
	
	public static Connection getConnection() {
		String user = "sa";
		String pass = "123456";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=MarkManagement";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
