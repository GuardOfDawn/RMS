<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();%>
	<title>risk overview for quality manager</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/lightbox.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
	<jsp:useBean id="riskList"
			type="rms.servlet.business.RiskListBean"
			scope="session"></jsp:useBean>
	<jsp:useBean id="riskItem"
			class="newproject.model.RiskItem"
			scope="page"></jsp:useBean>
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
			    <li><a href="<%=path%>/RiskViewServlet">查看所有风险</a></li>
				<br></br>
				<li><a href="<%=path%>/jsp/qualityManager/addRisk.jsp">添加风险</a></li>
	 	      </ul>
			</div>
   	  	  </div>
		  <div id="content">
		    <table id="riskListTable" style="width:100%; border-spacing:0;">
		    <tr><th>序号</th><th>标题</th><th>内容</th><th>删除</th><th>修改</th></tr>
		    <%
		    int number = 1;
		    for(int i=0;i<riskList.getSize();i++){
		    	pageContext.setAttribute("riskItem",riskList.getRisk(i));
		    	request.setAttribute("number", number);
		    	number++;
			%>
		    <tr>
		  	  <td><%=request.getAttribute("number") %></td>
		  	  <td><jsp:getProperty name="riskItem" property="title" /></td>
		      <td><jsp:getProperty name="riskItem" property="description" /></td>
		      <td>
		      	<input class="submit" type="button" name="deleteRisk" value="删除"
					 onclick="deleteRiskRow('<jsp:getProperty name="riskItem" property="riskId" />')"/>
		      </td>
		      <td>
		      	<input class="submit" type="button" name="modifyRisk" value="修改"
					 onclick="modifyRiskRow('<jsp:getProperty name="riskItem" property="riskId" />')"/>
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
	function followRiskRow(riskId){
		window.location.href='<%=path%>/FollowRiskServlet?riskIdFollow='+riskId
	}
	function deleteRiskRow(riskId){
		document.getElementById("lightDeleteRisk").style.display='block';
		document.getElementById("deleteRiskId").value=riskId;
	}
	function cancelDeleteRisk(){
		document.getElementById("deleteRiskId").value='';
		document.getElementById("lightDeleteRisk").style.display='none';
    }
    function ensureDeleteRisk(){
    	var riskToDelete = document.getElementById("deleteRiskId").value;
		document.getElementById("deleteRiskId").value='';
		document.getElementById("lightDeleteRisk").style.display='none';
		window.location.href='<%=path%>/RiskDeleteServlet?riskToDelete='+riskToDelete;
	
    }
	function modifyRiskRow(riskId){
		window.location.href='<%=path%>/jsp/qualityManager/modifyRisk.jsp?riskIdModify='+riskId;
	}
	
</script>
</html>