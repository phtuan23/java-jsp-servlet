package com.btl.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Classroom {
	private int id;
	private String className;
	private int status;
	private Date createdDate;
	
	public Classroom() {
	}

	public Classroom(String className, int status) {
		this.className = className;
		this.status = status;
	}

	public Classroom(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.className = rs.getString("className");
		this.status = rs.getInt("status");
		this.createdDate = rs.getDate("createdDate");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
