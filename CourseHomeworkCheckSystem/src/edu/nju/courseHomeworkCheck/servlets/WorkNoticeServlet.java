package edu.nju.courseHomeworkCheck.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.nju.courseHomeworkCheck.action.business.HomeworkListBean;
import edu.nju.courseHomeworkCheck.factory.DaoFactory;
import edu.nju.courseHomeworkCheck.models.Homework;

/**
 * Servlet implementation class WorkNoticeServlet
 */
@WebServlet("/WorkNoticeServlet")
public class WorkNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null){
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		}
		else{
			ServletContext context = getServletContext();
			String studentid = String.valueOf(session.getAttribute("userid"));
			HomeworkListBean listHomeworkUpload = new HomeworkListBean();
			listHomeworkUpload.setHomeworkList(DaoFactory.getHomeworkDao().findUnUploadByStudent(studentid));
			HomeworkListBean listHomeworkGrade = new HomeworkListBean();
			listHomeworkGrade.setHomeworkList(DaoFactory.getHomeworkDao().findFailedGradeByStudent(studentid));
			try {
				request.setAttribute("studentid", studentid);
				session.setAttribute("listHomeworkUpload", listHomeworkUpload);
				session.setAttribute("listHomeworkGrade", listHomeworkGrade);
				context.getRequestDispatcher("/jsp/workNotice.jsp").forward(
						request, response);
			} catch (ServletException e) {
				// System error - report error 500 and message
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"This is a ServletException.");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
