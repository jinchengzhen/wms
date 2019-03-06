
var storeID;
var basepath;
$(function(){
	basepath = getBathpath();
});

//init
function initSelect(){
	var url = basepath+"init/inventoryData.spring";
	var data1 = null;
	var data2 = null;
	$.post(url,{},function(data){
		var jsonobj = JSON.parse(data);
		data1 = jsonobj.goodsName;
		data2 = jsonobj.goodsVersion;
		$("#add_goodsName").select2({
			  data: data1,
			  placeholder:'请选择货品名称',
			  width: '48%',
			  allowClear:true
		});
		$("#add_goodsVersion").select2({
			  data: data2,
			  placeholder:'请选择货品型号',
			  width: '48%',
			  allowClear:true
		});
	});
}

//查询库存
function searchInfo(){
	storeID = $("#current_store").val();
//	var goodsID = $("#search_goodsName").val();
//	var batchID = $("#search_batchID").val();
	var args = {"storeID":storeID,/*"goodsID":goodsID,"batchID":batchID,*/"currpage":currpage,"pagesize":pagesize};
	var url = basepath+"select/inventorylimitpage.spring";
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
				htmlstr += "<tr class=\"select-tr\"><td>"+((currpage-1)*pagesize+i+1)+"</td><td>"+mag[i].goodsID+"</td><td>"+mag[i].goodsName+"</td><td>"+mag[i].goodsVersion+"</td><td>"+mag[i].typeName+"</td><td>"+mag[i].goodsSource+"</td><td>"+mag[i].userID+"</td><td><span class=\"option\" onclick=\"delete_goodsInfo('"+mag[i].goodsID+"')\">删除</span><span class=\"option\" onclick=\"find_goodsInfo('"+mag[i].goodsID+"')\">查看</span><span class=\"option\" onclick=\"update_goodsInfo('"+mag[i].goodsID+"')\">编辑</span></td>	</tr>";
			}
		}else{
			msg(jsonobj.message);
		}
		$("#inventorylistInfo").html(htmlstr);
	});
}


//库存信息关闭
function inventMag(){
	$("#inventoryInfo",window.parent.document).css("display","none");
}

//库存新增
function deal_add(){
	var storeID = $("#current_store").val();
	var remark = $("#add_remark").val();
	var position = $("#add_storelocation").val();
	var goodsID = $("#add_goodsName").val();
	var goodsVersion = $("#add_goodsVersion").val();
	var batchID = $("#add_batchID").val();//后台检查是否为空，为空则自动生成
	var total = $("#add_total").val();//数量
	var args = {"storeID":storeID,"position":position,"goodsID":goodsID,
			"goodsVersion":goodsVersion,"batchID":batchID,"total":total,"remark":remark};
	var array = new Array();
	array.push(storeID);
	array.push(storelocation);
	array.push(goodsName);
	array.push(goodsVersion);
	array.push(total);
	var url = basepath+"insert/inventory.spring";
	if(checkInventoryInfo(array)){
		$.post(url,args,function(data){
			var jsonobj = JSON.parse(data);
			alert(jsonobj.message);
			if(jsonobj.state == 1){
				searchInfo();
			}
		});
	}
	
}
//校验
function checkInventoryInfo(array){
	var flag = true;
	for(var i = 0;i < array.length;i++){
		if(array[i] == ""){
			flag = false;
			break;
		}
	}
	return flag;
}
function clearADDInfo(){
	
}
/*------------窗口打开与关闭------------*/
//打开新增弹窗
function show_add_pop(){
	$("#black_block").css("display","block");
	initSelect();
	$("#add_inventoryinfo_block").css("display","block");
}
//关闭新增弹窗
function close_add_pop(){
	$("#add_inventoryinfo_block").css("display","none");
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