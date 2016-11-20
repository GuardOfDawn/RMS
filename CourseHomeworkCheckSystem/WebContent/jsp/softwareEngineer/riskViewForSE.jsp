<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>risk overview for software engineer</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/lightbox.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
<jsp:useBean id="fullRiskList"
			type="rms.servlet.business.FullRiskListBean"
			scope="session"></jsp:useBean>
	<jsp:useBean id="riskItem"
			class="rms.servlet.business.FullRisk"
			scope="page"></jsp:useBean>
	<div id="main">
	  	<header>
	      <nav>
	        <ul class="lavaLampWithImage" id="lava_menu">
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
			    <li><a href="<%=path%>/RiskViewServlet">我需要追踪的风险</a></li>
	 	      </ul>
			</div>
   	  	  </div>
		  <div id="content">
		    <table id="riskListTable" style="width:100%; border-spacing:0;">
		    <tr><th>序号</th><th>项目</th><th>可能性</th><th>影响程度</th><th>状态</th><th>详细</th><th>修改</th><th>状态追踪</th></tr>
		    <%
		    int number = 1;
		    for(int i=0;i<fullRiskList.getSize();i++){
		    	pageContext.setAttribute("riskItem",fullRiskList.getFullRisk(i));
		    	request.setAttribute("number", number);
		    	number++;
			%>
		    <tr>
		  	  <td><%=request.getAttribute("number") %></td>
		  	  <td><jsp:getProperty name="riskItem" property="projectId" /></td>
		  	  <td><jsp:getProperty name="riskItem" property="possibility" /></td>
		      <td><jsp:getProperty name="riskItem" property="effectlevel" /></td>
		  	  <td><jsp:getProperty name="riskItem" property="state" /></td>
		      <td>
		      	<input class="submit" type="button" name="checkRisk" value="查看"
					 onclick="checkRiskRow('<jsp:getProperty name="riskItem" property="riskId" />')"/>
		      </td>
		      <td>
		      	<input class="submit" type="button" name="deleteRisk" value="修改"
					 onclick="modifyRiskRow('<jsp:getProperty name="riskItem" property="riskId" />')"/>
		      </td>
		      <td>
		      	<input class="submit" type="button" name="modifyRisk" value="添加状态"
					 onclick="addRiskStateRow('<jsp:getProperty name="riskItem" property="riskId" />')"/>
		      </td>
		    </tr>
		  	<%} %>
		    </table>
		  </div>
		</div>
	</div>
	
	<div id="lightDeleteRisk" class="delete_content" >
      	  <p></p>
      	  <div align="center">
      	    <p><input id="deleteRiskId" type="text" value="" width="30px" readonly="readonly"/></p>
      	    <h2>您确认删除这条风险吗？</h2>
      	  </div>
      	  <div class="form_settings" style="margin-left:-30px;margin-top:20px">
            <input class="submit" type="button" value="取消" onclick="cancelDeleteRisk()"/>
            <input class="submit" type="button" value="确认删除" onclick="ensureDeleteRisk()"/>
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
	function checkRiskRow(riskId){
		window.location.href='<%=path%>/jsp/qualityManager/riskDetail.jsp?riskIdCheck='+riskId;
	}
	function modifyRiskRow(riskId){
		window.location.href='<%=path%>/jsp/qualityManager/modifyRisk.jsp?riskIdModify='+riskId;
	}
	function addRiskStateRow(riskId){
		
	}
</script>
</html>