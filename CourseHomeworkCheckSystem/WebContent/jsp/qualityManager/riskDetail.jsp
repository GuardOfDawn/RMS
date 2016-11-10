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
   	  	<%
   	  		String riskId = request.getParameter("riskIdCheck");
   	 		for(int i=0;i<riskList.getSize();i++){
   	 			if(riskList.getRisk(i).getRiskId().equals(riskId)){
	    			pageContext.setAttribute("riskItem",riskList.getRisk(i));
	    			break;
   	 			}
   	 		}
   	  	%>
   	  	<div id="content">
	  	  <h2 align="center">查看风险详细</h2>
  		  <p><span>风险编号:<jsp:getProperty name="riskItem" property="riskId" /></span></p>
  		  <p><span>可能性:<jsp:getProperty name="riskItem" property="possibility" /></span></p>
  		  <p><span>影响程度:<jsp:getProperty name="riskItem" property="effect" /></span></p>
  		  <p><span>触发器:<jsp:getProperty name="riskItem" property="trigger" /></span></p>
  		  <p><span>提交者:<jsp:getProperty name="riskItem" property="commiterId" /></span></p>
  		  <p><span>跟踪者:<jsp:getProperty name="riskItem" property="followerId" /></span></p>
  		  <p><span>风险内容:</span><textarea rows="6" cols="50" readonly name="name" ><jsp:getProperty name="riskItem" property="description" /></textarea></p>
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