<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" import="common.util.*"%>
<%@page language="java" import="java.util.HashMap"%>
<%@page language="java" import="java.security.interfaces.RSAPublicKey"%>
<%@page language="java" import="java.security.interfaces.RSAPrivateKey"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
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
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="../css/frame.css" type="text/css"></link>
<script type="text/javascript"
	src="../../component/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../js/menuControl.js"></script>
<script type="text/javascript" src="../../component/RSA/RSA.js"></script>
<script type="text/javascript" src="../js/iframe.js"></script>
<script type="text/javascript" src="../../component/common/js/common.js"></script>
</head>
<body>
	<div class="tip" id="basePath" basepath=<%= "\""+basePath+"\"" %>
		style="display: none;"
		publicKeyExponent=<%="\""+publicKeyExponent+"\"" %>
		publicKeyModulus=<%="\""+publicKeyModulus+"\"" %>></div>
	<!-- 系统头部框 -->
	<div class="main">
		<div class="flag">
			<img src="../../static/images/warehouse.png">
		</div>
		<div class="title">
			<p class="vintage4">仓库管理系统</p>
			<br> <span class="vintage5">登录时间：</span><span class="vintage5"><%=session.getAttribute("loginTime")== null ? "":session.getAttribute("loginTime")%></span>
		</div>
		<div class="sys-opt font">
			<ul>
				<li><%=session.getAttribute("userName") == null ? "":session.getAttribute("userName") %>
					<ul>
						<li onclick="getpersonInfo()">个人信息</li>
						<li onclick="open_updateKey()">修改密码</li>
						<li onclick="quit()">退出登录</li>
					</ul></li>
			</ul>
		</div>
	</div>
	<!-- 菜单栏 -->
	<iframe id="menuFrame" name="menuFrame" src="frame-1.jsp"
		frameBorder="0" scrolling="no"></iframe>
	<!-- 功能页面 -->
	<iframe id="mainFrame" src="frame-2.jsp" frameBorder="0" scrolling="no"></iframe>

	<!-- 个人信息窗口 -->
	<div id="personalInfo-block" class="personalInfo-block table font">
		<div class="personalInfo-title">
			<div class="personalInfo-title-info">
				<span>个人详细信息</span>
			</div>
			<div class="personalInfo-title-quit">
				<div class="quit-x" onclick="close_personInfo()"></div>
			</div>
		</div>
		<div class="personalInfo-body">
			<div class="background-image">
				<div class="background-body">
					<div class="personalInfo-body-info">
						<div class="personalInfo-photo-block">
							<div class="person-image"></div>
						</div>
						<div class="table-block vintage1">
							<table>
								<tr>
									<td class="td-tip">姓名：</td>
									<td class="td-content"><input id="userName"
										name="personInfo" type="text" readonly="readonly"></td>
									<td class="td-tip">性别：</td>
									<td class="td-content"><input id="sex" name="personInfo"
										type="text" readonly="readonly"></td>
									<td class="td-tip">年龄：</td>
									<td class="td-content"><input id="userAge"
										name="personInfo" type="text" readonly="readonly"></td>
								</tr>
								<tr>
									<td>出生日期：</td>
									<td><input id="birthday" name="personInfo" type="text"
										readonly="readonly"></td>
									<td>身份证：</td>
									<td><input id="IDcard" name="personInfo" type="text"
										readonly="readonly"></td>
									<td>联系电话：</td>
									<td><input id="phone" name="personInfo" type="text"
										readonly="readonly"></td>
								</tr>
								<tr>
									<td>单位名称：</td>
									<td><input id="departCode" name="personInfo" type="text"
										readonly="readonly"></td>
									<td>入职时间：</td>
									<td><input id="hireDate" name="personInfo" type="text"
										readonly="readonly"></td>
								</tr>
								<tr></tr>
							</table>
						</div>
					</div>
					<div class="personalInfo-body-quit">
						<input type="button" value="确认" onclick="update_personInfo()"><span
							class="placeholders"></span><input type="button" value="修改"
							onclick="remove_readonly()"><span class="placeholders"></span><input
							type="button" value="返回" onclick="close_personInfo()">
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改密码 -->
	<div id="update-block" class="personalInfo-block table font">
		<div class="personalInfo-title">
			<div class="personalInfo-title-info">
				<span>密码修改</span>
			</div>
			<div class="personalInfo-title-quit">
				<div class="quit-x" onclick="close_updateKey()"></div>
			</div>
		</div>
		<div class="personalInfo-body">
			<div class="background-image">
				<div class="background-body">
					<div class="personalInfo-body-info">
						<div class="personalInfo-photo-block"></div>
						<div class="table-block2 vintage1">
							<table>
								<tr>
									<td>原始密码：</td>
									<td><input name="update_key" id="old_password"
										type="password" onblur="check_oldkey()"></td>
									<td><img name="update_key" class="check-flag"
										id="oldkey_flag" src="<%= basePath %>static/images/f.png"
										flag="0"></td>
								</tr>
								<tr>
									<td>新的密码：</td>
									<td><input name="update_key" id="new_password"
										type="password" onblur="check_newkey()"></td>
									<td><img id="newkey_flag" name="update_key"
										class="check-flag" src="<%= basePath %>static/images/f.png"
										flag="0"></td>
									<!-- <span id="newkey_flag"class="vintage5"></span> -->
								</tr>
								<tr>
									<td>再次输入：</td>
									<td><input name="update_key" id="again_password"
										type="password" onblur="check_againkey()"></td>
									<td><img id="againkey_flag" name="update_key"
										class="check-flag" src="<%= basePath %>static/images/f.png"
										flag="0"></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="personalInfo-body-quit">
						<input type="button" value="确认" onclick="update_key_submit()"><span
							class="placeholders"></span><input type="button" value="返回"
							onclick="close_updateKey()">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
  	/* 检查密码 */
  	//原始密码
  	function check_oldkey(){
  		var flag = false;
  		var basepath = $("#basePath").attr("basePath");
  		var url = basepath+"system/checkword.spring";
  		var orgPwd = $("#old_password").val();
  		RSAUtils.setMaxDigits(200);  
  		var publicKeyExponent = $("#basePath").attr("publicKeyExponent");
  		var publicKeyModulus = $("#basePath").attr("publicKeyModulus");
  		var key = new RSAUtils.getKeyPair(publicKeyExponent, "", publicKeyModulus);  
  		var password = RSAUtils.encryptedString(key,orgPwd.split("").reverse().join(""));  
  		var args = {"password":password};
  		$.post(url,args,function(data){
  			var jsonobj = JSON.parse(data);
  			if(jsonobj.result == '1'){
  				flag = true;
  			}
  		});
  		key_util("oldkey_flag",flag);	
  	}
  	//新密码
	function check_newkey(){
		var retpassword = /[a-zA-Z0-9_]{6,20}$/;
		var newkey = $("#new_password").val();
		var flag = !retpassword.test(newkey);
		key_util("newkey_flag",flag);	
  	}
  	//再次输入
	function check_againkey(){
		var againkey = $("#again_password").val();
		var flag = true;
		if(againkey != null && againkey != ""){
			var newkey = $("#new_password").val();
	  		flag = (againkey != newkey );
		}
  		key_util("againkey_flag",flag);	
	}
  	function key_util(idstr,flag){
  		if(flag){
  			$("#"+idstr).attr("src","../../static/images/f.png");
  			$("#"+idstr).attr("flag","0");
  		}else{
  			$("#"+idstr).attr("src","../../static/images/t.png");
  			$("#"+idstr).attr("flag","1");
  		}
  	}
  </script>
</html>