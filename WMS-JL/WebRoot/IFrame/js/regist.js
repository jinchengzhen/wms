
//初始化

var basepath = $("#basePath").attr("basePath");

$(function(){
	init_regist();
	addressInit("location_province","location_city","location_area");
});
function close_regist(){
	window.location.href = "login.jsp";
}
function init_regist(){
	
	var url = basepath+"system/selectInfo.spring";
	$.post(url,{"login_flag":"login_flag"},function(data){
		var jsonobj = JSON.parse(data);
		var properties_data = jsonobj.unitProperties;
		var unitType_data = jsonobj.unitType;
		$("#unitProperties").select2({
				 data: properties_data,
				 width:"100%",
				  placeholder:"请选择单位性质",
				  allowClear:false	
		});
		$("#unitType").select2({
				 data: unitType_data,
				 width:"100%",
				  placeholder:"请选择单位类别",
				  allowClear:false	
		});
	});
	$("input[name='registInfo']").each(function(){
		$(this).val("");
	}); 
	$("img[name='registInfo']").each(function(){
		$(this).attr("src","../../static/images/f.png");
		$(this).attr("flag","0");
	});
	$("#registEmail").attr("src","");
}
	
	
//注册提交
function regist_submit(){
	if(check_regist_submit()){
		var url = basepath+"system/registDeal.spring";
		var register = $("#register").val();
		var registerIDcard = $("#registerIDcard").val();
		var registerPhone = $("#registerPhone").val();
		var registEmail = $("#registEmail").val();
		var password = $("#password").val();
		var unitProperties = $("#unitProperties").val();
		var unitType = $("#unitType").val();
		var unitName = $("#unitName").val();
		var locationArray = getAddress();
		var location = locationArray[0]+locationArray[1]+locationArray[2];
		var address = $("#address").val();
		var flaginfo = $.md5(password+unitType+registerIDcard);
		var args = {"register":register,"registerIDcard":registerIDcard,
				"registerPhone":registerPhone,"registEmail":registEmail,"password":password,
				"unitProperties":unitProperties,"unitType":unitType,"unitName":unitName,
				"location":location,"address":address,"flaginfo":flaginfo};
		$.post(url,args,function(data){
			var jsonobj = JSON.parse(data);
			if(jsonobj.state == 1){
				if(confirm("注册成功！\r您的登录帐号为："+jsonobj.userID+"\r是否立即登录？")){
					$("#userID").val(jsonobj.userID);
					$("#password").val(password);
					login();
				}else{
					location.href = "login.jsp";
				}
			}else{
				alert(jsonobj.message);
			}
		});
	}
}
//注册提交校验
function check_regist_submit(){
	var flag = true;
	var i = 1;
	$("img[name='registInfo']").each(function(){
//		console.log((i++) +" : "+$(this).attr("flag"));
		if($(this).attr("flag") == "0"){
			alert("注册信息有误！");
			flag = false;
			return false;
		}
	});
	return flag;
}
/*------正则匹配表达式----*/
var chinesechar = /[\u4e00-\u9fa5]+/;//匹配中文字符
var patrn=/[\u4e00-\u9fa5]+/;//判断是否存在非法字符
var regphone =  /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;//匹配电话
var regpassword = /[a-zA-Z0-9_]{6,20}$/;//匹配密码
var regemail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;//验证邮箱
/*-------注册信息检查----*/
//姓名
function check_registName(val){
	var flag = chinesechar.test($.trim(val));
	regist_util("register_flag",flag);
}
//身份证号码
function check_registerIDcard(val){
	var flag = IdentityCodeValid($.trim(val));
	regist_util("registerIDcard_flag",flag);
}
//电话
function check_registerPhone(val){
	var flag = regphone.test($.trim(val));
	regist_util("registerPhone_flag",flag);
}
//密码
function check_password(val){
	var flag = regpassword.test($.trim(val));
	regist_util("password_flag",flag);
}
//邮箱
function check_registEmail(val){
	var flag = regemail.test($.trim(val));
	regist_util("registEmail_flag",flag);
}
//单位名称
function check_unitName(val){
	var flag = patrn.test($.trim(val));
	regist_util("unitName_flag",flag);
}
//select验证
function check_select_1(val){
	var flag = (val != null && val != "");
	regist_util("unitProperties_flag",flag);
}
function check_select_2(val){
	var flag = (val != null && val != "");
	regist_util("unitType_flag",flag);
}
//单位地址
function check_locationANDaddress(val){
	var array = getAddress();
	var flag0 = (array[0] != null && array[0] != "");//省
	var flag1 = (array[1] != null && array[1] != "");//市
	var flag2 = (array[2] != null && array[2] != "");//区
	var flag3 = false;
	if(flag0 && flag1 && flag2){
		flag3 = patrn.test($.trim(val));
	}
	regist_util("location_flag",flag3);
}
function regist_util(idstr,flag){
	if(flag){
		$("#"+idstr).attr("src","../../static/images/t.png");
		$("#"+idstr).attr("flag","1");
	}else{
		$("#"+idstr).attr("src","../../static/images/f.png");
		$("#"+idstr).attr("flag","0");
	}
}
/*-------注册信息检查结束----*/


