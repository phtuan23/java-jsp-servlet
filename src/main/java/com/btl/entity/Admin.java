package com.btl.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
	private int id;
	private String name;
	private String email;
	private String password;
	
	public Admin() {
	}

	public Admin(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Admin(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.email = rs.getString("email");
		this.password = rs.getString("password");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
