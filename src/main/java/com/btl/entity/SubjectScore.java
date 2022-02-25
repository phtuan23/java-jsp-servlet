package com.btl.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectScore {
	private String studentId;
	private int subjectId;
	private float score;
	
	public SubjectScore() {
	}

	public SubjectScore(String studentId, int subjectId, float score) {
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.score = score;
	}

	public SubjectScore(ResultSet rs) throws SQLException {
		this.studentId = rs.getString("studentId");
		this.subjectId = rs.getInt("subjectId");
		this.score = rs.getFloat("score");
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	
}
