package com.btl.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.AdminDaoImplement;
import com.btl.entity.Admin;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/account/update")
public class UpdateAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminDaoImplement dao;
	
    public UpdateAdminServlet() {
    	dao = AdminDaoImplement.getInstance();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Admin admin = dao.getById(id);
		
		if (admin != null) {
			request.setAttribute("admin", admin);
			request.getRequestDispatcher("/admin/admin/update.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/account");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Admin admin = dao.getById(id);
		
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> errors = new HashMap<>();
		List<Admin> admins = dao.getAll();
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String cf_password = request.getParameter("cf_password");
		
		if (name.equalsIgnoreCase("") || name.isBlank() || name.isEmpty()) {
			errors.put("name", "Vui lòng nhập họ tên");
		}else {
			admin.setName(name);
		}
		
		if (email.isBlank() || email.equalsIgnoreCase("") || email.isEmpty()) {
			errors.put("email", "Vui lòng địa chỉ email");
		}else {
			Pattern pattern = Pattern.compile("^[A-Z0-9a-z]+@[A-Za-z]+\\.[A-Za-z]{2,6}$");
			if (pattern.matcher(email).matches()) {
				boolean exist = false;
				for (Admin adm : admins) {
					if (email.equalsIgnoreCase(adm.getEmail()) && adm.getId() != admin.getId()) {
						exist = true;
					}
				}
				if (exist) {
					errors.put("email", "Email đã tồn tại. Vui lòng thử lại");
				}else {
					admin.setEmail(email);
				}
			}else {
				errors.put("email", "Email không đúng định dạng");
			}
		}
		
		if (password.equalsIgnoreCase("") || password.isBlank() || password.isEmpty()) {
			errors.put("password", "Vui lòng nhập mật khẩu");
		}else {
			if (password.length() < 6 || password.length() > 30) {
				errors.put("password", "Mật khẩu từ 6 đến 30 ký tự");
			}else {
				admin.setPassword(password);
			}
		}
		
		if (cf_password.equalsIgnoreCase("") || cf_password.isBlank() || cf_password.isEmpty()) {
			errors.put("cf_password", "Vui lòng xác nhận mật khẩu");
		}else {
			if (!cf_password.equalsIgnoreCase(password)) {
				errors.put("cf_password", "Mật khẩu không chính xác");
			}
		}
		
		if (errors.isEmpty()) {
			if (dao.update(admin)) {
				writer.write(gson.toJson("/MyProject/admin/account"));
			}else {
				writer.write(gson.toJson(new Notification("error", "Có lỗi. Vui lòng thử lại.", "failed")));
			}
		}else {
			String html = "<p>";
			for (var err : errors.entrySet()) {
				html += err.getValue() + "<br>";
			}
			html += "</p>";
			writer.write(gson.toJson(new Notification("error", html, "failed")));
		}
	}

}
