package com.btl.controller.score;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btl.dao.StudentDaoImplement;
import com.btl.dao.SubjectDaoImplement;
import com.btl.dao.SubjectScoreDaoImp;
import com.btl.entity.Student;
import com.btl.entity.Subject;
import com.btl.entity.SubjectScore;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/score/update")
public class UpdateScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDaoImplement studentDao;
	private SubjectScoreDaoImp sDao;
	private SubjectDaoImplement subjectDao;
	
    public UpdateScoreServlet() {
    	studentDao = StudentDaoImplement.getInstance();
    	sDao = SubjectScoreDaoImp.getInstance();
    	subjectDao = SubjectDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String studentId = request.getParameter("id");
		int subjectId = Integer.parseInt(request.getParameter("subject"));
		
		SubjectScore subjectScore = sDao.getById(studentId, subjectId);
		
		if (subjectScore != null) {
			Student student = studentDao.getById(studentId);
			Subject subject = subjectDao.getById(subjectId);
			request.setAttribute("subjectScore", subjectScore);
			request.setAttribute("student", student);
			request.setAttribute("subject", subject);
			request.getRequestDispatcher("/admin/score/update.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/admin/score");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		
		String studentId = request.getParameter("studentId");
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		SubjectScore subjectScore = sDao.getById(studentId, subjectId);
		
		if (subjectScore != null) {
			String _score = request.getParameter("score");
			
			if (_score.equalsIgnoreCase("") || _score.isBlank() || _score.isEmpty()) {
				writer.write(gson.toJson(new Notification("error", "Vui lòng nhập điểm.", "failed")));
			}else {
				try {
					float score = Float.parseFloat(_score);
					if (score < 0 || score > 10) {
						writer.write(gson.toJson(new Notification("error", "Điểm từ 0 - 10", "failed")));
					}else {
						subjectScore.setScore(score);
						if (sDao.update(subjectScore)) {
							writer.write(gson.toJson(new Notification("success", "Cập nhật điểm thành công", "success")));
						}else {
							writer.write(gson.toJson(new Notification("error", "Có lỗi. Vui lòng thử lại.", "failed")));
						}
					}
				} catch (Exception e) {
					writer.write(gson.toJson(new Notification("error", "Điểm không hợp lệ", "failed")));
				}
			}
		}
	}

}
