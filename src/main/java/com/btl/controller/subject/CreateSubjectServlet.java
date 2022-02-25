package com.btl.controller.subject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.SubjectDaoImplement;
import com.btl.entity.Subject;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/subject/create")
public class CreateSubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private SubjectDaoImplement dao;
       
    public CreateSubjectServlet() {
    	dao = SubjectDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.getRequestDispatcher("/admin/subject/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> errors = new HashMap<>();
		List<Subject> subjects = dao.getAll();
		
		String name = request.getParameter("name");
		String _session = request.getParameter("session");
		Subject subject = new Subject();
		
		if (name.equalsIgnoreCase("") || name.isBlank() || name.isEmpty()) {
			errors.put("name", "Vui lòng nhập tên môn học");
		}else {
			boolean exist = false;
			for (Subject sub : subjects) {
				if (name.equalsIgnoreCase(sub.getName())) {
					exist = true;
				}
			}
			if (exist) {
				errors.put("name", "Môn học đã tồn tại.");
			}else {
				subject.setName(name);
			}
		}
		
		if (_session.equalsIgnoreCase("") || _session.isBlank() || _session.isEmpty()) {
			errors.put("session", "Vui lòng nhập số buổi học.");
		}else {
			try {
				int session = Integer.parseInt(_session);
				if (session <= 0) {
					errors.put("session", "Số buổi học phải lớn hơn 0.");
				}else {
					subject.setSession(session);
				}
			} catch (Exception e) {
				errors.put("session", "Số buổi học không hợp lệ.");
			}
		}
		
		if (errors.isEmpty()) {
			if (dao.add(subject)) {
				writer.write(gson.toJson("/MyProject/admin/subject"));				
			}else {
				writer.write(gson.toJson(new Notification("error", "Có lỗi. Vui lòng thử lại", "failed")));
			}
		}else {
			String html = "";
			for (var err : errors.entrySet()) {
				html += err.getValue() + "<br>";
			}
			writer.write(gson.toJson(new Notification("error", html, "failed")));
		}
	}
}
