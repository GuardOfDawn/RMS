<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="rms.servlet.business.RAListBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>ra overview for quality manager</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/lightbox.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
<jsp:useBean id="raList"
			class="rms.servlet.business.RAListBean"
			scope="page"></jsp:useBean>
	<jsp:useBean id="raItem"
			class="newproject.model.RA"
			scope="page"></jsp:useBean>
	<div id="main">
	  	<header>
	      <nav>
	        <ul class="lavaLampWithImage" id="lava_menu">
          	  <li><a href="<%=path%>/RiskViewServlet">风险管理</a></li>
          	  <li class="current"><a href="<%=path%>/RAViewServlet">风险管理计划</a></li>
          	  <li><a href="<%=path%>/jsp/qualityManager/riskStat.jsp">风险状况统计</a></li>
          	  <li><a>您好，<%=session.getAttribute("userTypeInChinese")%>,<%=session.getAttribute("userId")%></a></li>
	          <li><a href="<%=path%>/LogoutServlet">登出</a></li>
	        </ul>
	      </nav>
	    </header>
		<div id="site_content">
		  <div id="sidebar_container">
      		<div class="sidebar">
	  	      <ul>
			    <br></br>
			    <li><a href="<%=path%>/RAViewServlet">已有风险管理计划</a></li>
				<br></br>
				<li><a href="<%=path%>/RASelectProjectServlet">添加风险管理计划</a></li>
	 	      </ul>
			</div>
   	  	  </div>
		  <div id="content">
		    <table id="riskListTable" style="width:100%; border-spacing:0;">
		    <tr><th>序号</th><th>计划编号</th><th>项目编号</th><th>计划描述</th></tr>
		    <%
		    int number = 1;
		    raList = (RAListBean) request.getAttribute("raList");
		    for(int i=0;i<raList.getSize();i++){
		    	pageContext.setAttribute("raItem",raList.getRA(i));
		    	request.setAttribute("number", number);
		    	number++;
			%>
		    <tr>
		  	  <td><%=request.getAttribute("number") %></td>
		  	  <td><jsp:getProperty name="raItem" property="raId" /></td>
		      <td><jsp:getProperty name="raItem" property="projectId" /></td>
		      <td><jsp:getProperty name="raItem" property="description" /></td>
		    </tr>
		  	<%} %>
		    </table>
		  </div>
		</div>
	</div>
	
</body>
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.lavalamp.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/image_fade.js"></script>
<script type="text/javascript">
  $(function() {
    $("#lava_menu").lavaLamp({
      fx: "backout",
      speed: 200
    });
  });
</script>
<script type="text/javascript">

</script>
</html>