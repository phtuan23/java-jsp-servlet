package com.btl.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.AdminDaoImplement;
import com.btl.entity.Admin;

@WebServlet(name = "AccountServlet", urlPatterns = { "/admin/account" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminDaoImplement dao;
	
    public AdminServlet() {
    	dao = AdminDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		List<Admin> admins = dao.getAll();
		request.setAttribute("admins", admins);
		request.getRequestDispatcher("/admin/admin/admin.jsp").forward(request, response);
	}
}
