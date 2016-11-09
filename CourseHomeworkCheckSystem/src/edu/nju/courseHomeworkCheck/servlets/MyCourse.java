package edu.nju.courseHomeworkCheck.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import edu.nju.courseHomeworkCheck.models.Course;

/**
 * Servlet implementation class MyCourse
 */
@WebServlet("/MyCourse")
public class MyCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource datasource = null;     
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyCourse() {
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
//		response.setContentType("text/html;charset=utf-8");
//		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		if(session==null){
			// Display the login page. If the cookie exists, set login
			response.sendRedirect(request.getContextPath() + "/Login");
		}
		else{
			searchMyCourse(request, response);
			ServletContext Context= getServletContext();
			
			@SuppressWarnings("unchecked")
			ArrayList<Course> list = (ArrayList<Course>) request.getAttribute("myCourses");

			int webCounter = Integer.parseInt((String) Context.getAttribute("webCounter"));
			int totalCounter = Integer.parseInt((String) Context.getAttribute("totalCounter"));
			int onlineCounter = Integer.parseInt((String) Context.getAttribute("onlineCounter"));
			int visitorCounter = totalCounter - onlineCounter;
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body bgcolor='#FFFACD'>");
			out.println("<p>总在线人数："+totalCounter+" 登录人数："+onlineCounter+" 游客人数:"+visitorCounter+
					" 网站总登录次数："+webCounter+"</p>");
	        out.println("<table width='650' border='0' >");
			out.println("<tr>");
			String imagePath = null;
			if(Integer.parseInt(String.valueOf(session.getAttribute("gender")))==1){
				imagePath = request.getContextPath()+"/image/boyPic.jpeg";
			}
			else{
				imagePath = request.getContextPath()+"/image/girlPic.jpeg";
			}
			out.println("<td width='80' height='80' background='"+imagePath+"'></td>"
					+ "<td width='650' height='80' background='"
					+ request.getContextPath() + "/image/top.jpg'>&nbsp;</td>");
			out.println("</tr>");
			out.println("</table>");
			
			out.println("<h3>Welcome " + session.getAttribute("nickname") + "!</h3>");
			
			out.println("<table bgcolor='#B0E0E6' style='font-weight:bold'>");
			out.println("<tbody><tr>");
			out.println("<td><a href='"+request.getContextPath() + "/BadHomeworkWarning"+"' style='text-decoration: none;'>首页</a></td>");
			out.println("<td style='BACKGROUND-POSITION: 50% 50%;background-image: url(image/verticalLine.png);BACKGROUND-REPEAT: no-repeat;' width='27'>&nbsp;&nbsp;</td>");
			out.println("<td><a href='"+request.getContextPath() + "/MyCourse"+"' style='text-decoration: none;'>我的课程</a></td>");
			out.println("<td style='BACKGROUND-POSITION: 50% 50%;background-image: url(image/verticalLine.png);BACKGROUND-REPEAT: no-repeat;' width='27'>&nbsp;&nbsp;</td>");
			out.println("<td><a href='"+request.getContextPath() + "/MyHomework"+"' style='text-decoration: none;'>我的作业</a></td>");
//			out.println("<td style='BACKGROUND-POSITION: 50% 50%;background-image: url(image/verticalLine.png);BACKGROUND-REPEAT: no-repeat;' width='27'>&nbsp;&nbsp;</td>");
//			out.println("<td name='Logout'><a href='http://localhost:8080"+request.getContextPath() + "/Login"+"' style='text-decoration: none;'>登出</a></td>");
			out.println("</tr></tbody>");
			out.println("</table>");
			
			out.println("<table border='2px' bordercolor='#00FF7F' cellspacing='0px' style='border-collapse:collapse;max-width:800px'>");
			out.println("<caption>课程情况</caption>");
			out.println("<tr>");
			out.println("<th>课程编号</th>");
			out.println("<th>课程名称</th>");
			out.println("<th>课程学期</th>");
			out.println("</tr>");
			if(list!=null){
				for (int i = 0; i < list.size(); i++) {
					Course c = list.get(i);
					out.println("<tr>");
					out.println("<td>"+c.getCourseid()+"</td>");
					out.println("<td><a href=''>"+c.getCourseName()+"</a></td>");
					out.println("<td>"+c.getCourseTerm()+"</td>");
					out.println("</tr>");
				}
			}
			out.println("</table>");
			
			out.println("<p>");
			out.println("</p>");
			out.println("<form method='GET' action='"
					+ response.encodeURL(request.getContextPath() + "/Login") + "'>");
			out.println("<input type='submit' name='Logout' value='登出'/>");
			out.println("</form>");
			out.println("</body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void searchMyCourse(HttpServletRequest req,HttpServletResponse resp){
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		try {
			connection = datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			HttpSession session = req.getSession(false);
			ArrayList<Course> courseList = new ArrayList<Course>();
			stmt = connection.prepareStatement("SELECT c.courseid,c.courseName,c.courseTerm "
					+ "FROM course c,courseselection cs "
					+ "WHERE cs.studentid=? and cs.courseid=c.courseid");
			stmt.setString(1, (String) session.getAttribute("userid"));
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				Course course = new Course();
				course.setCourseid(resultSet.getInt("courseid"));
				course.setCourseName(resultSet.getString("courseName"));
				course.setCourseTerm(resultSet.getString("courseTerm"));
				courseList.add(course);
			}
			req.setAttribute("myCourses", courseList);
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
