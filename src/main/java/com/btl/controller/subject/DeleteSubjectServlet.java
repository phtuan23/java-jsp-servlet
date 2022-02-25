package com.btl.controller.subject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.SubjectDaoImplement;
import com.btl.entity.Subject;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/subject/delete")
public class DeleteSubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectDaoImplement dao;
	
    public DeleteSubjectServlet() {
    	dao = SubjectDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Subject subject = dao.getById(id);
		
		if (subject != null) {
			PrintWriter writer = response.getWriter();
			Gson gson = new Gson();
			
			if (dao.delete(id)) {
				writer.write(gson.toJson(new Notification("success", "Xoá thành công", "success")));
			}else {
				writer.write(gson.toJson(new Notification("error", "Không thể xoá môn học hiện tại", "failed")));
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/subject");
		}
	}
}
