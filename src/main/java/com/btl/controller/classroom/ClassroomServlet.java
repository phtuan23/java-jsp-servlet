package com.btl.controller.classroom;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.ClassDaoImplement;
import com.btl.entity.Classroom;
import com.btl.entity.Pagination;

@WebServlet("/admin/classroom")
public class ClassroomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClassDaoImplement dao;
    
    public ClassroomServlet() {
    	dao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		Pagination<Classroom> pagination = null;
		if (request.getParameter("search") != null) {
			String search = request.getParameter("search");
			pagination = dao.search(6, currentPage, search);
			request.setAttribute("search", search);
		}else {
			pagination = dao.getPagination(6, currentPage);			
		}
		request.setAttribute("dao", dao);
		request.setAttribute("pagination", pagination);
		request.getRequestDispatcher("/admin/classroom/classroom.jsp").forward(request, response);
	}
}
