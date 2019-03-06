<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../js/menuControl.js"></script>
<title>功能窗口</title>
</head>
<style>
div{box-sizing:border-box;word-wrap: break-word;}
.font{font-family:"微软雅黑";font-size: 32px;color: green;}
.main-block{width:100%;height: 100%;padding: 1% 14% 0% 12%;float: left;display:table-cell;vertical-align:middle;text-align:center;}
.menulist-block{width:100%;height: 100%;float: left;display:table-cell;vertical-align:middle;text-align:center;}
.menutitle{width:100%;height: 138px;padding: 4px 2% 4px 0%;float: left;display:table-cell;vertical-align:middle;text-align:center;margin: 0.2%;margin-top: 0.4%;background-color: aliceblue;border-top: 16px solid darkslategray;}

.menuparent{width:3%;height: 100%;padding: 0% 0.5% 0% 0.5%;float: left;display:table-cell;vertical-align:middle;text-align:center;background-color: darkslategray;font-size: 20px; font-weight: bold;color: #fbff00;}
.menublock{cursor:pointer;width:10%;height: 100%;padding: 0.5% 2% 2% 2%;float: left;display:table-cell;vertical-align:middle;text-align:center;border: 4px solid forestgreen;margin-left: 0.5%;background-color: gainsboro; border-radius: 15px;}
.menublock:hover{border: 4px solid darkslategray;background-color: green;color: white;}
</style>
<body onclick="check_sessionIsOutdate()">
	<div class="tip" id="basePath" basepath=<%= "\""+basePath+"\"" %> style="display: none;"></div>
	<div class="main-block">
		<div id="menulist"class="menulist-block font">
		</div>
	</div>
</body>
</html>