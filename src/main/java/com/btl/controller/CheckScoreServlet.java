package com.btl.controller;

import java.io.IOException;
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
import com.btl.dao.SubjectDaoImplement;
import com.btl.dao.SubjectScoreDaoImp;
import com.btl.entity.Student;
import com.btl.entity.SubjectScore;

@WebServlet("/check-score")
public class CheckScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDaoImplement studentDao;
	private SubjectScoreDaoImp scoreDao;
	private SubjectDaoImplement subjectDao;
	private ClassDaoImplement classDao;
	
    public CheckScoreServlet() {
        studentDao = StudentDaoImplement.getInstance();
        scoreDao = SubjectScoreDaoImp.getInstance();
        subjectDao = SubjectDaoImplement.getInstance();
        classDao = ClassDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		List<SubjectScore> subjectScore = null;
		Map<String, String> errors = new HashMap<>();
		Student student = null;
		String className = null;
		
		if (request.getParameter("id") != null) {
			String studentId = request.getParameter("id");
			if(studentId.equals("") || studentId.isBlank() || studentId.isEmpty()) {
				errors.put("error", "Vui lòng nhập mã sinh viên để tra cứu.");
			}
			if(errors.isEmpty()){
				student = studentDao.getById(studentId);
				if (student != null) {
					subjectScore = scoreDao.getAllScore(studentId);
					className = classDao.getById(student.getClassId()).getClassName();
				}else {
					errors.put("error", "Không tìm thấy sinh viên có mã " + "\"" + studentId + "\"");
				}
			}
		}
		
		request.setAttribute("subjectScore", subjectScore);
		request.setAttribute("student", student);
		request.setAttribute("subjectDao", subjectDao);
		request.setAttribute("className", className);
		request.setAttribute("errors", errors);
		request.getRequestDispatcher("check-score.jsp").forward(request, response);
	}
}
