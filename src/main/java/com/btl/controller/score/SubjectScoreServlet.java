package com.btl.controller.score;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.ClassDaoImplement;
import com.btl.dao.StudentDaoImplement;
import com.btl.dao.SubjectDaoImplement;
import com.btl.dao.SubjectScoreDaoImp;
import com.btl.entity.Pagination;
import com.btl.entity.SubjectScore;

@WebServlet("/admin/score")
public class SubjectScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubjectScoreDaoImp scoreDao;
	private StudentDaoImplement studentDao;
	private SubjectDaoImplement subjectDao;
	private ClassDaoImplement classDao;
	
    public SubjectScoreServlet() {
    	scoreDao = SubjectScoreDaoImp.getInstance();
    	studentDao = StudentDaoImplement.getInstance();
    	subjectDao = SubjectDaoImplement.getInstance();
    	classDao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Pagination<SubjectScore> pagination = null;
		int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		
		if (request.getParameter("search") != null) {
			String studentId = request.getParameter("search");
			pagination = scoreDao.search(6, currentPage, studentId);
			request.setAttribute("search", studentId);
		}
		
		if (request.getParameter("classId") != null && request.getParameter("subjectId") != null) {
			if (request.getParameter("classId") != "") {
				int classId = Integer.parseInt(request.getParameter("classId"));
				pagination = scoreDao.getPagination(6, currentPage, classId);
				request.setAttribute("classId", classId);
				System.out.println(pagination.getTotalPage());
			}else if(request.getParameter("subjectId") != ""){
				int subjectId = Integer.parseInt(request.getParameter("subjectId"));
				pagination = scoreDao.getScoreBySubject(6, currentPage, subjectId);
				request.setAttribute("subjectId", subjectId);
			}
			if (request.getParameter("classId") != "" && request.getParameter("subjectId") != "") {
				int classId = Integer.parseInt(request.getParameter("classId"));
				int subjectId = Integer.parseInt(request.getParameter("subjectId"));
				pagination = scoreDao.getScore(subjectId, classId);
				request.setAttribute("classId", classId);
				request.setAttribute("subjectId", subjectId);
			}
		}
		
		
		request.setAttribute("pagination", pagination);
		request.setAttribute("classDao", classDao);
		request.setAttribute("studentDao", studentDao);
		request.setAttribute("subjectDao", subjectDao);
		request.getRequestDispatcher("/admin/score/subject-score.jsp").forward(request, response);
	}
}
