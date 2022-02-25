package com.btl.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Subject {
	private int id;
	private String name;
	private int session;
	
	public Subject() {
	}

	public Subject(String name, int session) {
		this.name = name;
		this.session = session;
	}

	public Subject(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.session = rs.getInt("session");
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

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}
}
