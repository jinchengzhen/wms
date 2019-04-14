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
<link rel="stylesheet" href="../css/frame.css" type="text/css"></link>
<script type="text/javascript"
	src="../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../js/menuControl.js"></script>
<title>菜单</title>
</head>
<body>
	<div class="tip" id="basePath" basepath=<%= "\""+basePath+"\"" %>
		style="display: none;"></div>
	<div class="menu font">
		<div class="menu-title" onclick="showMenu()">功能菜单</div>
		<ul id="menu_select">
		</ul>
	</div>
</body>
</html>