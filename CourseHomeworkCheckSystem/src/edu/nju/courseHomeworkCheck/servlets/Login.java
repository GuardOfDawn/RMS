package edu.nju.courseHomeworkCheck.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ServletContext Context= getServletContext();
//		if (null == request.getParameter("Logout")&&null == request.getParameter("return")) {
//			totalCounter++;
//			Context.setAttribute("totalCounter", Integer.toString(totalCounter));
//		}
//		if(null!=request.getParameter("Logout")){
//			onlineCounter--;
//			Context.setAttribute("onlineCounter", Integer.toString(onlineCounter));
//		}
		
		String login="";
		HttpSession session = request.getSession(false);
		Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        		
        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("LoginCookie")) {
                    login=cookie.getValue();
                    break;
                }
            }
        }
    
        // Logout action removes session, but the cookie remains
        if (null != request.getParameter("Logout")) {
            if (null != session) {
            	session.invalidate();
                session = null;
            }
        }
       
//        response.setContentType("text/html;charset=utf-8");
//		request.setCharacterEncoding("utf-8");
		int totalCounter= Integer.parseInt((String) Context.getAttribute("totalCounter"));
		int onlineCounter = Integer.parseInt((String) Context.getAttribute("onlineCounter"));
        @SuppressWarnings("unchecked")
		ArrayList<String> ipList = (ArrayList<String>)Context.getAttribute("allvisitIP");
        String currentIP = request.getRemoteAddr();
        boolean ipExist = false;
        for(String ip : ipList){
        	if(currentIP.equals(ip)){
        		ipExist = true;
        		break;
        	}
        }
        if(ipExist==false){
        	totalCounter++;
        	Context.setAttribute("totalCounter", Integer.toString(totalCounter));
        	ipList.add(currentIP);
            Context.setAttribute("allvisitIP", ipList);
        }
        int visitorCounter = totalCounter - onlineCounter;
        PrintWriter out = response.getWriter();
        out.println("<html>"
        		+ "<body>");
        out.println("<p>总在线人数："+totalCounter+" 登录人数："+onlineCounter+" 游客人数:"+visitorCounter+"</p>");
        out.println("<h3>学生登录</h3>");
        out.println(
            "<form method='POST' action='"
                + response.encodeURL(request.getContextPath()+"/StudentOperation")
                + "'>");
        out.println(
            "用户名: <input type='text' name='userid' value='" + login + "'>");
        out.println("<br>");
        out.println("<br>");
        out.println(
            "密 码 : <input type='password' name='password' value=''>");
        out.println("<br>");
        out.println("<br>");
        out.println("<input type='submit' name='Login' value='登录'>");
        //out.println("<input type='submit' name='ForgetPW' value='忘记密码'>");
       
        out.println("</form></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
