package com.btl.controller.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.StudentDaoImplement;
import com.btl.entity.Student;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/student/delete")
public class DeleteStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDaoImplement dao;
	
    public DeleteStudentServlet() {
    	dao = StudentDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Student student = dao.getById(id);
		Notification notification = null;
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		
		if (student != null) {
			if (dao.delete(id)) {
				notification = new Notification("success", "Xoá thành công", "success");
				writer.write(gson.toJson(notification));
			}else {
				notification = new Notification("error", "Không thể xoá sinh viên hiện tại", "failed");
				writer.write(gson.toJson(notification));
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/student" );
		}
	}
}
