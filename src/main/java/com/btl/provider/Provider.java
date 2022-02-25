package com.btl.provider;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.btl.database.ConnectionDatabase;

public class Provider {
	public static ResultSet executeQuery(String sql, Object...objects) {
		try {
			CallableStatement callableStatement = createCallableStatement(sql, objects);
			return callableStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int executeUpdate(String sql, Object...objects) {
		CallableStatement callableStatement = createCallableStatement(sql, objects);
		try {
			return callableStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private static CallableStatement createCallableStatement(String sql, Object...objects) {
		Connection connection = ConnectionDatabase.getConnection();
		CallableStatement callableStatement = null;
		try {
			callableStatement = connection.prepareCall(sql);
			if (objects.length > 0) {
				for (int i = 0; i < objects.length; i++) {
					callableStatement.setObject(i + 1, objects[i]);
				}
			}
			return callableStatement;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
