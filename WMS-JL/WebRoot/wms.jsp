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
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<link rel="stylesheet" href="IFrame/css/frame.css" type="text/css"></link>
<script type="text/javascript" src="component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="component/common/js/common.js"></script>
<title>仓库管理系统</title>
</head>
<style>
	body{    
    overflow-y: hidden;
    overflow-x: hidden;
}
</style>
<body>
	<div class="tip" id="basePath" basepath=<%= "\""+basePath+"\"" %> ></div>
	<div id='msg'style='display:block;position:absolute;z-index:999999999999;bottom:-40%;right:100px;width:240px; height:130px;border-right:#cccccc 1px solid;border-top:#cccccc 1px solid; border-left:#cccccc 1px solid;border-bottom:#cccccc 1px solid; background-color: #ffffff;'><div style='width:240px;height:25px; color: #ffffff; border-bottom:#cccccc 1px solid; background-color:#143251;text-align:left;text-indent:20px;line-height:25px;'>消息提示</div><div style='width:240px;height:100px;line-height:100px;text-align:center;'><span></span></div></div>
	<iframe id="wms_iframe"src="IFrame/jsp/login.jsp"></iframe>
</body>
</html>