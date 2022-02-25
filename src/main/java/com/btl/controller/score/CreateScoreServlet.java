package com.btl.controller.score;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.btl.entity.Classroom;
import com.btl.entity.Student;
import com.btl.entity.Subject;
import com.btl.entity.SubjectScore;
import com.btl.notification.Notification;
import com.google.gson.Gson;

@WebServlet("/admin/score/create")
public class CreateScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDaoImplement studentDao;
	private SubjectScoreDaoImp sDao;
	private ClassDaoImplement classDao;
	private SubjectDaoImplement subjectDao;
	
    public CreateScoreServlet() {
    	studentDao = StudentDaoImplement.getInstance();
    	sDao = SubjectScoreDaoImp.getInstance();
    	classDao = ClassDaoImplement.getInstance();
    	subjectDao = SubjectDaoImplement.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		List<Classroom> classrooms = classDao.getAll();
		List<Student> students = null;
		List<Subject> subjects = null;
		
		if (request.getParameter("classId") != null) {
			int classId = Integer.parseInt(request.getParameter("classId"));
			students = studentDao.getStudentByClass(classId);
			subjects = subjectDao.getListSubject(classId);
		}
		
		request.setAttribute("classDao", classDao);
		request.setAttribute("subjects", subjects);
		request.setAttribute("students", students);
		request.setAttribute("classrooms", classrooms);
		request.getRequestDispatcher("/admin/score/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		Gson gson = new Gson();
		Map<String, String> errors = new HashMap<>();
		List<SubjectScore> subjectScores = new ArrayList<>();
		
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		String[] studentIds = request.getParameterValues("studentId[]");
		String[] list_score = request.getParameterValues("score[]");
		
		List<String> listStdId = Arrays.asList(studentIds);
		List<String> listScore = Arrays.asList(list_score);

		List<Float> lstScore = new ArrayList<>(); // list score finally
		
		
		//validate
		for (int i = 0; i < listScore.size(); i++) {
			Student student = studentDao.getById(listStdId.get(i));
			try {
				float _score = Float.parseFloat(listScore.get(i));
				if (_score < 0 || _score > 10) {
					errors.put(student.getId(), "Điểm trong hệ số 10. Vui lòng nhập lại");
				}else {
					lstScore.add(_score);
				}
			} catch (NumberFormatException e) {
				errors.put(student.getId(), "Điểm của sinh viên " + student.getId() + " - " + student.getName() + " không hợp lệ");
			}
		}
		
		
		// tạo đối tượng subjectScore
		for (int i = 0; i < lstScore.size(); i++) {
			subjectScores.add(new SubjectScore(listStdId.get(i), subjectId, lstScore.get(i)));
		}
		
		
		if (errors.isEmpty()) {
			for (var ss : subjectScores) {
				sDao.add(ss);
			}
			writer.write(gson.toJson(new Notification("success", request.getContextPath() + "/admin/score", "success")));
		}else {
			writer.write(gson.toJson(errors));
		}
	}
}
