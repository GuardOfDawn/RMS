<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>workNotice Page</title>
</head>
<body bgcolor='#FFFACD'>
	<table width='650' border='0' >
		<tr>
			<td width='650' height='80' background='<%=request.getContextPath() + "/image/top.jpg"%>'>&nbsp;</td>
		</tr>
	</table>
	<table bgcolor='#B0E0E6' style='font-weight:bold'>
		<tbody>
			<tr>
				<td><a href='<%=request.getContextPath() + "/workNoticeServlet"%>' style='text-decoration: none;'>首页</a></td>
				<td style='BACKGROUND-POSITION: 50% 50%;background-image: url(image/verticalLine.png);BACKGROUND-REPEAT: no-repeat;' width='27'>&nbsp;&nbsp;</td>
				<td><a href='<%=request.getContextPath() + "/jsp/courses.jsp"%>' style='text-decoration: none;'>我的课程</a></td>
				<td style='BACKGROUND-POSITION: 50% 50%;background-image: url(image/verticalLine.png);BACKGROUND-REPEAT: no-repeat;' width='27'>&nbsp;&nbsp;</td>
				<td><a href='<%=request.getContextPath() + "/jsp/homework.jsp"%>' style='text-decoration: none;'>我的作业</a></td>
				<td style='BACKGROUND-POSITION: 50% 50%;background-image: url(image/verticalLine.png);BACKGROUND-REPEAT: no-repeat;' width='27'>&nbsp;&nbsp;</td>
				<td><a href='<%=request.getContextPath() + "/jsp/homework.jsp"%>' style='text-decoration: none;'>作业成绩</a></td>
			</tr>
		</tbody>
	</table>
	<h3>Welcome <%=request.getAttribute("studentid")%>!</h3>
	<h4>
		<jsp:useBean id="listHomeworkUpload"
			type="edu.nju.courseHomeworkCheck.action.business.HomeworkListBean"
			scope="session"></jsp:useBean>
		<jsp:useBean id="item" class="edu.nju.courseHomeworkCheck.models.Homework"
			scope="page"></jsp:useBean>
		<jsp:useBean id="course" class="edu.nju.courseHomeworkCheck.models.Course"
			scope="page"></jsp:useBean>
		未提交作业：
	</h4>
	<h4>
		<%
		if(listHomeworkUpload.getHomeworkList()==null||listHomeworkUpload.getHomeworkList().size()==0){
			%>
		<p>恭喜，你没有未提交的作业！</p>
		<%	
		}
		else{
			int number = 1;
			for (int i = 0; i < listHomeworkUpload.getHomeworkList().size(); i++) {
				pageContext.setAttribute("item", listHomeworkUpload.getHomeworkList(i));
				pageContext.setAttribute("course", listHomeworkUpload.getHomeworkList(i).getCourse());
				request.setAttribute("number", number);
				number++;
		%>
		<table width='650' border='2px' bordercolor='#00FF7F' cellspacing='0px' style='border-collapse:collapse'>
			<tbody>
				<tr>
					<td width='150'>Assignment No:</td>
					<td width='500'><%=request.getAttribute("number") %></td>
				</tr>
				<tr>
					<td>Course Name:</td>
					<td><jsp:getProperty name="course" property="courseName" /></td>
				</tr>
				<tr>
					<td>Due date:</td>
					<td><jsp:getProperty name="item" property="dueTime" /></td>
				</tr>
				<tr>
					<td>Title:</td>
					<td><jsp:getProperty name="item" property="homeworkTitle" /></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><jsp:getProperty name="item" property="homeworkContent" /></td>
				</tr>
			</tbody>
		</table>
		<br>
		<%}
		}%>
	</h4>
	<h4>
		<jsp:useBean id="listHomeworkGrade"
			type="edu.nju.courseHomeworkCheck.action.business.HomeworkListBean"
			scope="session"></jsp:useBean>
		<jsp:useBean id="item2" class="edu.nju.courseHomeworkCheck.models.Homework"
			scope="page"></jsp:useBean>
		<jsp:useBean id="course2" class="edu.nju.courseHomeworkCheck.models.Course"
			scope="page"></jsp:useBean>
		不及格作业：
	</h4>
	<h4>
		<%
		if(listHomeworkGrade.getHomeworkList()==null||listHomeworkGrade.getHomeworkList().size()==0){
			%>
		<p>恭喜，你没有不及格的作业！</p>
		<%	
		}
		else{
			int number2 = 1;
			for (int i = 0; i < listHomeworkGrade.getHomeworkList().size(); i++) {
				pageContext.setAttribute("item2", listHomeworkGrade.getHomeworkList(i));
				pageContext.setAttribute("course2", listHomeworkGrade.getHomeworkList(i).getCourse());
				request.setAttribute("number2", number2);
				number2++;
		%>
		<table width='650' border='2px' bordercolor='#00FF7F' cellspacing='0px' style='border-collapse:collapse'>
			<tbody>
				<tr>
					<td width='150'>Assignment No:</td>
					<td width='500'><%=request.getAttribute("number2") %></td>
				</tr>
				<tr>
					<td>Course Name:</td>
					<td><jsp:getProperty name="course2" property="courseName" /></td>
				</tr>
				<tr>
					<td>Title:</td>
					<td><jsp:getProperty name="item2" property="homeworkTitle" /></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><jsp:getProperty name="item2" property="homeworkContent" /></td>
				</tr>
				<tr>
					<td>Grade:</td>
					<td><jsp:getProperty name="item2" property="grade" /></td>
				</tr>
			</tbody>
		</table>
		<br>
		<%}
		}%>
	</h4>
	<form method='POST' action='<%=request.getContextPath() + "/LogoutServlet"%>'>
		<input type='submit' name='Logout' value='登出'/>
	</form>
</body>
</html>