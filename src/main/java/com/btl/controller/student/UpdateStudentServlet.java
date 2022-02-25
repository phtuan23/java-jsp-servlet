package com.btl.controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.ClassDaoImplement;
import com.btl.dao.StudentDaoImplement;
import com.btl.entity.Classroom;
import com.btl.entity.Student;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/student/update")
public class UpdateStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDaoImplement sDao;
	private ClassDaoImplement cDao;

    public UpdateStudentServlet() {
    	sDao = StudentDaoImplement.getInstance();
    	cDao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		Student student = sDao.getById(id);
		
		if (student != null) {
			List<Classroom> classes = cDao.getAll();
			Collections.sort(classes,new Comparator<Classroom>() {
				@Override
				public int compare(Classroom o1, Classroom o2) {
					return - (o1.getId() - o2.getId());
				}
			});
			request.setAttribute("student", student);
			request.setAttribute("classes", classes);
			request.getRequestDispatcher("/admin/student/update.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/student");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Notification notification = null;
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> errors = new HashMap<>();
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		int status = Integer.parseInt(request.getParameter("status"));
		String _sex = request.getParameter("sex");
		int classId = Integer.parseInt(request.getParameter("classId"));
		String _date = request.getParameter("birthday");
		
		Student student = sDao.getById(id);
		
		if (student != null) {
			student.setStatus(status);
			
			if (name.equalsIgnoreCase("") || name.isBlank() || name.isEmpty()) {
				errors.put("name", "Vui lòng nhập tên sinh viên");
			}else {
				student.setName(name);
			}
			
			//validate classId
			
			if (classId == 0) {
				errors.put("classId", "Vui lòng chọn lớp học");
			}else {
				student.setClassId(classId);
			}
			
			// validate sex
			if (_sex.equalsIgnoreCase("")) {
				errors.put("sex", "Vui lòng chọn giới tính");
			}else {
				student.setSex(Integer.parseInt(_sex) == 0 ? false : true);
			}
			
			// validate birthday
			if (_date.equalsIgnoreCase("") || _date.isBlank() || _date.isEmpty()) {
				errors.put("birthday", "Vui lòng nhập ngày sinh");
				System.out.println(errors.get("birthday"));
			}else {
				try {
					Date date = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(_date).getTime());
					student.setBirthday(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if (errors.isEmpty()) {
				if (sDao.update(student)) {
					writer.write(gson.toJson(new Notification("success","/MyProject/admin/student","success")));
				}else {
					notification = new Notification("error", "Có lỗi. Vui lòng thử lại.", "failed");
					writer.write(gson.toJson(notification));
				}
			}else {
				writer.write(gson.toJson(errors));
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/student");
		}
	}

}
