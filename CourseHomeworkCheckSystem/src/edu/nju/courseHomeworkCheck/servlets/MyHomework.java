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
import edu.nju.courseHomeworkCheck.models.Homework;

/**
 * Servlet implementation class MyHomework
 */
@WebServlet("/MyHomework")
public class MyHomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource datasource = null;     
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyHomework() {
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
			searchMyHomework(request, response);
			ServletContext Context= getServletContext();
			
			@SuppressWarnings("unchecked")
			ArrayList<Homework> list = (ArrayList<Homework>) request.getAttribute("myHomework");

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
			out.println("<caption>作业情况</caption>");
			out.println("<tr>");
			out.println("<th>作业编号</th>");
			out.println("<th>课程名称</th>");
			out.println("<th>作业标题</th>");
			out.println("<th>截止时间</th>");
			out.println("<th>上传时间</th>");
			out.println("<th>完成情况</th>");
			out.println("<th>作业评分</th>");
			out.println("</tr>");
			if(list!=null){
				for (int i = 1; i <= list.size(); i++) {
					Homework hw = list.get(i-1);
					out.println("<tr>");
					out.println("<td>"+i+"</td>");
					out.println("<td>"+hw.getCourse().getCourseName()+"</td>");
					out.println("<td>"+hw.getHomeworkTitle()+"</td>");
					out.println("<td>"+hw.getDueTime()+"</td>");
					out.println("<td>"+hw.getUploadTime()+"</td>");
					out.println("<td>"+hw.getCompletition()+"</td>");
					int grade = hw.getGrade();
					if(grade<60){
						out.println("<td style = 'color:red'>"+hw.getGrade()+"</td>");
					}
					else{
						out.println("<td>"+hw.getGrade()+"</td>");
					}
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
	
	private void searchMyHomework(HttpServletRequest req,HttpServletResponse resp){
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
			ArrayList<Homework> homeworkList = new ArrayList<Homework>();
			stmt = connection.prepareStatement("SELECT t.homeworkid,t.courseName,t.homeworktitle,t.dueTime,hg.completition,hg.uploadTime,hg.grade "
					+ "FROM "
					+ "(SELECT cs.studentid,h.homeworkid,c.courseName, h.homeworktitle,h.dueTime "
					+ "FROM courseselection cs,homework h,course c "
					+ "WHERE cs.studentid=? "
					+ "AND cs.courseid=h.courseid "
					+ "AND cs.courseid=c.courseid) t LEFT JOIN homeworkgrade hg ON t.homeworkid=hg.homeworkid AND t.studentid=hg.studentid");
//			stmt = connection.prepareStatement("SELECT hw.homeworkid,c.courseName,hw.homeworktitle"
//					+ ",hw.dueTime,hwg.uploadTime,hwg.completition,hwg.grade "
//					+ "FROM homework hw,homeworkgrade hwg,course c "
//					+ "WHERE hwg.studentid=? AND hwg.homeworkid=hw.homeworkid AND c.courseid=hw.courseid");
			stmt.setString(1, (String) session.getAttribute("userid"));
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				Homework work = new Homework();
				work.setHomeworkid(resultSet.getInt("homeworkid"));
				Course c = new Course();
				c.setCourseName(resultSet.getString("courseName"));
				work.setCourse(c);
				work.setHomeworkTitle(resultSet.getString("homeworktitle"));
				work.setDueTime(resultSet.getString("dueTime"));
				work.setUploadTime(resultSet.getString("uploadTime"));
				work.setCompletition(resultSet.getString("completition"));
				work.setGrade(resultSet.getInt("grade"));
				homeworkList.add(work);
			}
			req.setAttribute("myHomework", homeworkList);
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
