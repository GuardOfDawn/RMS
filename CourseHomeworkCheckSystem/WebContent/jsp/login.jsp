<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>Login Page</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
</head>
<body>
	<div id="main" align="center">
	    <div id="site_middle">
			<h2>欢迎来到风险管理系统</h2>
			<form method='POST' action='../LoginServlet'>
				<div class="form_settings">
					<p>用户名&nbsp;&nbsp;<input type="text" name="userid" value="" /></p>
            		<p>用户密码&nbsp;&nbsp;<input type="password" name="password" value="" /></p>
					<p style="padding-top: 15px"><span>&nbsp;</span>
						<input class="submit" type="submit" name="login" value="登录"/>
					</p>
					<p>
						<%if("loginError".equals(request.getAttribute("loginError"))){ %>
						用户名密码错误，登录失败！
						<%} %>
					</p>
				</div>
			</form>
		</div>
	</div>
</body>
</html>