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

@WebServlet("/admin/classroom/delete")
public class DeleteClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassDaoImplement dao;
	
    public DeleteClassServlet() {
    	dao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Classroom classroom = dao.getById(id);
		Notification notification = null;
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		if (classroom != null) {
			if (dao.delete(id)) {
				notification = new Notification("success", "Xoá thành công", "success");
				writer.write(gson.toJson(notification));
			}else {
				notification = new Notification("error", "Không thể xoá lớp học hiện tại", "error");
				writer.write(gson.toJson(notification));
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/classroom");
		}
	}
}
