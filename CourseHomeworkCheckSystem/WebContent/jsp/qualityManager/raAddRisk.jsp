<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="rms.servlet.business.RiskConditionBean"%>
    <%@page import="newproject.model.RiskCondition"%>
    <%@page import="java.util.List"%>
    <%@page import="java.util.ArrayList"%>
    <%@page import="rms.common.IdProducer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<% String path = request.getContextPath();
	   int number = 1;%>
	<title>RA overview for quality manager</title>
	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/lightbox.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/progressbar.css" />
  	<script type="text/javascript" src="<%=path%>/js/modernizr-1.5.min.js"></script>
    <script type="text/javascript" src="<%=path %>/calc/calendar.js"></script>
    <script type="text/javascript" src="<%=path %>/calc/calendar-en.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=path %>/calc/calendar-system.css"/>
</head>
<body>
	<div id="main">
		<jsp:useBean id="mostAddedRisks"
			class="rms.servlet.business.RiskConditionBean"
			scope="page"></jsp:useBean>
		<jsp:useBean id="problemedRisks"
			class="rms.servlet.business.RiskConditionBean"
			scope="page"></jsp:useBean>
		<jsp:useBean id="riskCondition"
			class="newproject.model.RiskCondition"
			scope="page"></jsp:useBean>
		<jsp:useBean id="risk"
			class="newproject.model.RiskItem"
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
			
			<form>
	          <div class="form_settings" style="margin-top:100px;margin-left:100px">
	          	<p>
	          	  <span style="width:300px;font-size:20px;">方式一：历史风险推荐并导入</span>
	          	</p>
	          	<br>
	          	<br>
	          	<p><span>起始日期</span>
	          	  <input type="text" id="startDate" size="12" value="选择日期" onclick="calShow('startDate');" onfocus="calShow('startDate');" style="width:80px;margin-left:-40px" onblur="checkStartDate()">
	          	</p>
	          	<p style="margin-top:-40px;margin-left:220px"><span>截止日期</span>
	          	  <input type="text" id="endDate" size="12" value="选择日期" onclick="calShow('endDate');" onfocus="calShow('endDate');" style="width:80px;margin-left:-40px" onblur="checkEndDate()">
	          	</p>
	          	<input class="submit" type="button" name="ensure" value="查找" style="margin-top:-60px;margin-left:450px" onclick="suggestRisks()"/>
	          </div>
	          <br>
	          <%RiskConditionBean r1 = (RiskConditionBean) request.getAttribute("mostAddedRisks");
	            RiskConditionBean r2 = (RiskConditionBean) request.getAttribute("problemedRisks");
	            if(r1!=null&&r2!=null&&r1.getRiskList()!=null&&r2.getRiskList()!=null){ %>
	          <div class="form_settings" style="margin-left:100px">
		          <p><span style="width:700px">1.该时间段被识别最多的风险：（不可选中的为已经导入的风险条目）</span></p>
		          <br>
		          <br>
		          <p style="margin-left:-100px">
	          	  <%
	                for(int i=0;i<r1.getSize();i++){
	                	RiskCondition rc = r1.getRiskCondition(i);
						pageContext.setAttribute("riskCondition",rc);
						pageContext.setAttribute("risk",rc.getRisk());	
	          	  %>
	         	    	<label id="mostAddedLabel<%=i %>"><input id="mostAdded<%=i %>" type="checkbox" value="<jsp:getProperty name="risk" property="riskId" />;<jsp:getProperty name="risk" property="title" />;<jsp:getProperty name="risk" property="description" />" /><jsp:getProperty name="risk" property="title" /></label><br>
	          	  <%} %>
	          	  </p>
		          <p><span style="width:700px">2.该时间段演变成问题最多的风险：（不可选中的为已经导入的风险条目）</span></p>
		          <br>
		          <br>
		          <p style="margin-left:-100px">
	          	  <%
	                for(int i=0;i<r2.getSize();i++){
	                	RiskCondition rc = r1.getRiskCondition(i);
						pageContext.setAttribute("riskCondition",rc);
						pageContext.setAttribute("risk",rc.getRisk());
	          	  %>
	         	    	<label id="problemedLabel<%=i%>"><input id="problemed<%=i %>" type="checkbox" value="<jsp:getProperty name="risk" property="riskId" />;<jsp:getProperty name="risk" property="description" />" /><jsp:getProperty name="risk" property="title" /></label><br>
	          	  <%} %>
	          	  </p>
	          	  <input class="submit" type="button" name="ensure" value="勾选添加" style="margin-top:-60px;margin-left:450px" onclick="addSuggestRisks()"/>
          	  </div>
	          <%} %>
			</form>

			<form method="post">
	          <div class="form_settings" style="margin-top:30px;margin-left:100px">
	          	<p><span style="width:300px;font-size:20px;margin-top:5px;">方式二：创建新的风险并添加</span></p>
	          	<br>
	          	<br>
	          	<p><input id="addRiskItem" class="submit" type="button" value="创建风险" onclick="addNewRisk()" style="font-size:16px;margin-top:-60px;margin-left:450px" /></p>
	          </div>
			</form>
			
		    <br>
			<div id="raRiskListDiv">
			  <HR style="border:1 dashed #987cb9" width="100%" color=#987cb9 SIZE=0.5>
	          <h2 align="center">风险列表</h2>
	          <table id="raRiskList" style="width:100%; border-spacing:0;">
		        <tr><th>风险编号</th><th>标题</th><th>可能性</th><th>影响程度</th><th>风险状态</th><th>触发器/阈值</th><th>风险内容</th><th>修改</th><th>删除</th></tr>
		      </table>
	        </div>
	        <div class="form_settings" style="float:right">
		        <input class="submit" type="button" value="下一步" onclick="ensureAddRisk()"/>
		    </div>
	        
	        <div id="lightForImport" class="white_content">
	      	  <p></p>
	      	  <h2 align="center">导入风险条目</h2>
	      	  <input type="text" id="importIdList" value="" style="display:none"/>
	      	  <input type="text" id="importTitleList" value="" style="display:none"/>
	      	  <input type="text" id="importDesList" value="" style="display:none"/>
	      	  <p></p>
	          <div class="form_settings" style="margin-left:60px" >
	            <p><span>风险编号</span><input id="riskIdImport" type="text" name="riskId" value="" disabled="disabled"/></p>
	            <p><span>风险标题</span><input id="riskTitleImport" type="text" name="riskTitle" value="" disabled="disabled"/></p>
	            <p><span>可能性</span>
				  <select id="possibilityImport" name="possibility">
          	    	<option value="High" selected="selected">高</option>
          	    	<option value="Medium">中</option>
          	    	<option value="Low">低</option>
          	      </select>
				</p>
				<p></p>
	            <p><span>影响程度</span>
				  <select id="effectLevelImport" name="effectLevel">
          	    	<option value="High" selected="selected">高</option>
          	    	<option value="Medium">中</option>
          	    	<option value="Low">低</option>
          	      </select>
          	    </p>
				<p></p>
	            <p><span>风险状态</span>
				  <select id="riskStateImport" name="riskState">
          	    	<option value="UnRemoved" selected="selected">未排除</option>
          	    	<option value="Removed">已排除</option>
          	    	<option value="Problem">演变成问题</option>
          	    	<option value="ProblemSolved">问题得到解决</option>
          	      </select>
          	    </p>
				<p></p>
	            <p><span>触发器/阈值</span><input id="thresholdImport" type="text" name="threshold" value="" /></p>
	            <p><span>风险内容</span><textarea id="descriptionImport" rows="6" cols="50" name="description"></textarea></p>
	          </div>
	          <div class="form_settings">
	            <input class="submit" type="button" value="取消" onclick="closeImportItem()"/>
	            <input class="submit" type="button" name="addItemEnsure" value="确认" onclick="importRiskItem()"/>
	          </div>
			</div>
			
			<div id="lightForAdd" class="white_content" style="display:none">
	      	  <p></p>
	      	  <h2 align="center">创建并添加风险条目</h2>
	      	  <p></p>
	          <div class="form_settings" style="margin-left:60px" >
	            <p><span>风险编号</span><input id="riskIdAdd" type="text" name="riskId" value="" disabled="disabled"/></p>
	            <p><span>风险标题</span><input id="riskTitleAdd" type="text" name="riskTitle" value=""/></p>
	            <p><span>可能性</span>
				  <select id="possibilityAdd" name="possibility">
          	    	<option value="High" selected="selected">高</option>
          	    	<option value="Medium">中</option>
          	    	<option value="Low">低</option>
          	      </select>
				</p>
				<p></p>
	            <p><span>影响程度</span>
				  <select id="effectLevelAdd" name="effectLevel">
          	    	<option value="High" selected="selected">高</option>
          	    	<option value="Medium">中</option>
          	    	<option value="Low">低</option>
          	      </select>
          	    </p>
				<p></p>
	            <p><span>风险状态</span>
				  <select id="riskStateAdd" name="riskState">
          	    	<option value="UnRemoved" selected="selected">未排除</option>
          	    	<option value="Removed">已排除</option>
          	    	<option value="Problem">演变成问题</option>
          	    	<option value="ProblemSolved">问题得到解决</option>
          	      </select>
          	    </p>
				<p></p>
	            <p><span>触发器/阈值</span><input id="thresholdAdd" type="text" name="threshold" value="" /></p>
	            <p><span>风险内容</span><textarea id="descriptionAdd" rows="6" cols="50" name="description"></textarea></p>
	          </div>
	          <div class="form_settings">
	            <input class="submit" type="button" value="取消" onclick="closeAddItem()"/>
	            <input class="submit" type="button" name="addItemEnsure" value="确认添加" onclick="addRiskItem()"/>
	          </div>
			</div>
			
			<div id="lightForModify" class="white_content" style="display:none">
	      	  <p></p>
	      	  <h2 align="center">修改风险条目</h2>
	      	  <p></p>
	          <div class="form_settings" style="margin-left:50px" >
	            <p><span>风险编号</span><input id="riskIdM" type="text" name="riskId" value="" disabled="disabled"/></p>
	            <p><span>风险标题</span><input id="riskTitleM" type="text" name="riskTitle" value=""/></p>
	            <p><span>可能性</span>
				  <select id="possibilityM" name="possibility">
          	    	<option value="High" selected="selected">高</option>
          	    	<option value="Medium">中</option>
          	    	<option value="Low">低</option>
          	      </select>
				</p>
				<p></p>
	            <p><span>影响程度</span>
				  <select id="effectLevelM" name="effectLevel">
          	    	<option value="High" selected="selected">高</option>
          	    	<option value="Medium">中</option>
          	    	<option value="Low">低</option>
          	      </select>
          	    </p>
				<p></p>
	            <p><span>风险状态</span>
				  <select id="riskStateM" name="riskState">
          	    	<option value="UnRemoved" selected="selected">未排除</option>
          	    	<option value="Removed">已排除</option>
          	    	<option value="Problem">演变成问题</option>
          	    	<option value="ProblemSolved">问题得到解决</option>
          	      </select>
          	    </p>
				<p></p>
	            <p><span>触发器/阈值</span><input id="thresholdM" type="text" name="threshold" value="" /></p>
	            <p><span>风险内容</span><textarea id="descriptionM" rows="6" cols="50" name="description"></textarea></p>
	          </div>
	          <div class="form_settings">
	            <input class="submit" type="button" value="取消" onclick="closeModifyItem()"/>
	            <input class="submit" type="button" name="addItemEnsure" value="确认修改" onclick="modifyRiskItem()"/>
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
function suggestRisks(){
	var startDateString = document.getElementById("startDate").value;
	var endDateString = document.getElementById("endDate").value;
	if(startDateString=="选择日期"||endDateString=="选择日期"){
		alert("请先选择风险条目推荐的起始和结束日期");
	}
	else{
		window.location.href='<%=path%>/RASuggestRiskServlet?startDate='+startDateString+'&endDate='+endDateString;
	}
}
function addSuggestRisks(){
	var r1_size = <%=request.getAttribute("r1_size")%>;
	var r2_size = <%=request.getAttribute("r2_size")%>;
	var riskIdList = new Array();
	var riskTitleList = new Array();
	var riskDesList = new Array();
	var length = 0;
	var exist = 0;
	var i=0;
	var j=0;
	for(i=0;i<r1_size;i++){
		var checkboxId="mostAdded"+i;
		if(document.getElementById(checkboxId).checked==true){
			var parts = document.getElementById(checkboxId).value.split(";");
			for(j=0;j<length;j++){
				if(riskIdList[j]==parts[0]){
					exist = 1;
					break;
				}
			}
			if(exist==0){
				riskIdList[length]=parts[0];
				riskTitleList[length]=parts[1];
				riskDesList[length]=parts[2];
				length=length+1;
			}
			else{
				exist=0;
			}
		}
	}
	for(i=0;i<r2_size;i++){
		var checkboxId="problemed"+i;
		if(document.getElementById(checkboxId).checked==true){
			var parts = document.getElementById(checkboxId).value.split(";");
			for(j=0;j<length;j++){
				if(riskIdList[j]==parts[0]){
					exist = 1;
					break;
				}
			}
			if(exist==0){
				riskIdList[length]=parts[0];
				riskTitleList[length]=parts[1];
				riskDesList[length]=parts[2];
				length=length+1;
			}
			else{
				exist=0;
			}
		}
	}
	if(length==0){
		alert("导入风险条目前先选择至少一条风险");
	}
	else{
		document.getElementById('riskIdImport').value=riskIdList[0];
		document.getElementById('riskTitleImport').value=riskTitleList[0];
		document.getElementById('descriptionImport').value=riskDesList[0];
		var idString = "";
		var titleString = "";
		var desString = "";
		for(i=1;i<length;i++){
			idString = idString+riskIdList[i];
			titleString = titleString+riskTitleList[i];
			desString = desString+riskDesList[i];
			if(i!=length-1){
				idString = idString+";";
				desString = desString+";";
			}
		}
		document.getElementById('importIdList').value=idString;
		document.getElementById('importIdList').value=titleString;
		document.getElementById('importDesList').value=desString;
		document.getElementById('lightForImport').style.display='block';
	}
}
function closeImportItem(){
	//清除输入内容
	document.getElementById('importIdList').value="";
	document.getElementById('importTitleList').value="";
	document.getElementById('importDesList').value="";
	document.getElementById('riskIdImport').value="";
	document.getElementById('riskTitleImport').value="";
	var options = document.getElementById('possibilityImport').options;
	options[0].selected = true;
	options = document.getElementById('effectLevelImport').options;
	options[0].selected = true;
	options = document.getElementById('riskStateImport').options;
	options[0].selected = true;
	document.getElementById('thresholdImport').value="";
	document.getElementById('descriptionImport').value="";
	document.getElementById('lightForImport').style.display='none';
}
function importRiskItem(){
	//为表格添加一行风险条目
	var table = document.getElementById('raRiskList');
	var newRow = table.insertRow(table.rows.length);
    var newCel1 = newRow.insertCell(0);
    var newCel2 = newRow.insertCell(1);
    var newCel3 = newRow.insertCell(2);
    var newCel4 = newRow.insertCell(3);
    var newCel5 = newRow.insertCell(4);
    var newCel6 = newRow.insertCell(5);
    var newCel7 = newRow.insertCell(6);
    var newCel8 = newRow.insertCell(7);
    var newCel9 = newRow.insertCell(8);

    var id = document.getElementById('riskIdImport').value;
    newCel1.innerHTML = id;
    newCel2.innerHTML = document.getElementById('riskTitleImport').value;
    newCel3.innerHTML = document.getElementById('possibilityImport').value;
    newCel4.innerHTML = document.getElementById('effectLevelImport').value;
    newCel5.innerHTML = document.getElementById('riskStateImport').value;
    newCel6.innerHTML = document.getElementById('thresholdImport').value;
    newCel7.innerHTML = document.getElementById('descriptionImport').value;
    newCel8.innerHTML = "<input class='submit' type='button' name='modifyItem' value='修改' onclick=\"modifyRow(\'"+id+"\')\" />";
    newCel9.innerHTML = "<input class='submit' type='button' name='deleteItem' value='删除' onclick=\"deleteRow(\'"+id+"\')\" />";

    //使得推荐导入的相应风险条目不可选中（防止重复导入）
    var i=0;
    var r1_size = <%=request.getAttribute("r1_size")%>;
    var r2_size = <%=request.getAttribute("r2_size")%>;
    for(i=0;i<r1_size;i++){
		var checkboxId="mostAdded"+i;
		var parts = document.getElementById(checkboxId).value.split(";");
		if(parts[0]==id){
			document.getElementById(checkboxId).checked=false;
			document.getElementById(checkboxId).disabled=true;
			break;
		}
	}
	for(i=0;i<r2_size;i++){
		var checkboxId="problemed"+i;
		var parts = document.getElementById(checkboxId).value.split(";");
		if(parts[0]==id){
			document.getElementById(checkboxId).checked=false;
			document.getElementById(checkboxId).disabled=true;
			break;
		}
	}
    
    //下一个待导入的风险条目
	var idString = document.getElementById('importIdList').value;
    var titleString = document.getElementById('importTitleList').value;
	var desString = document.getElementById('importDesList').value;
	if(idString==""){
		document.getElementById('lightForImport').style.display='none';
		alert("风险条目导入完成");
	}
	else{
		alert("风险"+id+"导入成功，点击确定继续导入");
		var idParts = idString.split(";");
		var titleParts = titleString.split(";");
		var desParts = desString.split(";");
		document.getElementById('riskIdImport').value=idParts[0];
		document.getElementById('riskTitleImport').value=titleString[0];
		var options = document.getElementById('possibilityImport').options;
		options[0].selected = true;
		options = document.getElementById('effectLevelImport').options;
		options[0].selected = true;
		options = document.getElementById('riskStateImport').options;
		options[0].selected = true;
		document.getElementById('thresholdImport').value="";
		document.getElementById('descriptionImport').value=desParts[0];
		idString = "";
		desString = "";
		for(i=1;i<idParts.length;i++){
			idString = idString+idParts[i];
			desString = desString+desParts[i];
			if(i!=idParts.length-1){
				idString = idString+";";
				desString = desString+";";
			}
		}
		document.getElementById('importIdList').value=idString;
		document.getElementById('importDesList').value=desString;
	}
}
function addNewRisk(){
	var day = new Date();
	var timeArray = new Array(6);
	timeArray[0] = day.getFullYear();
	timeArray[1] = day.getMonth()+1;
	timeArray[2] = day.getDate();
	timeArray[3] = day.getHours();
	timeArray[4] = day.getMinutes();
	timeArray[5] = day.getSeconds();
	var newRiskId = "r";
	var i=0;
	for(i=0;i<timeArray.length;i++){
		if(timeArray[i]<10){
			newRiskId = newRiskId+"0"+timeArray[i];
		}
		else{
			newRiskId = newRiskId+timeArray[i];
		}
	}
	document.getElementById('riskIdAdd').value=newRiskId;
	document.getElementById('lightForAdd').style.display='block';
}
function closeAddItem(){
	//清除输入内容
	document.getElementById('riskIdAdd').value="";
	document.getElementById('riskTitleAdd').value="";
	var options = document.getElementById('possibilityAdd').options;
	options[0].selected = true;
	options = document.getElementById('effectLevelAdd').options;
	options[0].selected = true;
	options = document.getElementById('riskStateAdd').options;
	options[0].selected = true;
	document.getElementById('thresholdAdd').value="";
	document.getElementById('descriptionAdd').value="";
	document.getElementById('lightForAdd').style.display='none';
}
function addRiskItem(){
	//为表格添加一行风险条目
	var table = document.getElementById('raRiskList');
	var newRow = table.insertRow(table.rows.length);
    var newCel1 = newRow.insertCell(0);
    var newCel2 = newRow.insertCell(1);
    var newCel3 = newRow.insertCell(2);
    var newCel4 = newRow.insertCell(3);
    var newCel5 = newRow.insertCell(4);
    var newCel6 = newRow.insertCell(5);
    var newCel7 = newRow.insertCell(6);
    var newCel8 = newRow.insertCell(7);
    var newCel9 = newRow.insertCell(8);

    var id = document.getElementById('riskIdAdd').value;
    newCel1.innerHTML = id;
    newCel2.innerHTML = document.getElementById('riskTitleAdd').value;
    newCel3.innerHTML = document.getElementById('possibilityAdd').value;
    newCel4.innerHTML = document.getElementById('effectLevelAdd').value;
    newCel5.innerHTML = document.getElementById('riskStateAdd').value;
    newCel6.innerHTML = document.getElementById('thresholdAdd').value;
    newCel7.innerHTML = document.getElementById('descriptionAdd').value;
    newCel8.innerHTML = "<input class='submit' type='button' name='modifyItem' value='修改' onclick=\"modifyRow(\'"+id+"\')\" />";
    newCel9.innerHTML = "<input class='submit' type='button' name='deleteItem' value='删除' onclick=\"deleteRow(\'"+id+"\')\" />";

    //清除输入内容
	document.getElementById('riskIdAdd').value="";
	document.getElementById('riskTitleAdd').value="";
	var options = document.getElementById('possibilityAdd').options;
	options[0].selected = true;
	options = document.getElementById('effectLevelAdd').options;
	options[0].selected = true;
	options = document.getElementById('riskStateAdd').options;
	options[0].selected = true;
	document.getElementById('thresholdAdd').value="";
	document.getElementById('descriptionAdd').value="";
	document.getElementById('lightForAdd').style.display='none';
}
function modifyRow(riskId){
	var table = document.getElementById('raRiskList');
	var rows = table.rows;
	for(var i=1;i<rows.length;i++){
		if(rows[i].cells[0].innerHTML==riskId){
			document.getElementById('riskIdM').value=riskId;
			document.getElementById('riskTitleM').value= rows[i].cells[1].innerHTML;
			document.getElementById('possibilityM').value= rows[i].cells[2].innerHTML;
			document.getElementById('effectLevelM').value= rows[i].cells[3].innerHTML;
			document.getElementById('riskStateM').value= rows[i].cells[4].innerHTML;
			document.getElementById('thresholdM').value=rows[i].cells[5].innerHTML;
			document.getElementById('descriptionM').value=rows[i].cells[6].innerHTML;
			document.getElementById('lightForModify').style.display='block';
			break;
		}
	}
}
function deleteRow(riskId){
	var table = document.getElementById('raRiskList');
	var rows = table.rows;
	for(var i=1;i<rows.length;i++){
		if(rows[i].cells[0].innerHTML==riskId){
			table.deleteRow(i);
			//如果删除的是从推荐中导入的风险，则将推荐导入相应的风险条目改为可选中
			var j=0;
		    var r1_size = <%=request.getAttribute("r1_size")%>;
		    var r2_size = <%=request.getAttribute("r2_size")%>;
		    for(j=0;j<r1_size;j++){
				var checkboxId="mostAdded"+j;
				var parts = document.getElementById(checkboxId).value.split(";");
				if(parts[0]==riskId){
					document.getElementById(checkboxId).disabled=false;
					break;
				}
			}
			for(j=0;j<r2_size;j++){
				var checkboxId="problemed"+j;
				var parts = document.getElementById(checkboxId).value.split(";");
				if(parts[0]==riskId){
					document.getElementById(checkboxId).disabled=false;
					break;
				}
			}
			break;
		}
	}
}
function closeModifyItem(){
	//清除输入内容
	document.getElementById('riskIdM').value="";
	document.getElementById('riskTitleM').value="";
	var options = document.getElementById('possibilityM').options;
	options[0].selected = true;
	options = document.getElementById('effectLevelM').options;
	options[0].selected = true;
	options = document.getElementById('riskStateM').options;
	options[0].selected = true;
	document.getElementById('thresholdM').value="";
	document.getElementById('descriptionM').value="";
	document.getElementById('lightForModify').style.display='none';
}
function modifyRiskItem(){
	var table = document.getElementById('raRiskList');
	var rows = table.rows;
    var riskId = document.getElementById('riskIdM').value;
	for(var i=1;i<rows.length;i++){
		if(rows[i].cells[0].innerHTML==riskId){
			rows[i].cells[1].innerHTML=document.getElementById('riskTitleM').value;
			rows[i].cells[2].innerHTML=document.getElementById('possibilityM').value;
			rows[i].cells[3].innerHTML=document.getElementById('effectLevelM').value;
			rows[i].cells[4].innerHTML=document.getElementById('riskStateM').value;
			rows[i].cells[5].innerHTML=document.getElementById('thresholdM').value;
			rows[i].cells[6].innerHTML=document.getElementById('descriptionM').value;
			//clear contents
			document.getElementById('riskIdM').value="";
			document.getElementById('riskTitleM').value="";
			var options = document.getElementById('possibilityM').options;
			options[0].selected = true;
			options = document.getElementById('effectLevelM').options;
			options[0].selected = true;
			options = document.getElementById('riskStateM').options;
			options[0].selected = true;
			document.getElementById('thresholdM').value="";
			document.getElementById('descriptionM').value="";
			document.getElementById('lightForModify').style.display='none';
			break;
		}
	}
}
function ensureAddRisk(){
	var riskList = "";
	var table = document.getElementById('raRiskList');
	var rows = table.rows;
	for(var j=1;j<rows.length;j++){
		riskList += rows[j].cells[0].innerHTML;
		riskList += "\",\"";
		riskList += rows[j].cells[1].innerHTML;
		riskList += "\",\"";
		riskList += rows[j].cells[2].innerHTML;
		riskList += "\",\"";
		riskList += rows[j].cells[3].innerHTML;
		riskList += "\",\"";
		riskList += rows[j].cells[4].innerHTML;
		riskList += "\",\"";
		riskList += rows[j].cells[5].innerHTML;
		riskList += "\",\"";
		riskList += rows[j].cells[6].innerHTML;
		if(j!=rows.length-1){
			riskList += "\":\"";
		}
	}
	window.location.href='<%=path%>/RAEnsureAddRiskServlet?riskListString='+riskList;
}
</script>
<script type="text/javascript">
$(function() {
	var startDate = "<%=request.getAttribute("startDate") %>";
	var endDate = "<%=request.getAttribute("endDate") %>";
	if(startDate!="null"&&endDate!="null"){
		document.getElementById("startDate").value = startDate;
		document.getElementById("endDate").value = endDate;
	}
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