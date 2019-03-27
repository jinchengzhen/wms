var basepath;
$(function(){
	basepath = $("#basePath").attr("basePath");
	$("#all").clcik();
	searchInfo();
});
/*--------------按条件查询-----------------*/
function searchInfo(){
	var userName = $("#search_userName").val();
	var IDcard = $("#search_IDcard").val();
	var args = {"userName":userName,"IDcard":IDcard,"currpage":currpage,"pagesize":pagesize};
	var url = basepath+"select/userlimitpage.spring";
	var old_time = new Date().getTime();
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		var totaldata = jsonobj.totaldata;
		var curr_time = new Date().getTime();
		var htmlstr = "";
		if(totaldata > 0){
			setpageInfo(totaldata,(curr_time - old_time));
			var mag = jsonobj.message;
			for(var i = 0; i < mag.length; i++){
				var bgcolor = "white";
				var userAut = mag[i].userAut;
				if(i%2 == 1){
					bgcolor = "#ebf5d8";
				}
				htmlstr += "<tr class=\"select-tr\" style=\"background-color:"+bgcolor+";\">";
				htmlstr += "<td>"+((currpage-1)*pagesize+i+1)+"</td><td>"+mag[i].userName+"</td><td>"+mag[i].userID+"</td><td>"+mag[i].IDcard+"</td><td>"+mag[i].phone+"</td><td><span class=\"option\" onclick=\"delete_userInfo('"+mag[i].userID+"')\">删除人员</span><span class=\"option\" onclick=\"searchUser('"+mag[i].userID+"')\">查看|编辑</span><span id=\""+mag[i].userID+"\" class=\"option\"  onclick=\"setAutLimit('"+mag[i].userID+"')\">权限设置</span></td>";;
				htmlstr += "</tr>";
			}
		}else{
			alert(jsonobj.message);
		}
		$("#userlistInfo").html(htmlstr);
	});
}
function clearSearchInfo(){
	$("#search_userName").val("");
	$("#search_IDcard").val("");
}
/*------------查询单人信息-------------*/
function searchUser(userID){
	var url = basepath+"select/selectuser.spring";
	var args = {"userID":userID};
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		if(jsonobj.state == "1"){
			var userobj = jsonobj.message;
			if(userobj != null){
				setuserInfo(userobj);
				show_su_pop();
			}
		}else{
			alert(jsonobj.message);
		}
	});
}
//赋值
function setuserInfo(userobj){
	$("#su_userName").val(userobj.userName);
	$("#su_userName").attr("title",userobj.userName);
	$("#su_sex").val(userobj.sex == "1"?"男":"女");
	$("#su_sex").attr("val",userobj.sex);
	$("#su_birthday").val(userobj.birthday);
	$("#su_birthday").attr("title",userobj.birthday);
	$("#su_userAge").val(userobj.userAge);
	$("#su_userAge").attr("title",userobj.userAge);
	$("#su_phone").val(userobj.phone);
	$("#su_phone").attr("title",userobj.phone);
	$("#su_IDcard").val(userobj.IDcard);
	$("#su_IDcard").attr("title",userobj.IDcard);
	$("#su_userID").val(userobj.userID);
	$("#su_userID").attr("title",userobj.userID);
	$("#su_hireDate").val(userobj.hireDate);
	$("#su_hireDate").attr("title",userobj.hireDate);
}
//取值
function getuserInfo(){
	var userID = $("#su_userID").val();
	var userName = $("#su_userName").val();
	var sex = $("#su_sex").attr("val");
	var birthday = $("#su_birthday").val();
	var IDcard = $("#su_IDcard").val();
	var phone = $("#su_phone").val();
	var userAge = $("#su_userAge").val();
	var hireDate = $("#su_hireDate").val();
	var args = {"userID":userID,"userName":userName,
			"sex":sex,"birthday":birthday,"IDcard":IDcard,
			"phone":phone,"userAge":userAge,
			"hireDate":hireDate};
	return args;
}
/*------------修改单人信息-------------*/
function update_userInfo(){
	var args = getuserInfo();
	var url = basepath+"infoMag/updateUser.spring";
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		alert(jsonobj.message);
		if(jsonobj.state == "1"){
			close_su_pop();
		}
	})
}



