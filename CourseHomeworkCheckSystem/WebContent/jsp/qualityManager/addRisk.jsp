<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="newproject.model.RiskItem"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>risk add view</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
	<div id="main">
	  	<header>
	      <nav>
	        <ul class="lavaLampWithImage" id="lava_menu">
          	  <li class="current"><a href="<%=path%>/RiskViewServlet">风险管理</a></li>
          	  <li><a href="<%=path%>/Dessert/storevisitall">风险管理计划</a></li>
          	  <li><a href="<%=path%>/Dessert/storevisitall">风险状况统计</a></li>
          	  <li><a>您好，<%=session.getAttribute("userTypeInChinese")%>,<%=session.getAttribute("userid")%></a></li>
	          <li><a href="<%=path%>/LogoutServlet">登出</a></li>
	        </ul>
	      </nav>
	    </header>
		<div id="site_content">
		  <div id="sidebar_container">
      		<div class="sidebar">
	  	      <ul>
			    <br></br>
			    <li><a href="<%=path%>/RiskViewServlet">查看风险</a></li>
				<br></br>
				<li><a href="<%=path%>/jsp/qualityManager/addRisk.jsp">添加风险</a></li>
	 	      </ul>
			</div>
   	  	  </div>
		  <div id="content">
		  	<jsp:useBean id="riskItem"
				class="newproject.model.RiskItem"
				scope="page"></jsp:useBean>
			
			<%	RiskItem risk = (RiskItem) request.getAttribute("riskItem");
	  		if(risk==null){
	  		%>
		    <h2 align="center">添加风险</h2>
	        <form action="<%=path%>/RiskAddServlet" method="post">
	          <div class="form_settings" style="margin-left:150px">
	            <p><span>风险标题</span><input type="text" name="title" value="" /></p>
	            <p><span>风险内容</span><textarea rows="6" cols="50" name="description"></textarea></p>
	            <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="ensure" value="确认添加"/></p>
	            <%
	            if("false".equals(String.valueOf(request.getAttribute("addRes")))){
	            %>
	            <p>风险添加失败，请重新添加</p>
	            <%} %>
	          </div>
	        </form>
	        <%}
	  		else{
	  			pageContext.setAttribute("riskItem",risk);
	  		%>
	  		<h2 align="center">添加风险成功</h2>
	  		<form action="" method="post">
	   	  	 <div class="form_settings" style="margin-left:150px">
	  		  <p><span>风险编号</span><input type="text" name="riskId" readonly="readonly" value="<jsp:getProperty name="riskItem" property="riskId" />" /></p>
	  		  <p><span>风险标题</span><input type="text" name="title" readonly="readonly" value="<jsp:getProperty name="riskItem" property="title" />" /></p>
	          <p><span>风险内容</span><textarea rows="6" cols="50" readonly name="description" ><jsp:getProperty name="riskItem" property="description" /></textarea></p>
			 </div>
			</form><%} %>
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
</html>