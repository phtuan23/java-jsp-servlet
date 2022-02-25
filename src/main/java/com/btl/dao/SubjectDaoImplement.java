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
import com.btl.entity.Subject;
import com.btl.provider.Provider;

public class SubjectDaoImplement implements IDao<Subject>{

	private static SubjectDaoImplement instance;
	
	private SubjectDaoImplement() {
	}
	
	public static SubjectDaoImplement getInstance() {
		if (instance == null) {
			instance = new SubjectDaoImplement();
		}
		return instance;
	}

	@Override
	public List<Subject> getAll() {
		// TODO Auto-generated method stub 			sp_getSubject()
		List<Subject> list = new ArrayList<>();
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getSubject()}");
			while(rs.next()) {
				list.add(new Subject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(Subject obj) {
		// TODO Auto-generated method stub		sp_createSubject(?,?)
		if (Provider.executeUpdate("{CALL sp_createSubject(?,?)}", obj.getName(),obj.getSession()) > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Subject obj) {
		// TODO Auto-generated method stub			sp_updateSubject(?,?,?)
		if (Provider.executeUpdate("{CALL sp_updateSubject(?,?,?)}", obj.getName(),obj.getSession(),obj.getId()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Object id) {
		// TODO Auto-generated method stub			sp_deleteSubject(?)
		if (Provider.executeUpdate("{CALL sp_deleteSubject(?)}", id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Subject getById(Object id) {
		// TODO Auto-generated method stub			sp_getSubjectById(?)
		Subject subject = null;
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getSubjectById(?)}", id);
			while(rs.next()) {
				subject = new Subject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return subject;
	}

	
	// getSubjectPaginate(?,?,?)
	
	public Pagination<Subject> getPagination(int records, int currentPage){
		Pagination<Subject> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Subject> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL getSubjectPaginate(?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.registerOutParameter(3, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Subject(rs));
			}
			int totalPage = callableStatement.getInt(3);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	// sp_searchSubject(?,?,?,?)
	
	public Pagination<Subject> search(int records, int currentPage, String search){
		Pagination<Subject> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Subject> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL sp_searchSubject(?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setString(3, "%"+search+"%");
			callableStatement.registerOutParameter(4, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Subject(rs));
			}
			int totalPage = callableStatement.getInt(4);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	public List<Subject> getListSubject(int classId){
		List<Subject> list = new ArrayList<>();
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getSubjectNotInsertScore(?)}", classId);
			while (rs.next()) {
				list.add(new Subject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	

}
