package edu.nju.courseHomeworkCheck.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.nju.courseHomeworkCheck.factory.DaoFactory;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		boolean cookieFound = false;
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			// Look through all the cookies and see if the
			// cookie with the login info is there.
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("LoginCookie")) {
					cookieFound = true;
					break;
				}
			}
		}
		
		if(session.getAttribute("userid")==null){
			String loginValue = request.getParameter("userid");
			boolean isLoginAction = (null == loginValue) ? false : true;
			if (isLoginAction) { // User is logging in
				if (cookieFound) { // If the cookie exists update the value only
				// if changed
					if (!loginValue.equals(cookie.getValue())) {
						cookie.setValue(loginValue);
						response.addCookie(cookie);
					}
				} else {
					// If the cookie does not exist, create it and set value
					cookie = new Cookie("LoginCookie", loginValue);
					cookie.setMaxAge(Integer.MAX_VALUE);
					// System.out.println("Add cookie");
					response.addCookie(cookie);
				}

				// create a session to show that we are logged in
				boolean loginRes = DaoFactory.getStudentDao()
						.checkLogin(loginValue,request.getParameter("password"));
				if(loginRes){
					session = request.getSession(true);
					session.setMaxInactiveInterval(5*60);
					session.setAttribute("userid", loginValue);
					
					response.sendRedirect(request.getContextPath() + "/WorkNoticeServlet");
				}
				else{
					response.sendRedirect(request.getContextPath() + "/jsp/loginError.jsp");
				}
				
			} else {
				// Display the login page. If the cookie exists, set login
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			
		}
		else{
			response.sendRedirect(request.getContextPath() + "/WorkNoticeServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
