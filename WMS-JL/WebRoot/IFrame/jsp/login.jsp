<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" import="common.util.*"%>
<%@page language="java" import="com.wms.manage.*"%>
<%@page language="java" import="java.util.HashMap"%>
<%@page language="java" import="java.security.interfaces.RSAPublicKey"%>
<%@page language="java" import="java.security.interfaces.RSAPrivateKey"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if(session.getAttribute("loginTime") != null){
		String userID = session.getAttribute("userID").toString();
		SessionManage.removeSession(userID);
	}
	HashMap<String, Object> map = RSAUtils.getKeys();  
	//生成公钥和私钥    
	RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
	RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
	//私钥保存在session中，用于解密  
	session.setAttribute("privateKey", privateKey);  
	//公钥信息保存在页面，用于加密  
	String publicKeyExponent = publicKey.getPublicExponent().toString(16);  
	String publicKeyModulus = publicKey.getModulus().toString(16);  
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>仓库管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="../css/login.css" type="text/css"></link>
<script type="text/javascript"
	src="../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../../component/RSA/RSA.js"></script>
<script type="text/javascript" src="../../IFrame/js/fun.base.js"></script>
<script type="text/javascript" src="../../IFrame/js/script.js"></script>
<script type="text/javascript" src="../../IFrame/js/login.js"></script>
<script type="text/javascript" src="../../component/common/js/common.js"></script>
<script type="text/javascript" src="../../component/common/js/load.js"></script>
</head>
<body>
	<div class="login">
		<div class="box png">
			<div class="logo png">
				<div class="logotip-1 vintage2">仓库管理系统</div>
				<div class="logotip-2 vintage5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;——让管理变得更简单</div>
			</div>
			<div class="input">
				<div class="log">
					<div class="name">
						<label>用户名</label><input type="text" class="text" id="userID"
							placeholder="用户名" name="userID" tabindex="1">
					</div>
					<div class="pwd">
						<label>密 码</label><input type="password" class="text"
							id="password" placeholder="密码" name="password" tabindex="2">
						<input id="login" type="button" class="submit" tabindex="3"
							value="登录" onclick="login()"> <input id="regist"
							type="button" class="submit" tabindex="3" value="注册"
							onclick="regist()">
						<div class="check"></div>
					</div>
					<div class="tip" id="basePath" basepath=<%= "\""+basePath+"\"" %>
						publicKeyExponent=<%="\""+publicKeyExponent+"\"" %>
						publicKeyModulus=<%="\""+publicKeyModulus+"\"" %>></div>
				</div>
			</div>
		</div>
		<div class="air-balloon ab-1 png"></div>
		<div class="air-balloon ab-2 png"></div>
		<div class="footer"></div>
	</div>



	<div
		style="text-align: center; margin: 50px 0; font: normal 14px/24px 'MicroSoft YaHei';">
	</div>
	<script type="text/javascript">
$("body").keydown(function() {
	if (event.keyCode == "13") {//keyCode=13是回车键
		document.getElementById('login').click();
	}
});
function regist(){
	window.location.href = "regist.jsp";
}
</script>
</body>
</html>