package com.btl.controller.student;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.ClassDaoImplement;
import com.btl.dao.StudentDaoImplement;
import com.btl.entity.Classroom;
import com.btl.entity.Pagination;
import com.btl.entity.Student;

@WebServlet("/admin/student")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDaoImplement dao;
	private ClassDaoImplement cDao;
	
    public StudentServlet() {
    	dao = StudentDaoImplement.getInstance();
    	cDao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Pagination<Student> pagination = null;
		int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		List<Classroom> classes = cDao.getAll();
		
		if (request.getParameter("search") != null) {
			String search = request.getParameter("search");
			pagination = dao.search(6, currentPage, search);
			request.setAttribute("search", search);
		}else if(request.getParameter("class_id") != null){
			int classId = Integer.parseInt(request.getParameter("class_id"));
			pagination = dao.studentByClass(6, currentPage, classId);
			request.setAttribute("classId", classId);
		}
		if(request.getParameter("class_id") != null && request.getParameter("search") != null) {
			String search = request.getParameter("search");
			int classId = Integer.parseInt(request.getParameter("class_id"));
			request.setAttribute("search", search);
			request.setAttribute("classId", classId);
			pagination = dao.studentSearchClassId(6, currentPage, search, classId);
			System.out.println("ok");
		}
		
		request.setAttribute("pagination", pagination);
		request.setAttribute("dao", dao);
		request.setAttribute("cDao", cDao);
		request.setAttribute("classes", classes);
		request.getRequestDispatcher("/admin/student/student.jsp").forward(request, response);
	}
}
