package com.btl.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.btl.entity.Admin;
import com.btl.provider.Provider;

public class AdminDaoImplement implements IDao<Admin>{
	private static AdminDaoImplement instance;
	
	private AdminDaoImplement() {
	}

	public static AdminDaoImplement getInstance() {
		if (instance == null) {
			instance = new AdminDaoImplement();
		}
		return instance;
	}
	
	@Override
	public List<Admin> getAll() {
		List<Admin> list = new ArrayList<>();
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getAdmin}");
			while (rs.next()) {
				list.add(new Admin(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(Admin obj) {
		// TODO Auto-generated method stub 	sp_createAdmin(?,?,?)
		if (Provider.executeUpdate("{CALL sp_createAdmin(?,?,?)}", obj.getName(),obj.getEmail(),obj.getPassword()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Admin obj) {
		// TODO Auto-generated method stub sp_updateAdmin(?,?,?,?)
		if (Provider.executeUpdate("{CALL sp_updateAdmin(?,?,?,?)}", obj.getName(),obj.getEmail(),obj.getPassword(),obj.getId()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Admin getById(Object id) {
		// TODO Auto-generated method stub  sp_getAdminById(?)
		Admin admin = null;
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_getAdminById(?)}", id);
			while(rs.next()) {
				admin = new Admin(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}
	
	public Admin login(String email, String password) {
		// sp_loginAdmin(?,?) email password
		Admin admin = null;
		try {
			ResultSet rs = Provider.executeQuery("{CALL sp_loginAdmin(?,?)}", email,password);
			while (rs.next()) {
				admin = new Admin(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}
}
