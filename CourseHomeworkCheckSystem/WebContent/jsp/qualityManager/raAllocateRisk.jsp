<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="rms.servlet.business.StateItemBean"%>
    <%@page import="rms.servlet.business.UserListBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();
	   int number = 2;%>
	<title>RA allocate risk for quality manager</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/lightbox.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/progressbar.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
</head>
<body>
	<div id="main">
		<jsp:useBean id="developerList"
			class="rms.servlet.business.UserListBean"
			scope="page"></jsp:useBean>
		<jsp:useBean id="developer"
			class="rms.servlet.business.SimpleUser"
			scope="page"></jsp:useBean>
		<jsp:useBean id="riskList"
			class="rms.servlet.business.StateItemBean"
			scope="page"></jsp:useBean>
		<jsp:useBean id="riskItem"
			class="newproject.model.StateItem"
			scope="page"></jsp:useBean>
			
	  	<header>
	      <nav>
	        <ul class="lavaLampWithImage" id="lava_menu">
          	  <li><a href="<%=path%>/RiskViewServlet">风险管理</a></li>
          	  <li class="current"><a href="<%=path%>/Dessert/storevisitall">风险管理计划</a></li>
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
			
			<div id="raRiskListDiv" style="margin-top:100px">
			  <p>
	          	  <span style="width:300px;font-size:20px;">1.分配风险到开发人员：</span>
	          </p>
	          <h2 align="center">风险列表</h2>
	          <table id="raRiskList" style="width:100%; border-spacing:0;">
		        <tr><th>风险编号</th><th>可能性</th><th>影响程度</th><th>风险内容</th><th>分配人员</th><th>跟踪者列表</th></tr>
		        <%riskList = (StateItemBean) request.getAttribute("riskList");
		          for(int i=0;i<riskList.getSize();i++){
		        	pageContext.setAttribute("riskItem",riskList.getStateItem(i));
		        	%>
		        <tr>
			  	  <td><jsp:getProperty name="riskItem" property="riskId" /></td>
			      <td><jsp:getProperty name="riskItem" property="possibility" /></td>
			      <td><jsp:getProperty name="riskItem" property="effectlevel" /></td>
			      <td><jsp:getProperty name="riskItem" property="description" /></td>
			      <td>
			      	<input class="submit" type="button" name="deleteRisk" value="分配"
						 onclick="allocateRisk('<jsp:getProperty name="riskItem" property="riskId" />')"/>
			      </td>
			      <td>暂无</td>
			    </tr>
		        <%} %>
		      </table>
	          <br>
	        </div>
	        
	        <div class="form_settings" >
		      <p>
	          	  <span style="width:300px;font-size:20px;">2.添加风险计划描述：</span>
	          </p>
	          <br>
	          <br>
	          <p><textarea id="raDescription" rows="6" cols="70" name="raDescription"></textarea></p>
		    </div>
	        
	        <div class="form_settings" style="float:right">
		        <input class="submit" type="button" value="分配完成" onclick="ensureAllocateRisk()"/>
		    </div>
		    
		    <div id="lightForAllocate" class="white_content">
	      	  <p></p>
	      	  <h2 align="center">分配风险给开发人员</h2>
	          <div class="form_settings" style="margin-left:60px" >
	            <p><span>风险编号</span><input id="riskId" type="text" name="riskId" value="" disabled="disabled"/></p>
	            <p><span>可能性</span><input id="possibility" type="text" name="possibility" value="" disabled="disabled"/></p>
	            <p><span>影响程度</span><input id="effectLevel" type="text" name="effectLevel" value="" disabled="disabled"/></p>
	            <p><span>风险内容</span><input id="description" type="text" name="description" value="" disabled="disabled"/></p>
	          </div>
	          
		      <div class="form_settings" style="margin-left:60px" >
		        <br>
	          	<p><span style="width:400px">从该风险所属项目的开发人员中选择：</span></p>
		        <p style="margin-left:-100px" >
	          	  <%developerList = (UserListBean) request.getAttribute("developerList");
	                for(int i=0;i<developerList.getSize();i++){
						pageContext.setAttribute("developer",developerList.getUser(i));	
	          	  %>
	         	    <label id="developerLabel<%=i %>">
	         	    	<input id="developer<%=i %>" type="checkbox" value="<jsp:getProperty name="developer" property="userID" />" />
	         	    	<jsp:getProperty name="developer" property="userID" />:<jsp:getProperty name="developer" property="userName" />
	         	    </label><br>
	          	  <%} %>
	          	</p>
	          </div>
	          
	          <div class="form_settings">
	            <input class="submit" type="button" value="取消" onclick="closeAllocateItem()"/>
	            <input class="submit" type="button" name="addItemEnsure" value="确认" onclick="allocateRiskItem()"/>
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
function allocateRisk(riskId){
	var table = document.getElementById('raRiskList');
	var rows = table.rows;
	for(var i=1;i<rows.length;i++){
		if(rows[i].cells[0].innerHTML==riskId){
			document.getElementById('riskId').value=riskId;
			document.getElementById('possibility').value= rows[i].cells[1].innerHTML;
			document.getElementById('effectLevel').value= rows[i].cells[2].innerHTML;
			document.getElementById('description').value=rows[i].cells[3].innerHTML;
			document.getElementById('lightForAllocate').style.display='block';
			break;
		}
	}
}
function closeAllocateItem(){
	document.getElementById('riskId').value="";
	document.getElementById('possibility').value= "";
	document.getElementById('effectLevel').value= "";
	document.getElementById('description').value= "";
	var count = <%=request.getAttribute("developerCount")%>;
	for(var i=0;i<count;i++){
		var checkboxId="developer"+i;
		if(document.getElementById(checkboxId).checked==true){
			document.getElementById(checkboxId).checked=false;
		}
	}
	document.getElementById('lightForAllocate').style.display='none';
}
function allocateRiskItem(){
	var count = <%=request.getAttribute("developerCount")%>;
	var follower = "";
	for(var i=0;i<count;i++){
		var checkboxId="developer"+i;
		if(document.getElementById(checkboxId).checked==true){
			var parts = document.getElementById(checkboxId).value.split(":");
			follower = follower + parts[0];
			document.getElementById(checkboxId).checked=false;
			follower = follower + ",";
		}
	}
	if(follower==""){
		alert("至少要分配一个开发者来跟踪该风险");
	}
	else{
		follower = follower.substring(0,follower.length-1);
		//更新表格内容
		var riskId = document.getElementById('riskId').value;
		var table = document.getElementById('raRiskList');
		var rows = table.rows;
		for(var i=1;i<rows.length;i++){
			if(rows[i].cells[0].innerHTML==riskId){
				rows[i].cells[4].innerHTML = "<input class='submit' type='button' name='modifyItem' value='修改' onclick=\"modifyRow(\'"+riskId+"\')\" />";
				rows[i].cells[5].innerHTML = follower;
				break;
			}
		}
		//设置分配风险子界面不显示
		document.getElementById('riskId').value="";
		document.getElementById('possibility').value= "";
		document.getElementById('effectLevel').value= "";
		document.getElementById('description').value= "";
		document.getElementById('lightForAllocate').style.display='none';
	}
}
function modifyRow(riskId){
	var table = document.getElementById('raRiskList');
	var rows = table.rows;
	var follower;
	var i=0;
	for(i=1;i<rows.length;i++){
		if(rows[i].cells[0].innerHTML==riskId){
			document.getElementById('riskId').value=riskId;
			document.getElementById('possibility').value= rows[i].cells[1].innerHTML;
			document.getElementById('effectLevel').value= rows[i].cells[2].innerHTML;
			document.getElementById('description').value= rows[i].cells[3].innerHTML;
			follower = rows[i].cells[5].innerHTML;
			break;
		}
	}
	if(follower!="暂无"){
		var count = <%=request.getAttribute("developerCount")%>;
		var followerList = follower.split(",");
		var j=0;
		for(i=0;i<count;i++){
			var checkboxId="developer"+i;
			var parts = document.getElementById(checkboxId).value.split(":");
			for(j=0;j<followerList.length;j++){
				if(followerList[j]==parts[0]){
					document.getElementById(checkboxId).checked = true;
					break;
				}
			}
		}
	}
	document.getElementById('lightForAllocate').style.display='block';
}
function ensureAllocateRisk(){
	var table = document.getElementById('raRiskList');
	var rows = table.rows;
	var follower = "";
	var i=0;
	var unFinished = 0;
	for(i=1;i<rows.length;i++){
		if(rows[i].cells[5].innerHTML=="暂无"){
			unFinished = 1;
			break;
		}
	}
	if(unFinished==1){
		alert("还有未分配的风险");
	}
	else{
		var raDescription = document.getElementById('raDescription').value;
		if(raDescription==""){
			alert("必须填写风险计划描述");
		}
		else{
			for(i=1;i<rows.length;i++){
				follower = follower + rows[i].cells[0].innerHTML;
				follower = follower + "\",\"";
				follower = follower + rows[i].cells[5].innerHTML;
				if(i!=rows.length-1){
					follower = follower + "\":\"";
				}
			}
			window.location.href='<%=path%>/RAAddServlet?followerString='+follower+'&raDescription='+raDescription;
		}
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