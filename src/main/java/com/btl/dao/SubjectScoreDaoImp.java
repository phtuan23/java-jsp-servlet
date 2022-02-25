package com.btl.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.btl.database.ConnectionDatabase;
import com.btl.entity.Pagination;
import com.btl.entity.Student;
import com.btl.entity.Subject;
import com.btl.entity.SubjectScore;
import com.btl.provider.Provider;

public class SubjectScoreDaoImp implements IDao<SubjectScore>{
	private static SubjectScoreDaoImp instance;
	
	private SubjectScoreDaoImp() {
	}
	
	public static SubjectScoreDaoImp getInstance() {
		if (instance == null) {
			instance = new SubjectScoreDaoImp();
		}
		return instance;
	}

	@Override
	public List<SubjectScore> getAll() {
		List<SubjectScore> list = new ArrayList<>();
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getScore}");
			while (rs.next()) {
				list.add(new SubjectScore(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(SubjectScore obj) {
		// TODO Auto-generated method stub
		if (Provider.executeUpdate("{CALL sp_addScore(?,?,?)}", obj.getStudentId(),obj.getSubjectId(),obj.getScore()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(SubjectScore obj) {
		if (Provider.executeUpdate("{CALL sp_updateScore(?,?,?)}", obj.getStudentId(),obj.getSubjectId(),obj.getScore()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Object id) {
		return false;
	}

	@Override
	public SubjectScore getById(Object id) {
		return null;
	}
	
	public List<SubjectScore> getAllScore(Object id){
		List<SubjectScore> list = new ArrayList<>();
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_checkScore(?)}", id);
			while (rs.next()) {
				list.add(new SubjectScore(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public SubjectScore getById(Object studentId, Object subjectId) {
		// TODO Auto-generated method stub sp_getById(?,?)
		SubjectScore ss = null;
		try {
			ResultSet  rs = Provider.executeQuery("{CALL sp_getById(?,?)}", studentId, subjectId);
			while (rs.next()) {
				ss = new SubjectScore(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ss;
	}
	
	public Pagination<SubjectScore> getPagination(int records, int currentPage, int classId){
		Pagination<SubjectScore> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<SubjectScore> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL sp_scoreByClass(?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setInt(3, classId);
			callableStatement.registerOutParameter(4, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new SubjectScore(rs));
			}
			int totalPage = callableStatement.getInt(4);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	

	
	public Pagination<SubjectScore> search(int records, int currentPage, String search){
		Pagination<SubjectScore> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<SubjectScore> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL sp_searchScore(?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setString(3, search);
			callableStatement.registerOutParameter(4, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new SubjectScore(rs));
			}
			int totalPage = callableStatement.getInt(4);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	public Pagination<SubjectScore> getScoreBySubject(int records, int currentPage, int subjetId){
		Pagination<SubjectScore> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<SubjectScore> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL getScoreBySubject(?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setInt(3, subjetId);
			callableStatement.registerOutParameter(4, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new SubjectScore(rs));
			}
			int totalPage = callableStatement.getInt(4);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	public Pagination<SubjectScore> getScore(int subjetId, int classId){
		ResultSet rs = null;
		List<SubjectScore> list = new ArrayList<>();
		try {
			rs = Provider.executeQuery("{CALL sp_getScoreByClassAndSub(?,?)}", subjetId,classId);
			while (rs.next()) {
				list.add(new SubjectScore(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Pagination<SubjectScore>(1, list, 1);
	}
}
