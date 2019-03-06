//document.write("<script language=javascript src='../../../component/select2-4.0.1-rc.1/dist/js/select2.full.min.js'></script>");
var basepath;
$(function(){
	basepath = getBathpath();
	initData();
	searchInfo();
});
var selectdata_user = null ;//选择框数据-用户名-ID
function initData(){
	var url = basepath+"init/storeData.spring";
	$.post(url,{},function(data){
		var jsonobj = JSON.parse(data);
		data_user = jsonobj.user;
		$("#add_managerID").select2({
		  data: data_user,
		  width:"67.5%",
		  placeholder:'请选择管理人员',
		  allowClear:true
		});
		$("#search_managerID").select2({
			data: data_user,
			width:"52%",
			placeholder:'请选择管理人员',
			allowClear:true
		});
	});
}


//查询货品信息（列表）
function searchInfo(){
	var storeID = $("#search_storeID").val();
	var managerID = $("#search_managerID").val();
	var args = {"storeID":storeID,"managerID":managerID,"currpage":currpage,"pagesize":pagesize};
	var url = basepath+"select/liststore.spring";
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
				var magstr = JSON.stringify(mag[i]);
				htmlstr += "<tr class=\"select-tr\"><td>"+((currpage-1)*pagesize+i+1)+"</td><td>"+mag[i].storeID+"号库</td><td>"+mag[i].storelocation+"</td><td>"+mag[i].storeSize+"</td><td>"+mag[i].positionNum+"</td><td>"+mag[i].managerID+"</td><td><span class=\"option\" onclick=\"delete_storeInfo('"+mag[i].storeID+"')\">删除仓库</span><span class=\"option\" onclick='setUpdateInfo("+magstr+") '>修改信息</span><span class=\"option\" onclick=\"inventMag('"+mag[i].storeID+"')\">库存管理</span></td>	</tr>";
			}
		}else{
			msg(jsonobj.message);
		}
		$("#storelistInfo").html(htmlstr);
	});
}
//重置查询
function clearSearchInfo(){
	$("#search_storeID").val("");
	$("#search_managerID").val("");
	$("#search_managerID").select2({
		data: selectdata_user,
		width:"52%",
		placeholder:'请选择管理人员',
		allowClear:true
	});
}
//库存信息显示
function inventMag(storeID){
	$("#inventoryInfo").css("display","block");
	$("#inventoryInfo").contents().find("#current_store").val(storeID+"号库");
	$("#inventoryInfo").contents().find("#select_func").click()
	
}



var regSize =  /^[0-9xX]+$/;
//新增库房信息
function deal_add(){
	var storeID = $("#add_storeID").val();
	var storelocation = $("#add_storelocation").val();
	var storeSize = $("#add_storeSize").val();
	var positionNum = $("#add_positionNum").val();
	var managerID = $("#add_managerID").val();
	var remark = $("#add_remark").val();
	var array = new Array();
	array.push(storeID);
	array.push(storelocation);
	var flag = regSize.test($.trim(storeSize));
	if(!regSize.test($.trim(storeSize))){
		storeSize = "";
	}
	array.push(storeSize);
	if(positionNum == ""){
		positionNum = "1";
	}
	array.push(positionNum);
	array.push(managerID);
	if(checkStoreInfo(array)){
		var args = {"storeID":storeID,"storelocation":storelocation,"storeSize":storeSize,
				"positionNum":positionNum,"managerID":managerID,"remark":remark};
		var url = basepath+"insert/store.spring";
		$.post(url,args,function(data){
			var jsonobj = JSON.parse(data);
			msg(jsonobj.message);
			if(jsonobj.state == 1){
				searchInfo();
			}
		});
	}else{
		msg("信息填写有误！");
	}
}
//清除新增信息
function clearADDInfo(){
	$("#add_storeID").val("");
	$("#add_storelocation").val("");
	$("#add_storeSize").val("");
	$("#add_positionNum").val("");
	$("#add_managerID").val("");
	$("#add_managerID").select2({ data: selectdata_user,  width:"67.5%",  placeholder:'请选择管理人员',  allowClear:true	});
	$("#add_remark").val("");
}
function JsonArray2String(array,splitflag){
	var str = "";
	for(var i = 0;i < array.length;i++){
		str += JSON.stringify(array[i])+splitflag;
	}
	return str;
}
function String2JsonArray(str,splitflag){
	var array = str.split(splitflag);
	var result = new Array();
	for(var i = 0;i < array.length;i++){
		if(array[i] != ""){
			var jsonobj = JSON.parse(array[i]);
			result.push(jsonobj);
		}
	}
	return result;
}
//删除库房
function delete_storeInfo(storeID){
	var url = basepath+"delete/store.spring";
	var args = {"storeID":storeID};
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		msg(jsonobj.message);
		if(jsonobj.state == '1'){
			searchInfo();
		}
	});
}
function checkStoreInfo(array){
	var flag = true;
	for(var i = 0;i < array.length;i++){
		if(array[i] == ""){
			flag = false;
			break;
		}
	}
	return flag;
}
//修改信息
function deal_update(){
	var storeID = $("#update_storeID").val();
	var storelocation = $("#update_storelocation").val();
	var storeSize = $("#update_storeSize").val();
	var positionNum = $("#update_positionNum").val();
	var managerID = $("#update_managerID").val();
	var remark = $("#update_remark").val();
	var array = new Array();
	array.push(storeID);
	array.push(storelocation);
	array.push(storeSize);
	array.push(positionNum);
	array.push(managerID);
	if(checkStoreInfo(array)){
		var args = {"storeID":storeID,"storelocation":storelocation,"storeSize":storeSize,
				"positionNum":positionNum,"managerID":managerID,"remark":remark};
		var url = basepath+"update/store.spring";
		$.post(url,args,function(data){
			var jsonobj = JSON.parse(data);
			alert(jsonobj.message);
			if(jsonobj.state == 1){
				searchInfo();
			}
		});
	}else{
		alert("信息填写不完整！")
	}
}
function setUpdateInfo(StoreObj){
	$("#update_storeID").val(StoreObj.storeID);
	$("#update_storelocation").val(StoreObj.storelocation);
	$("#update_storeSize").val(StoreObj.storeSize);
	$("#update_positionNum").val(StoreObj.positionNum);
	$("#update_managerID").select2({
	  data: selectdata_user,
	  width:"67.5%",
	  placeholder:'请选择管理人员',
	  allowClear:true
	});
	$("#update_remark").val(StoreObj.remark);
	show_update_pop();
}
//清除修改弹窗
function clearUpdateInfo(){
	$("#update_storeID").val("");
	$("#update_storelocation").val("");
	$("#update_storeSize").val("");
	$("#update_positionNum").val("");
	$("#update_managerID").val("");
	$("#update_managerID").select2({ data: selectdata_user,  width:"67.5%",  placeholder:'请选择管理人员',  allowClear:true	});
	$("#update_remark").val("");
}
/*------------窗口打开与关闭------------*/
//打开新增弹窗
function show_add_pop(){
	$("#black_block").css("display","block");
	$("#add_storeinfo_block").css("display","block");
}
//关闭新增弹窗
function close_add_pop(){
	$("#add_storeinfo_block").css("display","none");
	$("#black_block").css("display","none");
}
//打开修改弹窗
function show_update_pop(){
	$("#black_block").css("display","block");
	$("#update_storeinfo_block").css("display","block");
}
//关闭修改弹窗
function close_update_pop(){
	$("#update_storeinfo_block").css("display","none");
	clearUpdateInfo();
	$("#black_block").css("display","none");
}
//清除弹窗数据
function clear_popinfo(){
	
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