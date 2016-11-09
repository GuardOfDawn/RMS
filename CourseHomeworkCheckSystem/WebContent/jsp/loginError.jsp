<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login error Page</title>
</head>
<body bgcolor='#FFFACD'>
	<p>登录出错!</p>
	<form method='GET' action='<%=request.getContextPath() + "/jsp/login.jsp"%>'>
		<input type='submit' name='return' value='重新登录'/>
	</form>
</body>
</html>