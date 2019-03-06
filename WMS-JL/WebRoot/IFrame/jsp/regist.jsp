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
	if(session.getAttribute("privateKey") != null){
		session.removeAttribute("privateKey");
	}
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
    <link rel="stylesheet" href="../../component/select2-4.0.1-rc.1/dist/css/select2.css" type="text/css"></link>
    <link rel="stylesheet" href="../../IFrame/css/frame.css" type="text/css"></link>
	<script type="text/javascript" src="../../component/jquery/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="../../component/jquery-md5/jquery-md5-new.js"></script>
	<script type="text/javascript" src="../../component/select2-4.0.1-rc.1/dist/js/select2.full.js"></script>
	<script type="text/javascript" src="../../component/common/js/common.js"></script>
	<script type="text/javascript" src="../../IFrame/js/address.js"></script>
	<script type="text/javascript" src="../../IFrame/js/regist.js"></script>
	<script type="text/javascript" src="../../component/RSA/RSA.js"></script>
	<script type="text/javascript" src="../../IFrame/js/login.js"></script>
    </head>
  <body style="background: url(../../static/images/login-bg.jpg) center top repeat-x #FFF;height:100%;">
	<div class="tip" style="display: none;"id="basePath" basepath=<%= "\""+basePath+"\"" %> publicKeyExponent=<%="\""+publicKeyExponent+"\"" %> publicKeyModulus=<%="\""+publicKeyModulus+"\"" %> ></div>
	<div id="regist-block"class="regist-block table font">
	  	<div class="personalInfo-title">
	  		<div class="personalInfo-title-info"><span>用户注册</span></div>
	  		<div class="personalInfo-title-quit"><div class="quit-x" onclick="close_regist()"></div></div>
	  	</div>
	  	<div class="personalInfo-body">
	  	<div class="background-image">
	  		<div class="background-body">
		  		<div class="personalInfo-body-info">
		  			<div class="table-block3 vintage1">
			  			<table>
			  				<tr><td >注册人姓名：</td>
			  					<td style="width:286px;"><input id="register" name="registInfo" type="text"onblur="check_registName($(this).val())"></td><td style="width: 35%;"></td>
			  					<td ><img id="register_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>身份证号码：</td>
			  					<td><input id="registerIDcard" name="registInfo" type="text"onblur="check_registerIDcard($(this).val())"></td><td></td>
			  					<td><img id="registerIDcard_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>联系电话：</td>
			  					<td><input id="registerPhone" name="registInfo" type="text"onblur="check_registerPhone($(this).val())"></td><td></td>
			  					<td><img id="registerPhone_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>邮箱地址：</td>
			  					<td><input id="registEmail" name="registInfo" type="text"onblur="check_registEmail($(this).val())"></td><td></td>
			  					<td><img id="registEmail_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>登录密码：</td>
			  					<td><input id="password" name="registInfo" type="password"onblur="check_password($(this).val())"></td><td><input type="hidden" id="userID" ></td>
			  					<td><img id="password_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>单位名称：</td>
			  					<td ><input id="unitName" name="registInfo" type="text"onblur="check_unitName($(this).val())"></td>		<td></td>
			  					<td><img id="unitName_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>单位性质：</td>
			  					<td><input id="unitProperties" name="registInfo" onchange="check_select_1($(this).val())"></td><td></td>
			  					<td><img id="unitProperties_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>单位类别：</td>
			  					<td><input id="unitType" name="registInfo" onchange="check_select_2($(this).val())"></td><td></td>
			  					<td><img id="unitType_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  				<tr><td>单位地址：</td>
			  					<td colspan="2" ><input id="location_province" type="text" name="registInfo"style="width: 30.9%;">&nbsp;&nbsp;<input id="location_city" type="text" name="registInfo"style="width: 32.5%;">&nbsp;&nbsp;<input id="location_area" type="text" name="registInfo"style="width: 32.5%;"></td>
			  					<td></td>
			  				<tr><td></td>
			  					<td colspan="2"><input id="address" name="registInfo" type="text"onblur="check_locationANDaddress($(this).val())"placeholder="详细地址..."></td>
			  					<td><img id="location_flag" name="registInfo" class="check-flag" src="../../static/images/f.png"flag="0"></td></tr>
			  			</table>
		  			</div>
		  		</div>
		  		<div class="personalInfo-body-quit"><input type="button" value="提交" onclick="regist_submit()"><span class="placeholders"></span><span class="placeholders"></span><input type="button" value="返回" onclick="close_regist()"></div>
	  		</div>
	  		</div>
	  	</div>
	  </div>

<script type="text/javascript">
	
</script>
  </body>
</html>