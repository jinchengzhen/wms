var basepath;
$(function(){
	basepath = $("#basePath").attr("basePath");
})
//退出
function quit(){
	var url = basepath+"system/quit.spring";
	$.post(url,{},function(data){
		var jsonobj = JSON.parse(data);
		window.location.href = jsonobj.url;
	});
	stopPropagation();
}

/*-------系统信息设置----------*/
//修改个人信息
function update_personInfo(){
//	var url = "";
//	var args = {};
//	$.post(url,args,function(data){
//		loginer = JSON.parse(data);
//		set_personInfo();
//		alert(loginer.message);
//	});
	close_personInfo();
}
//修改密码
function update_key_submit(){
	if(check_flag()){
		var basepath = $("#basePath").attr("basePath");
  		var url = basepath+"system/updatekey.spring";
  		var orgPwd = $("#new_password").val();
  		RSAUtils.setMaxDigits(200);  
  		var publicKeyExponent = $("#basePath").attr("publicKeyExponent");
  		var publicKeyModulus = $("#basePath").attr("publicKeyModulus");
  		var key = new RSAUtils.getKeyPair(publicKeyExponent, "", publicKeyModulus);  
  		var password = RSAUtils.encryptedString(key,orgPwd.split("").reverse().join(""));  
  		var args = {"password":password};
  		$.post(url,args,function(data){
  			var jsonobj = JSON.parse(data);
  			msg(jsonobj.message);
  			if(jsonobj.state == '1'){
  				close_updateKey();
  			}
  		});
	}
}
//检查密码修改标志
function check_flag(){
	if($("#oldkey_flag").attr("flag") == "0"){
		alert("请输入原始密码！");
		document.getElementById("old_password").focus(); 
		return false;
	}
	if($("#newkey_flag").attr("flag") == "0"){
		alert("密码格式错误！");
		document.getElementById("new_password").focus(); 
		return false;
	}
	if($("#againkey_flag").attr("flag") == "0"){
		alert("两次密码输入不一致！");
		document.getElementById("again_password").focus(); 
		return false;
	}
	return true;
}
//用户信息设置
function set_personInfo(loginer){
	if(loginer != null){
		$("#userName").val(loginer.userName);
		$("#sex").val(loginer.sex);
		$("#userAge").val(loginer.userAge);
		$("#birthday").val(loginer.birthday);
		$("#IDcard").val(loginer.IDcard);
		$("#phone").val(loginer.phone);
		$("#departCode").val(loginer.departCode);
		$("#hireDate").val(loginer.hireDate);
	}
}
//设置只读
function set_readonly(){
	$("input[name='personInfo']").each(function(){
		$(this).attr("readonly","readonly");
		$(this).css("background-color","#ccc");
	});
	$("textarea[name='personInfo']").attr("readonly");
	$("textarea[name='personInfo']").css("background-color","#ccc");
}
//清除只读属性
function remove_readonly(){
	$("input[name='personInfo']").each(function(){
		$(this).removeAttr("readonly");
		$(this).css("background-color","white");
	});
	$("textarea[name='personInfo']").removeAttr("readonly");
	$("textarea[name='personInfo']").css("background-color","white");
}
//获取个人信息
function getpersonInfo(){
	var basepath = $("#basePath").attr("basePath");
	var url = basepath+"system/getpersonInfo.spring";
	$.post(url,{},function(data){
		var jsonobj = JSON.parse(data);
		set_personInfo(jsonobj);
		open_personalInfo();
	});
	
}
/*---------------打开、关闭窗口-----------------*/
//个人信息显示
function open_personalInfo(){
	close_updateKey();
	
	$("#personalInfo-block").css("display","block");
}
//个人信息关闭
function close_personInfo(){
	$("#personalInfo-block").css("display","none");
}
//修改密码打开
function open_updateKey(){
	close_personInfo();
	$("#update-block").css("display","block");
}
//修改密码关闭
function close_updateKey(){
	clear_updateKey();
	$("#update-block").css("display","none");
}
//关闭窗口清除数据
function clear_updateKey(){
	$("input[name='update_key']").each(function(){
		$(this).val("");
	});
	$("img[name='update_key']").each(function(){
		$(this).attr("src","../../static/images/f.png");
		$(this).attr("flag","0");
	});
}