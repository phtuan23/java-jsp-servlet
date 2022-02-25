package com.btl.entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
	private String id;
	private String name;
	private int status;
	private boolean sex;
	private int classId;
	private Date birthday;
	
	public Student() {
	}

	public Student(String id, String name, int status, boolean sex, int classId, Date birthday) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.sex = sex;
		this.classId = classId;
		this.birthday = birthday;
	}
	
	

	public Student(ResultSet rs) throws SQLException {
		this.id = rs.getString("id");
		this.name = rs.getString("name");
		this.status = rs.getInt("status");
		this.sex = rs.getBoolean("sex");
		this.classId = rs.getInt("classId");
		this.birthday = rs.getDate("birthday");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
