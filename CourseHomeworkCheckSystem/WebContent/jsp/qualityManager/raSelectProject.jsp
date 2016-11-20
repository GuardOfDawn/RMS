<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="rms.servlet.business.ProjectListBean"%>
    <%@page import="newproject.model.Project"%>
    <%@page import="java.util.List"%>
    <%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();
	   int number = 0;%>
	<title>RA select project for quality manager</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/lightbox.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/progressbar.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
	<div id="main">
		<jsp:useBean id="projectList"
			class="rms.servlet.business.ProjectListBean"
			scope="page"></jsp:useBean>
		<jsp:useBean id="project"
			class="newproject.model.Project"
			scope="page"></jsp:useBean>
		
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
			<div class="stepInfo">
				<div class="stepIco stepIco1" id="create">1
					<div class="stepText" id="createText">选择项目</div>
				</div>
				<div class="stepIco stepIco2" id="check">2
					<div class="stepText" id="checkText">添加风险</div>
				</div>
				<div class="stepIco stepIco3" id="produce">3
					<div class="stepText" id="produceText">分配风险</div>
				</div>
			</div>
			
			<form method="post">
	          <div class="form_settings" style="margin-top:100px;margin-left:100px">
	          	<p>
	          	<span>选择项目</span>
	            <select id="selectProject" name="selectProject" size="5" onchange="projectSelected(this)" style="margin-top:5px;" >
	              <%projectList = (ProjectListBean)request.getAttribute("projectList");
	                for(int i=0;i<projectList.getSize();i++){
						pageContext.setAttribute("project",projectList.getProject(i));
	          	  %>
          	    	<option value='<jsp:getProperty name="project" property="projectId" />;<jsp:getProperty name="project" property="projectName" />;<jsp:getProperty name="project" property="description" />'>
          	    		<jsp:getProperty name="project" property="projectId" />
          	    	</option>
	          	  <%} %>
              	</select>
              	</p>
              	<p><span>项目编号</span><input type="text" id="projectId" name="projectId" disabled="disabled" value='' /></p>
              	<p><span>项目标题</span><input type="text" id="projectName" name="projectName" disabled="disabled"  value='' /></p>
	            <p><span>项目内容</span><textarea id="description" rows="6" cols="50" name="description" disabled="disabled" ></textarea></p>
				<p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="button" name="ensure" value="下一步" onclick="nextStep()"/></p>
			  </div>
			</form>
		    
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
function projectSelected(obj){
	var parts = obj.value.split(";");
	document.getElementById('projectId').value= parts[0];
	document.getElementById('projectName').value= parts[1];
	document.getElementById('description').value= parts[2];
}
function nextStep(){
	var projectId = document.getElementById('projectId').value;
	if(projectId==""){
		alert("请选择一个项目再进行下一步");
	}
	else{
		window.location.href='<%=path%>/RAAddRiskServlet?project='+projectId;
	}
}
</script>
<script type="text/javascript">
$(function() {
	setTimeout("changeDivStyle();", 100); // 0.1秒后展示结果，因为HTML加载顺序，先加载脚本+样式，再加载body内容。所以加个延时
});
function changeDivStyle(){
//		var o_status = $("#o_status").val();	//获取隐藏框值
	var o_status = "<%=number %>";
	if(o_status==0){
		$('#create').css('background', '#DD0000');
		$('#createText').css('color', '#DD0000');
	}else if(o_status==1){
		$('#check').css('background', '#DD0000');
		$('#checkText').css('color', '#DD0000');
	}else if(o_status==2){
		$('#produce').css('background', '#DD0000');
		$('#produceText').css('color', '#DD0000');
	}
}
</script>
</html>