package com.btl.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.btl.database.ConnectionDatabase;
import com.btl.entity.Classroom;
import com.btl.entity.Pagination;
import com.btl.entity.Student;
import com.btl.provider.Provider;

public class StudentDaoImplement implements IDao<Student>{
	private static StudentDaoImplement instance;
	
	private StudentDaoImplement() {
	}

	public static StudentDaoImplement getInstance() {
		if (instance == null) {
			instance = new StudentDaoImplement();
		}
		return instance;
	}
	
	@Override
	public List<Student> getAll() {
		List<Student> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = Provider.executeQuery("{CALL sp_getAllStudent}");
			while (rs.next()) {
				list.add(new Student(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(Student obj) {
		// TODO Auto-generated method stub //sp_createStudent(?,?,?,?,?,?)
		if (Provider.executeUpdate("{CALL sp_createStudent(?,?,?,?,?,?)}", 
				obj.getId(),obj.getName(),obj.getStatus(),obj.isSex(),obj.getClassId(),obj.getBirthday()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Student obj) {
		if (Provider.executeUpdate("{CALL sp_updateStudent(?,?,?,?,?,?)}", 
				obj.getName(),obj.getStatus(),obj.isSex(),obj.getClassId(),obj.getBirthday(),obj.getId()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Object id) {
		// TODO Auto-generated method stub sp_deleteStudent(?)
		if (Provider.executeUpdate("{CALL sp_deleteStudent(?)}", id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Student getById(Object id) {
		ResultSet rs = null;
		Student s = null;
		try {
			rs = Provider.executeQuery("{CALL sp_getStudentById(?)}",id);
			while (rs.next()) {
				s = new Student(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public Pagination<Student> getPagination(int records, int currentPage){ //getStudentPaginate(?,?,?)
		Pagination<Student> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Student> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL getStudentPaginate(?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.registerOutParameter(3, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Student(rs));
			}
			int totalPage = callableStatement.getInt(3);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	public String getClassName(int classId) throws SQLException {
		ResultSet rs = Provider.executeQuery("select className from Class where id = ?", classId);
		String name = null;
		while (rs.next()) {
			name = rs.getString(1);
		}
		return name;
	}
	
	//sp_searchStudent(?,?,?,?)
	public Pagination<Student> search(int records, int currentPage, String search){ //getStudentPaginate(?,?,?)
		Pagination<Student> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Student> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL sp_searchStudent(?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setString(3, "%"+search+"%");
			callableStatement.registerOutParameter(4, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Student(rs));
			}
			int totalPage = callableStatement.getInt(4);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	//sp_getStudentByClass
	
	public Pagination<Student> studentByClass(int records, int currentPage, int classId){ //sp_getStudentByClass(?,?,?)
		Pagination<Student> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Student> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL sp_getStudentByClass(?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setInt(3, classId);
			callableStatement.registerOutParameter(4, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Student(rs));
			}
			int totalPage = callableStatement.getInt(4);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	////sp_getStudentPagination(?,?,?,?,?) record page search class outpr
	public Pagination<Student> studentSearchClassId(int records, int currentPage,String search, int classId){
		Pagination<Student> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Student> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL sp_getStudentPagination(?,?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setString(3, "%"+search+"%");
			callableStatement.setInt(4, classId);
			callableStatement.registerOutParameter(5, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Student(rs));
			}
			int totalPage = callableStatement.getInt(5);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	
	public List<Student> getStudentByClass(int classId){
		//sp_studentByClassId(?)
		List<Student> list = new ArrayList<>();
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_studentByClassId(?)}", classId);
			while (rs.next()) {
				list.add(new Student(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
