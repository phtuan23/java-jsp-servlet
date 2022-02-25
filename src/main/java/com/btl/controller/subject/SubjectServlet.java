package com.btl.controller.subject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.SubjectDaoImplement;
import com.btl.entity.Pagination;
import com.btl.entity.Subject;

@WebServlet("/admin/subject")
public class SubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectDaoImplement dao;

	public SubjectServlet() {
		dao = SubjectDaoImplement.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		Pagination<Subject> pagination = null;
		
		if (request.getParameter("search") != null) {
			String search = request.getParameter("search");
			request.setAttribute("search", search);
			pagination = dao.search(6, currentPage, search);
		}else {			
			pagination = dao.getPagination(6, currentPage);
		}
		request.setAttribute("pagination", pagination);
		request.getRequestDispatcher("/admin/subject/subject.jsp").forward(request, response);
	}
}
