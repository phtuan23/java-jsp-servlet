package com.btl.controller.classroom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.ClassDaoImplement;
import com.btl.entity.Classroom;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/classroom/update")
public class UpdateClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassDaoImplement dao;
	
    public UpdateClassServlet() {
    	dao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		Classroom cr = dao.getById(id);
		if (cr != null) {
			request.setAttribute("classroom", cr);
			request.getRequestDispatcher("/admin/classroom/update.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/classroom");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Notification notification = null;
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Classroom cr = dao.getById(id);
		
		if (cr != null) {
			String name = request.getParameter("name");
			int status = Integer.parseInt(request.getParameter("status"));
			
			if (name.equalsIgnoreCase("") || name.isBlank() || name.isEmpty()) {
				notification = new Notification("error", "Vui lòng nhập tên lớp", "failed");
				writer.write(gson.toJson(notification));
			}else {
				cr.setClassName(name);
				cr.setStatus(status);
				if (dao.update(cr)) {
					writer.write(gson.toJson("/MyProject/admin/classroom"));
				}else {
					notification = new Notification("error", "Lớp học đã tồn tại. Vui lòng thử lại", "failed");
					writer.write(gson.toJson(notification));
				}
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/classroom");
		}
	}

}
