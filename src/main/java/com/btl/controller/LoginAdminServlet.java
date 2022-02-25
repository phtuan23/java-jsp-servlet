package com.btl.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.btl.dao.AdminDaoImplement;
import com.btl.entity.Admin;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/login")
public class LoginAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminDaoImplement dao;
	
    public LoginAdminServlet() {
    	dao = AdminDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> errors = new HashMap<>();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email.equalsIgnoreCase("") || email.isBlank() || email.isEmpty()) {
			errors.put("email", "Vui lòng nhập email");
		}
		
		if (password.equalsIgnoreCase("") || password.isBlank() || password.isEmpty()) {
			errors.put("password", "Vui lòng nhập mật khẩu");
		}
		if (errors.isEmpty()) {
			Admin admin = dao.login(email, password);
			if (admin != null) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", admin);
				writer.write(gson.toJson(new Notification("success", request.getContextPath() + "/admin", "success")));
			}else {
				errors.put("login", "Email hoặc mật khẩu không đúng.");
				request.setAttribute("errors", errors);
				writer.write(gson.toJson(errors));
			}
		}else {
			request.setAttribute("errors", errors);
			writer.write(gson.toJson(errors));
		}
	}
}
