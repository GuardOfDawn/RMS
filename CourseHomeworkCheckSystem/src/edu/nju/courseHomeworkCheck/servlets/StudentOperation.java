package edu.nju.courseHomeworkCheck.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentOperation
 */
@WebServlet("/StudentOperation")
public class StudentOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource datasource = null;     
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentOperation() {
        super();
    }
    
    public void init(){
		InitialContext jndiContext = null;
		
		Properties properties = new Properties();
		properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
		properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.naming.java.javaURLContextFactory");
		try {
			jndiContext = new InitialContext(properties);
			datasource = (DataSource) jndiContext
					.lookup("java:comp/env/jdbc/studentinfo");
		} catch (NamingException e) {
			e.printStackTrace();
		}

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
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
//		response.setContentType("text/html;charset=utf-8");
//		request.setCharacterEncoding("utf-8");

		if (session == null) {
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
				boolean loginRes = checkLogin(request, response);
				if(loginRes){
//					ServletContext Context= getServletContext();
//					int webCounter= Integer.parseInt((String) Context.getAttribute("webCounter"));
//					webCounter = webCounter+1;
//					int onlineCounter = Integer.parseInt((String) Context.getAttribute("onlineCounter"));
//					onlineCounter = onlineCounter+1;
//					Context.setAttribute("webCounter", Integer.toString(webCounter));
//					Context.setAttribute("onlineCounter", Integer.toString(onlineCounter));
					
					session = request.getSession(true);
					session.setMaxInactiveInterval(5*60);
					session.setAttribute("userid", loginValue);
					session.setAttribute("nickname", request.getAttribute("nickname"));
					session.setAttribute("gender", request.getAttribute("gender"));
					request.setAttribute("userid", loginValue);
					
					response.sendRedirect(request.getContextPath() + "/BadHomeworkWarning");
				}
				else{
					response.sendRedirect(request.getContextPath() + "/LoginError");
				}
				
			} else {
				// Display the login page. If the cookie exists, set login
				response.sendRedirect(request.getContextPath() + "/Login");
			}
		} else {
			request.setAttribute("userid", session.getAttribute("userid"));
			request.setAttribute("nickname", session.getAttribute("nickname"));
			request.setAttribute("gender", session.getAttribute("gender"));
			
			response.sendRedirect(request.getContextPath() + "/BadHomeworkWarning");
		}
	}
	
	private boolean checkLogin(HttpServletRequest req,
			HttpServletResponse resp) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;	
		try {
			connection = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt = connection.prepareStatement("select * from student where studentid=? and password=?");
			stmt.setString(1, (String) req.getParameter("userid"));
			stmt.setString(2, (String) req.getParameter("password"));
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				result = true;
				req.setAttribute("nickname", resultSet.getString("nickname"));
				req.setAttribute("gender", resultSet.getInt("gender"));
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
