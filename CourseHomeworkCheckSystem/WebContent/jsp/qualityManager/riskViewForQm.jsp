<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>risk overview for quality manager</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
	<jsp:useBean id="riskList"
			type="rms.servlet.business.RiskListBean"
			scope="session"></jsp:useBean>
	<jsp:useBean id="riskItem"
			class="rms.model.RiskItem"
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
			    <li><a href="<%=path%>/RiskViewServlet">查看风险</a></li>
				<br></br>
				<li><a href="<%=path%>/jsp/qualityManager/addRisk.jsp">添加风险</a></li>
	 	      </ul>
			</div>
   	  	  </div>
		  <div id="content">
		    <table id="riskListTable" style="width:100%; border-spacing:0;">
		    <tr><th>序号</th><th>可能性</th><th>影响程度</th><th>触发器</th><th>提交者</th><th>状态</th><th>详细</th><th>跟踪</th><th>删除</th><th>修改</th></tr>
		    <%
		    int number = 1;
		    for(int i=0;i<riskList.getSize();i++){
		    	pageContext.setAttribute("riskItem",riskList.getRisk(i));
		    	request.setAttribute("number", number);
		    	request.setAttribute("followCondition", riskList.isFollowed(i));
		    	number++;
			%>
		    <tr>
		  	  <td><%=request.getAttribute("number") %></td>
		  	  <td><jsp:getProperty name="riskItem" property="possibility" /></td>
		      <td><jsp:getProperty name="riskItem" property="effect" /></td>
		      <td><jsp:getProperty name="riskItem" property="trigger" /></td>
		      <td><jsp:getProperty name="riskItem" property="commiterId" /></td>
		  	  <td><jsp:getProperty name="riskItem" property="state" /></td>
		      <td>
		      	<input class="submit" type="button" name="checkRisk" value="查看"
					 onclick="checkRiskRow('<jsp:getProperty name="riskItem" property="riskId" />')"/>
		      </td>
		      <%if(String.valueOf(request.getAttribute("followCondition")).equals("0")){ %>
		      <td>
		      	<input class="submit" type="button" name="followRisk" value="跟踪"
					 onclick="followRiskRow('<jsp:getProperty name="riskItem" property="riskId" />')">
		      </td>
		      <%}else{ %>
		      <td>已跟踪</td>
		      <%} %>
		      <td>
		      	<input class="submit" type="button" name="deleteRisk" value="删除"
					 onclick=""/>
		      </td>
		      <td>
		      	<input class="submit" type="button" name="modifyRisk" value="修改"
					 onclick=""/>
		      </td>
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
	function checkRiskRow(riskId){
		window.location.href='<%=path%>/jsp/qualityManager/riskDetail.jsp?riskIdCheck='+riskId;
	}
	function followRiskRow(riskId){
		window.location.href='<%=path%>/FollowRiskServlet?riskIdFollow='+riskId
	}
</script>
</html>