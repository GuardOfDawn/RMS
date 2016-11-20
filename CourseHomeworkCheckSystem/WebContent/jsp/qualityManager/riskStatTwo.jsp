<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="org.jfree.data.general.DefaultPieDataset,org.jfree.chart.ChartFactory
		,org.jfree.chart.JFreeChart,org.jfree.chart.servlet.*" %>
    <%@page import="rms.servlet.business.RiskConditionBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>risk statistic for quality manager</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/lightbox.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
    <script type="text/javascript" src="<%=path %>/calc/calendar.js"></script>
    <script type="text/javascript" src="<%=path %>/calc/calendar-en.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path %>/calc/calendar-system.css"/>
</head>
<body>
	<div id="main">
	  	<header>
	      <nav>
	        <ul class="lavaLampWithImage" id="lava_menu">
          	  <li><a href="<%=path%>/RiskViewServlet">风险管理</a></li>
          	  <li><a href="<%=path%>/RAViewServlet">风险管理计划</a></li>
          	  <li class="current"><a href="<%=path%>/jsp/qualityManager/riskStat.jsp">风险状况统计</a></li>
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
			    <li><a href="<%=path%>/jsp/qualityManager/riskStat.jsp">查看识别最多的风险</a></li>
				<br></br>
				<li><a href="<%=path%>/jsp/qualityManager/riskStatTwo.jsp">查看演变成问题最多的风险</a></li>
	 	      </ul>
			</div>
   	  	  </div>
		  <div id="content">
		    <form>
	          <div class="form_settings" style="margin-top:100px;margin-left:100px">
	          	<p>
	          	  <span style="width:400px;font-size:20px;">查看特定时间内演变成问题最多的风险</span>
	          	</p>
	          	<br>
	          	<br>
	          	<p><span>起始日期</span>
	          	  <input type="text" id="startDate" size="12" value="选择日期" onclick="calShow('startDate');" onfocus="calShow('startDate');" style="width:100px;margin-left:-40px" onblur="checkStartDate()">
	          	</p>
	          	<p style="margin-top:-40px;margin-left:220px"><span>截止日期</span>
	          	  <input type="text" id="endDate" size="12" value="选择日期" onclick="calShow('endDate');" onfocus="calShow('endDate');" style="width:100px;margin-left:-40px" onblur="checkEndDate()">
	          	</p>
	          	<input class="submit" type="button" name="ensure" value="查找" style="margin-top:-60px;margin-left:450px" onclick="getRiskStat()"/>
	          </div>
	        </form>
	        
		    <%RiskConditionBean riskList = (RiskConditionBean)request.getAttribute("problemedRisks");
	          if(riskList!=null&&riskList.getRiskList()!=null){
	          %>
		    <div>
				<%
				DefaultPieDataset dpd = new DefaultPieDataset();
			    
				for(int i=0;i<riskList.getSize();i++){
					dpd.setValue(riskList.getRiskCondition(i).getRisk().getRiskId(), riskList.getRiskCondition(i).getRecognizedCount());
				}
			    
			    JFreeChart chart = ChartFactory.createPieChart("风险演变成问题的比例图",dpd, true, false, false);
			    
			    String fileName = ServletUtilities.saveChartAsPNG(chart,800,600,session); 
			    
			    String url = request.getContextPath() + "/DisplayChart?filename=" + fileName;
				%>
				<img src="<%= url %>" width="800" height="600">
			</div>
			<%} %> 
		    
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
    var startDate = "<%=request.getAttribute("startDate") %>";
	var endDate = "<%=request.getAttribute("endDate") %>";
	if(startDate!="null"&&endDate!="null"){
		document.getElementById("startDate").value = startDate;
		document.getElementById("endDate").value = endDate;
	}
    $("#lava_menu").lavaLamp({
      fx: "backout",
      speed: 200
    });
  });
</script>
<script type="text/javascript">
function checkStartDate(){
  	var startDateString = document.getElementById("startDate").value;
  	var parts = startDateString.split("-");
  	var startDate = new Date(parts[0],parts[1]-1,parts[2]);
  	var day = new Date();
  	if(startDate>day){ 
  		alert("开始时间必须小于当前日期");var day = new Date();
	  	var year = day.getFullYear();
		var month = day.getMonth()+1;
		var day = day.getDate();
		if(month<10){
			if(day<10){
				document.getElementById("startDate").value=year+"-0"+month+"-0"+day;
			}
			else{
				document.getElementById("startDate").value=year+"-0"+month+"-"+day;
			}
		}else{
			if(day<10){
				document.getElementById("startDate").value=year+"-"+month+"-0"+day;
			}
			else{
				document.getElementById("startDate").value=year+"-"+month+"-"+day;
			}
		}
  	}
}
function checkEndDate(){
	var startDateString = document.getElementById("startDate").value;
	if(startDateString=="选择日期"){
		alert("请先选择起始日期");
  		document.getElementById("endDate").value="选择日期";
	}
	else{
		var parts = startDateString.split("-");
	  	var startDate = new Date(parts[0],parts[1]-1,parts[2]);
	  	var endDateString = document.getElementById("endDate").value;
	  	var parts = endDateString.split("-");
	  	var endDate = new Date(parts[0],parts[1]-1,parts[2]);
	  	if(startDate>endDate||startDate==endDate){
	  		alert("开始日期必须小于结束日期");
	  		document.getElementById("endDate").value="选择日期";
	  	}
  		var day = new Date();
  	  	if(endDate>day){ 
  	  		alert("结束时间必须小于当前日期");
  		  	var year = day.getFullYear();
  			var month = day.getMonth()+1;
  			var day = day.getDate();
  			if(month<10){
  				if(day<10){
  					document.getElementById("endDate").value=year+"-0"+month+"-0"+day;
  				}
  				else{
  					document.getElementById("endDate").value=year+"-0"+month+"-"+day;
  				}
  			}else{
  				if(day<10){
  					document.getElementById("endDate").value=year+"-"+month+"-0"+day;
  				}
  				else{
  					document.getElementById("endDate").value=year+"-"+month+"-"+day;
  				}
  			}
  	  	}
	}
}
function getRiskStat(){
	var startDateString = document.getElementById("startDate").value;
	var endDateString = document.getElementById("endDate").value;
	if(startDateString=="选择日期"||endDateString=="选择日期"){
		alert("请先选择风险条目推荐的起始和结束日期");
	}
	else{
		window.location.href='<%=path%>/RiskProblemedStatServlet?startDate='+startDateString+'&endDate='+endDateString;
	}
}
</script>
</html>