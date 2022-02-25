package com.btl.controller.classroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.ClassDaoImplement;
import com.btl.entity.Classroom;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/classroom/create")
public class InsertClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassDaoImplement dao;
	
    public InsertClassServlet() {
    	dao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("/admin/classroom/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Notification notification = null;
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		
		String name = request.getParameter("name");
		int status = Integer.parseInt(request.getParameter("status"));
		
		List<Classroom> classes = dao.getAll();
		
		if (name.equalsIgnoreCase("") || name.isBlank() || name.isEmpty()) {
			notification = new Notification("error", "Vui lòng nhập tên lớp", "failed");
			writer.write(gson.toJson(notification));
		}else {
			boolean exist = false;
			for (Classroom classroom : classes) {
				if (name.equalsIgnoreCase(classroom.getClassName())) {
					exist = true;
				}
			}
			if (exist) {
				notification = new Notification("error", "Lớp học đã tồn tại", "failed");
				writer.write(gson.toJson(notification));
			}else {
				if (dao.add(new Classroom(name, status))) {
					writer.write(gson.toJson("/MyProject/admin/classroom"));
				}else {
					notification = new Notification("error", "Thêm mới thất bại. Vui lòng thử lại", "failed");
					writer.write(gson.toJson(notification));
				}
			}
		}
	}
}
