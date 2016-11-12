<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="rms.model.RiskItem"%>
    <%@page import="rms.common.Possibility"%>
    <%@page import="rms.common.EffectLevel"%>
    <%@page import="rms.common.RiskState"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>risk modify view</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
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
		  <jsp:useBean id="riskList"
				type="rms.servlet.business.RiskListBean"
				scope="session"></jsp:useBean>
		  <jsp:useBean id="riskItem"
				class="rms.model.RiskItem"
				scope="page"></jsp:useBean>
			
			<%	String riskId = request.getParameter("riskIdModify");
	   	 		for(int i=0;i<riskList.getSize();i++){
	   	 			riskItem = riskList.getRisk(i);
	   	 			if(riskItem.getRiskId().equals(riskId)){
		    			pageContext.setAttribute("riskItem",riskItem);
		    			break;
	   	 			}
	   	 		}
	  		%>
		    <h2 align="center">修改风险</h2>
	        <form action="<%=path%>/RiskModifyServlet" method="post">
	          <div class="form_settings" style="margin-left:150px">
	            <p><span>风险编号</span><input type="text" name="riskId" readonly="readonly" value="<jsp:getProperty name="riskItem" property="riskId" />" /></p>
	            <p><span>可能性</span>
	              <select id="id" name="possibility">
	              	<%
	              	if(riskItem.getPossibility()==Possibility.High){ %>
	            	<option value="0" selected="selected">高</option>
	            	<option value="1">中</option>
	            	<option value="2">低</option>
	            	<%}
	              	else if(riskItem.getPossibility()==Possibility.Medium){%>
	            	<option value="0">高</option>
	            	<option value="1" selected="selected">中</option>
	            	<option value="2">低</option>
	            	<%}
	              	else{%>
	            	<option value="0">高</option>
	            	<option value="1">中</option>
	            	<option value="2" selected="selected">低</option>
	            	<%} %>
	              </select>
	            </p>
            	<p><span>影响程度</span>
	              <select id="id" name="effectLevel">
	            	<%
	              	if(riskItem.getEffect()==EffectLevel.High){ %>
	            	<option value="0" selected="selected">高</option>
	            	<option value="1">中</option>
	            	<option value="2">低</option>
	            	<%}
	              	else if(riskItem.getEffect()==EffectLevel.Medium){%>
	            	<option value="0">高</option>
	            	<option value="1" selected="selected">中</option>
	            	<option value="2">低</option>
	            	<%}
	              	else{%>
	            	<option value="0">高</option>
	            	<option value="1">中</option>
	            	<option value="2" selected="selected">低</option>
	            	<%} %>
	              </select>
	            </p>
	            <p><span>触发器</span><input type="text" name="trigger" value="<jsp:getProperty name="riskItem" property="trigger" />" /></p>
	            <p><span>风险状态</span>
	              <select id="id" name="state">
	            	<%
	              	if(riskItem.getState()==RiskState.UnRemoved){ %>
	            	<option value="0" selected="selected">未排除</option>
	            	<option value="1">已排除</option>
	            	<%}
	              	else{%>
	            	<option value="0">未排除</option>
	            	<option value="1" selected="selected">已排除</option>
	            	<%} %>
	              </select>
	            </p>
	            <p><span>风险内容</span><textarea rows="6" cols="50" name="description" ><jsp:getProperty name="riskItem" property="description" /></textarea></p>
	            <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="ensure" value="确认修改"/></p>
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
</html>