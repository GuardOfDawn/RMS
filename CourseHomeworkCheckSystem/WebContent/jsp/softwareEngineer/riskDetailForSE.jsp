<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="rms.common.Possibility"%>
    <%@page import="rms.common.EffectLevel"%>
    <%@page import="rms.common.RiskState"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% String path = request.getContextPath();%>
<title>risk overview for software engineer</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
	<jsp:useBean id="fullRiskList"
			type="rms.servlet.business.FullRiskListBean"
			scope="session"></jsp:useBean>
	<jsp:useBean id="riskItem"
			class="rms.servlet.business.FullRisk"
			scope="page"></jsp:useBean>
	<jsp:useBean id="riskStateItem"
			class="rms.servlet.business.RiskStateItem"
			scope="page"></jsp:useBean>
	<div id="main">
	  	<header>
	      <nav>
	        <ul class="lavaLampWithImage" id="lava_menu">
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
			    <li><a href="<%=path%>/RiskViewServlet">我需要追踪的风险</a></li>
	 	      </ul>
			</div>
   	  	  </div>
   	  	<%
   	  		String riskId = request.getParameter("riskIdCheck");
   	 		for(int i=0;i<fullRiskList.getSize();i++){
   	 			if(fullRiskList.getFullRisk(i).getRiskId().equals(riskId)){
	    			pageContext.setAttribute("riskItem",fullRiskList.getFullRisk(i));
	    			break;
   	 			}
   	 		}
   	  	%>
   	  	<div id="content">
		  <h2 align="center">查看风险详细</h2>
   	  	  <form action="" method="post">
	   	  	<div class="form_settings" style="margin-left:150px">
	  		  <p><span>风险编号</span><input id="riskId" type="text" name="riskId" readonly="readonly" value="<jsp:getProperty name="riskItem" property="riskId" />" /></p>
	  		  <p><span>对应项目编号</span><input type="text" name="projectId" readonly="readonly" value="<jsp:getProperty name="riskItem" property="projectId" />" /></p>
	  		    <%
              	if(riskItem.getPossibility().equals("High")){ %>
              <p><span>可能性</span><input type="text" name="riskId" readonly="readonly" value="高" /></p>
            	<%}
              	else if(riskItem.getPossibility().equals("Medium")){%>
              <p><span>可能性</span><input type="text" name="riskId" readonly="readonly" value="中" /></p>
	  		    <%}
              	else{%>
              <p><span>可能性</span><input type="text" name="riskId" readonly="readonly" value="低" /></p>
	  		    <%} %>
	  		     <%
              	if(riskItem.getEffectlevel().equals("High")){ %>
              <p><span>影响程度</span><input type="text" name="riskId" readonly="readonly" value="高" /></p>
            	<%}
              	else if(riskItem.getEffectlevel().equals("Medium")){%>
              <p><span>影响程度</span><input type="text" name="riskId" readonly="readonly" value="中" /></p>
	  		    <%}
              	else{%>
              <p><span>影响程度</span><input type="text" name="riskId" readonly="readonly" value="低" /></p>
	  		    <%} %>
	  		  <p><span>触发器</span><input type="text" name="trigger" readonly="readonly" value="<jsp:getProperty name="riskItem" property="threshold" />" /></p>
	          <%
              	if(riskItem.getState()==RiskState.UnRemoved){ %>
              <p><span>风险状态</span><input type="text" name="riskId" readonly="readonly" value="未排除" /></p>
            	<%}
              	else if(riskItem.getState()==RiskState.Removed){%>
              <p><span>风险状态</span><input type="text" name="riskId" readonly="readonly" value="已排除" /></p>
	  		    <%}
              	else if(riskItem.getState()==RiskState.Problem){%>
              <p><span>风险状态</span><input type="text" name="riskId" readonly="readonly" value="演变成问题" /></p>
	  		    <%}
              	else {%>
              <p><span>风险状态</span><input type="text" name="riskId" readonly="readonly" value="演变成问题并得到解决" /></p>
	  		    <%} %>
	          <p><span>风险内容:</span><textarea rows="6" cols="50" readonly name="name" ><jsp:getProperty name="riskItem" property="description" /></textarea></p>
			 </div>
			 
			 <div class="form_settings" style="margin-left:20px">
			 	<p><span>风险跟踪记录</span></p>
			 	<input class="submit" type="button" value="添加状态记录" onclick="addStateItem()"/>
				<br>
			 	<%	
				int number = 1;
				for (int i = 0; i < riskItem.getRiskStateList().size(); i++) {
					pageContext.setAttribute("riskStateItem", riskItem.getRiskStateList().get(i));
					request.setAttribute("number", number);
					number++;
				%>
				<table width='650' border='2px' cellspacing='0px' style='border-collapse:collapse'>
					<tbody>
						<tr>
							<td width='150'>风险状态编号:</td>
							<td width='500'><%=request.getAttribute("number") %></td>
						</tr>
						<tr>
							<td>记录创建时间:</td>
							<td><jsp:getProperty name="riskStateItem" property="time" /></td>
						</tr>
						<tr>
							<td>记录创建者:</td>
							<td><jsp:getProperty name="riskStateItem" property="comitter" /></td>
						</tr>
						<tr>
							<td>风险状态内容:</td>
							<td><jsp:getProperty name="riskStateItem" property="description" /></td>
						</tr>
					</tbody>
				</table>
				<br>
				<%}%>
				<%String addRes = String.valueOf(request.getAttribute("res"));
				  if(addRes!=null&&addRes.equals("true")){ %>
				  <p><span>风险状态添加成功</span></p>
				<%}
				  else if(addRes!=null&&addRes.equals("false")){%>
				  <p><span>风险状态添加失败</span></p>
				<%} %>
			 </div>
			 
		   </form>
		   
		   <div id="lightForAdd" class="add_content" style="display:none">
	      	  <p></p>
	      	  <h2 align="center">添加风险状态条目</h2>
	      	  <p></p>
	          <div class="form_settings" style="margin-left:60px" >
	            <p><span>风险状态</span>
				  <select id="riskStateAdd" name="riskState">
          	    	<option value="UnRemoved" selected="selected">未排除</option>
          	    	<option value="Removed">已排除</option>
          	    	<option value="Problem">演变成问题</option>
          	    	<option value="ProblemSolved">问题得到解决</option>
          	      </select>
          	    </p>
				<p></p>
	            <p><span>风险状态描述</span><textarea id="descriptionAdd" rows="6" cols="50" name="description"></textarea></p>
	          </div>
	          <div class="form_settings">
	            <input class="submit" type="button" value="取消" onclick="closeAddItem()"/>
	            <input class="submit" type="button" name="addItemEnsure" value="确认添加" onclick="addStateItem()"/>
	          </div>
			</div>
			
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
function addStateItem(){
	document.getElementById('lightForAdd').style.display='block';
}
function closeAddState(){
	var options = document.getElementById('riskStateAdd').options;
	options[0].selected = true;
	document.getElementById('descriptionAdd').value="";
	document.getElementById('lightForAdd').style.display='none';
}
function addStateItem(){
	var addedStateString = "";
	addedStateString = addedStateString+document.getElementById('riskId').value;
	addedStateString = addedStateString+"\",\"";
	addedStateString = addedStateString+document.getElementById('riskStateAdd').value;
	addedStateString = addedStateString+"\",\"";
	addedStateString = addedStateString+document.getElementById('descriptionAdd').value;
	window.location.href='<%=path%>/RiskStateAddServlet?addedStateString='+addedStateString;
}
</script>
</html>