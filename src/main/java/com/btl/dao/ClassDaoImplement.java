package com.btl.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.btl.database.ConnectionDatabase;
import com.btl.entity.Classroom;
import com.btl.entity.Pagination;
import com.btl.provider.Provider;

public class ClassDaoImplement implements IDao<Classroom>{
	private static ClassDaoImplement instance;
	
	private ClassDaoImplement() {
	}

	public static ClassDaoImplement getInstance() {
		if (instance == null) {
			instance = new ClassDaoImplement();
		}
		return instance;
	}
	@Override
	public List<Classroom> getAll() {
		// TODO Auto-generated method stub // sp_getAllClass
		List<Classroom> list = new ArrayList<>();
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getAllClass}");
			while (rs.next()) {
				list.add(new Classroom(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(Classroom obj) {
		// TODO Auto-generated method stub  sp_createClass(?,?)
		if (Provider.executeUpdate("{CALL sp_createClass(?,?)}", obj.getClassName(),obj.getStatus()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Classroom obj) {
		if (Provider.executeUpdate("{CALL sp_updateClass(?,?,?)}", obj.getClassName(),obj.getStatus(),obj.getId()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Object id) {
		if (Provider.executeUpdate("{CALL sp_deleteClass(?)}", id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Classroom getById(Object id) {
		Classroom clasroom = null;
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getClassById(?)}",id);
			while (rs.next()) {
				clasroom = new Classroom(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clasroom;
	}

	
	public Pagination<Classroom> getPagination(int records, int currentPage){ //getClassPaginate(?,?,?)
		Pagination<Classroom> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Classroom> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL getClassPaginate(?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.registerOutParameter(3, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Classroom(rs));
			}
			int totalPage = callableStatement.getInt(3);
//			Collections.sort(null);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	public Pagination<Classroom> search(int records, int currentPage, String search){
		Pagination<Classroom> pagination = null;
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		List<Classroom> list = new ArrayList<>();
		try {
			callableStatement = ConnectionDatabase.getConnection().prepareCall("{CALL sp_searchPaginateClass(?,?,?,?)}");
			callableStatement.setInt(1, records);
			callableStatement.setInt(2, currentPage);
			callableStatement.setString(3, "%"+search+"%");
			callableStatement.registerOutParameter(4, Types.INTEGER);
			rs = callableStatement.executeQuery();
			while(rs.next()) {
				list.add(new Classroom(rs));
			}
			int totalPage = callableStatement.getInt(4);
			pagination = new Pagination<>(totalPage, list, currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagination;
	}
	
	public int getTotalStudent(int id) {
		int total = 0;
		ResultSet rs = null;
		try {
			rs = Provider.executeQuery("{ CALL sp_getTotalStudent(?)}", id);
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
}