/*-------------新增处理--------------------*/
/*------正则匹配表达式----*/
var chinesechar = /[\u4e00-\u9fa5]+/;//匹配中文字符
var regphone =  /^((0\d{2,3}-\d{7,8})|(1[3584]\d{9}))$/;//匹配电话
var regemail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;//验证邮箱
//输入检验提交
function deal_add(){
	var flag = true;
	var userName = $("#add_userName").val();
	if(!chinesechar.test($.trim(userName))){		flag = false;	}//姓名匹配中文字符
	var IDcard = $("#add_IDcard").val();
	if(!IdentityCodeValid($.trim(IDcard))){		flag = false;	}//身份证匹配验证
	var phone = $("#add_phone").val();
	if(!regphone.test($.trim(phone))){		flag = false;	}//电话验证
	var duty = $("#add_duty").val();
	if(flag){
		var flaginfo = $.md5(IDcard+phone);
		var args = {"userName":userName,"IDcard":IDcard,"phone":phone,"flaginfo":flaginfo};
		var url = basepath+"insert/user.spring";
		$.post(url,args,function(data){
			var jsonobj = JSON.parse(data);
			alert(jsonobj.message);
			if(jsonobj.state == "1"){
				close_add_pop();
				searchInfo();
			}
		});
	}else{
		alert("新增信息有误！");
	}
}
/*--------------审批--------------*/
function deal_approve(){
	
}


/*------------设置状态------------*/
function select_status(obj){
	$(".select-opntion").each(function(){
		$(this).css("background-color","cornflowerblue");
		$(this).css("color","black");
	})
	obj.css("background-color","#5757d8")
	obj.css("color","lightgoldenrodyellow")
}


/*------------导出xls------------*/
function exportXls(){
	var userName = $("#search_userName").val();
	var IDcard = $("#search_IDcard").val();
	var args = {"userName":userName,"IDcard":IDcard};
	var url = basepath+"export/userXls.spring";
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		window.location.href = jsonobj.url;
	});
}
/*------------窗口打开与关闭------------*/
//打开新增弹窗
function show_add_pop(){
	$("#black_block").css("display","block");
	$("#add_apply_block").css("display","block");
}
//关闭新增弹窗
function close_add_pop(){
	$("#add_apply_block").css("display","none");
	$("#black_block").css("display","none");
}
//审批弹窗
function show_approve_pop(){
	$("#black_block").css("display","block");
	$("#approve_block").css("display","block");
}
function close_approve_pop(){
	$("#approve_block").css("display","none");
	$("#black_block").css("display","none");
}
//清除弹窗数据
function clear_popinfo(){
}
//查看、修改弹窗显示
function show_su_pop(){
	$("#black_block").css("display","block");
	$("#su_block").css("display","block");
}
//查看、修改弹窗关闭
function close_su_pop(){
	$("#su_block").css("display","none");
	clear_popinfo();
	$("#black_block").css("display","none");
}


/* ----------------分页------------------ */
var currpage = 1;
var pagesize = 10;
var totalpage = 0;
function pageturn(option){
	if(option == "first"){
		currpage = 1;
	}else if(option == "prev"){
		if(currpage > 1){
			currpage--;
		}else{
			alert("当前为第一页");
		}
	}else if(option == "next"){
		if(currpage < totalpage){
			currpage++;
		}else{
			alert("当前为最后一页");
		}
	}else if(option == "last"){
		currpage = totalpage;
	}
	searchInfo();
}

function setpageInfo(totaldata,spendtime){
	$("#currpage").text(currpage);
	totalpage = Math.ceil(totaldata/pagesize);
	$("#totalpage").text(totalpage);
	$("#select_time").text(spendtime/1000);
}
