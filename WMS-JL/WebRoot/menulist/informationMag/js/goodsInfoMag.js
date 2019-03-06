var basepath;
$(function(){
	basepath = getBathpath();
	initData();
	searchInfo();
});
function initData(){
	$.fn.select2.defaults.set('width', '70%');
	var data1 = null/* [{ id: 0, text: '常温' }, { id: 1, text: '冷藏' }, { id: 2, text: '冷冻' },] */;
	var data2 = null/* [{ id: 0, text: '供货商' }, { id: 1, text: '生产经营' },] */;
	var data3 = new Array();
	var data4 = null;
	for(var i=0;i<1000;i++){
		data3.push({id:(i+1),text:""+(i+1)+"天"});
	}
	for(var i=0;i<36;i++){
		data3.push({id:1000+(i+1),text:""+(i+1)+"月"});
	}
	for(var i=0;i<100;i++){
		data3.push({id:2000+(i+1),text:""+(i+1)+"年"});
	}
	var url = basepath+"init/goodsData.spring";
	$.post(url,{},function(data){
		var jsonobj = JSON.parse(data);
		data1 = jsonobj.goodsSave;
		data2 = jsonobj.goodsSource;
		data4 = jsonobj.goodsType;
		$("#search_typeName").select2({
		  data: data4,
		  placeholder:'请选择货品类别',
		  width: '60%',
		  allowClear:true
		});
		$("#add_typeName").select2({
			  data: data4,
			  placeholder:'请选择货品类别',
			  allowClear:true
			});
		$("#add_storage").select2({
		  data: data1,
		  placeholder:'请选择存储条件',
		  allowClear:true
		});
		$("#add_goodsSource").select2({
		  data: data2,
		  placeholder:'请选择货源',
		  allowClear:true
		});
		$("#search_goodsSource").select2({
		  data: data2,
		  placeholder:'请选择货源',
		  width: '60%',
		  allowClear:true
		});
		$("#add_limitTerm").select2({
		  data: data3,
		  placeholder:'请选择有效期限',
		  allowClear:true
		});
	});
}

//查询货品信息（列表）
function searchInfo(){
	var goodsName = $("#search_goodsName").val();
	var typeName = $("#search_typeName").val();
	var goodsSource = $("#search_goodsSource").val();
	var args = {"goodsID":goodsName,"typeID":typeName,"goodsSource":goodsSource,"currpage":currpage,"pagesize":pagesize};
	var url = basepath+"select/goodslimitpage.spring";
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
		$("#goodslistInfo").html(htmlstr);
	});
}
//查询单品信息
function find_goodsInfo(goodsID){
	var url = basepath+"select/goodsdetail.spring";
	var args = {"goodsID":goodsID};
	$.post(url,args,function(data){
		var jsonobj0 = JSON.parse(data);
		if(jsonobj0.state == '1'){
			setdetailInfo(jsonobj0.message);
			show_su_pop();
		}else{
			msg(jsonobj0.message);
		}
	});
}
//单品信息赋值
function setdetailInfo(jsonobj){
	$("#su_goodsName").val(jsonobj.goodsName);
	$("#su_goodsID").val(jsonobj.goodsID);
	$("#su_simpleName").val(jsonobj.simpleName);
	$("#su_typeName").val(jsonobj.typeName);
	$("#su_goodsVersion").val(jsonobj.goodsVersion);
	$("#su_TP").val(jsonobj.TP);
	$("#su_storage").val(jsonobj.storage);
	$("#su_sizeMax").val(jsonobj.sizeMax);
	$("#su_sizeMin").val(jsonobj.sizeMin);
	$("#su_goodsSource").val(jsonobj.goodsSource);
	$("#su_quality").val(jsonobj.quality);
	$("#su_limitTerm").val(jsonobj.limitTerm);
	$("#su_userID").val(jsonobj.userID);
	$("#su_updateDate").val(jsonobj.updateDate);
	$("#su_remark").val(jsonobj.remark);
	
	$("#su_goodsName").attr("readonly","readonly");
	$("#su_goodsID").attr("readonly","readonly");
	$("#su_simpleName").attr("readonly","readonly");
	$("#su_typeName").attr("readonly","readonly");
	$("#su_goodsVersion").attr("readonly","readonly");
	$("#su_TP").attr("readonly","readonly");
	$("#su_storage").attr("readonly","readonly");
	$("#su_sizeMax").attr("readonly","readonly");
	$("#su_sizeMin").attr("readonly","readonly");
	$("#su_goodsSource").attr("readonly","readonly");
	$("#su_quality").attr("readonly","readonly");
	$("#su_userID").attr("readonly","readonly");
	$("#su_updateDate").attr("readonly","readonly");
	$("#su_remark").attr("readonly","readonly");
}
//单品信息清空
function clearDetailInfo(){
	$("#su_goodsName").val("");
	$("#su_goodsID").val("");
	$("#su_simpleName").val("");
	$("#su_typeName").val("");
	$("#su_goodsVersion").val("");
	$("#su_TP").val("");
	$("#su_storage").val("");
	$("#su_sizeMax").val("");
	$("#su_sizeMin").val("");
	$("#su_goodsSource").val("");
	$("#su_quality").val("");
	$("#su_limitTerm").val("");
	$("#su_userID").val("");
	$("#su_updateDate").val("");
	$("#su_remark").val("");
}
//重置查询
function clearSearchInfo(){
	$("#search_goodsName").val("");
//	$("#search_typeName").select2("val","");
//	$("#search_goodsSource").val("");
}
//新增货品处理
function deal_add(){
	var goodsName = $("#add_goodsName").val();
	var simpleName = $("#add_simpleName").val();
	var typeName = $("#add_typeName").val();
	var baseUnit = $("#add_baseUnit").val();
	var sizeMax = $("#add_sizeMax").val();
	var sizeMin = $("#add_sizeMin").val();
	var TP = $("#add_TP").attr("tpvalue");
	var quality = $("#add_quality").val();
	var goodsSource = $("#add_goodsSource").val();
	var goodsVersion = $("#add_goodsVersion").val();
	var storage = $("#add_storage").val();
	var limitTerm = $("#add_limitTerm").val();
	var remark = $("#add_remark").val();
	var args = {"goodsName":goodsName,"simpleName":simpleName,"typeName":typeName,
			"baseUnit":baseUnit,"sizeMax":sizeMax,"sizeMin":sizeMin,
			"TP":TP,"quality":quality,"goodsSource":goodsSource,
			"goodsVersion":goodsVersion,"storage":storage,"limitTerm":limitTerm,"remark":remark}
	var url = basepath+"insert/goodsInfo.spring";
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		msg(jsonobj.message);
		if(jsonobj.state == 1){
			close_add_pop();
			searchInfo();
		}
	});
}
//清除新增信息
function clearADDInfo(){
	$("#add_goodsName").val("");
	$("#add_simpleName").val("");
	$("#add_typeName").val("");
	$("#add_baseUnit").val("");
	$("#add_sizeMax").val("");
	$("#add_sizeMin").val("");
	$("#add_TP").val("");
	$("#add_TP").attr("tpvalue","");
	$("#add_quality").val("");
	$("#add_goodsSource").val("");
	$("#add_goodsVersion").val("");
	$("#add_storage").val("");
	$("#add_limitTerm").val("");
	$("#add_remark").val("");
}
/*--------------规格设置-------------*/
//新增
function insert_TPproperty(){
	var num = $(".tp-tr").length;
	if(num < 10){
		var htmlstr = "<div class=\"tp-tr\" id='tp"+new Date().getTime()+"'>	<div class=\"tp-th1\"><input type=\"text\"></div><div class=\"tp-th1\"><input type=\"text\"></div><div class=\"tp-th2\"><input type=\"text\"></div><div class=\"tp-th3\"><img src=\"../../../static/images/delete.png\" title=\"删除属性\"  onclick=\"delete_TPproperty('tp"+new Date().getTime()+"')\"></div>		</div>";
		$("#tp_table").append(htmlstr);
		if(num == 9){
			//隐藏新增div
			$("#tp_insert").css("display","none");
		}
	}else{
		msg("最多添加10个属性！");
	}
	
}
//确认
function setTP(){
	var array = new Array();
	var showval = "";
	$(".tp-tr").each(function(){
		var properties = $(this).find("input");
		var name = properties.eq(0).val();
		var data = properties.eq(1).val();
		var unit = properties.eq(2).val();
		if(name != undefined && name != null && name != ""
			&& data != undefined && data != null && data != ""	){
			if(unit == null){
				unit = "";
			}
			array.push({"name":name,"data":data,"unit":unit});
			showval += name+":"+data+" "+unit+";";
		}
	});
	var TPvalue = JsonArray2String(array,'-');
	$("#add_TP").val(showval);
	$("#add_TP").attr("title",showval);
	$("#add_TP").attr("tpvalue",TPvalue);
	close_setTP();
}
//删除
function delete_TPproperty(id){
	var num = $(".tp-tr").length;
	if(num == 10){
		$("#tp_insert").css("display","block");
	}
	if(num > 2){
		$("#"+id).remove();
	}else{
		msg("至少设置一个属性！");
	}
}
//辅助
function setBaseUnit(value){
	$('input[name="baseunit"]').each(function(){
		$(this).val(value);
	});
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
//删除货品信息
function delete_goodsInfo(goodsID){
	var url = basepath+"delete/goods.spring";
	var args = {"goodsID":goodsID};
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		msg(jsonobj.message);
		if(jsonobj.state == '1'){
			searchInfo();
		}
	});
}

/*------------导出xls------------*/
function exportXls(){
	var goodsName = $("#search_goodsName").val();
	var typeName = $("#search_typeName").val();
	var goodsSource = $("#search_goodsSource").val();
	var args = {"goodsID":goodsName,"typeID":typeName,"goodsSource":goodsSource};
	var url = basepath+"export/goodsXls.spring";
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		window.location.href = jsonobj.url;
	});
}
/*------------窗口打开与关闭------------*/
//打开规格设置
function show_setTP(){
	var property = $("#add_TP").attr("tpvalue");
	var htmlstr = "<div class=\"tp-tr\"><div class=\"tp-th1 fontB\">属性</div><div class=\"tp-th1 fontB\">数值</div><div class=\"tp-th2 fontB\">单位</div><div class=\"tp-th3 fontB\">操作</div>	</div>	";
	if(property != null && property != undefined && property != ""){
		var array = String2JsonArray(property,'-');
		for(var i = 0;i < array.length;i++){
			htmlstr += "<div class=\"tp-tr\" id=\"tp_tr1\">	<div class=\"tp-th1\"><input type=\"text\" value=\""+array[i].name+"\"></div><div class=\"tp-th1\"><input type=\"text\" value=\""+array[i].data+"\"></div><div class=\"tp-th2\"><input type=\"text\" value=\""+array[i].unit+"\"></div><div class=\"tp-th3\"><img src=\"../../../static/images/delete.png\" title=\"删除属性\"  onclick=\"delete_TPproperty('tp_tr1')\"></div>		</div>";
		}
	}else{
		htmlstr += "<div class=\"tp-tr\" id=\"tp_tr1\">	<div class=\"tp-th1\"><input type=\"text\"></div><div class=\"tp-th1\"><input type=\"text\"></div><div class=\"tp-th2\"><input type=\"text\"></div><div class=\"tp-th3\"><img src=\"../../../static/images/delete.png\" title=\"删除属性\"  onclick=\"delete_TPproperty('tp_tr1')\"></div>		</div>";
	}
	$("#tp_table").html(htmlstr);
	$("#black_block").css("z-index","1002");
	$("#TP_block").css("z-index","1003");
	$("#TP_block").css("display","block");
}
//关闭规格设置
function close_setTP(){
	$("#black_block").css("z-index","1000");
	$("#TP_block").css("z-index","1001");
	$("#TP_block").css("display","none");
}
//打开新增弹窗
function show_add_pop(){
	$("#black_block").css("display","block");
	$("#add_goodsinfo_block").css("display","block");
}
//关闭新增弹窗
function close_add_pop(){
	$("#add_goodsinfo_block").css("display","none");
	clear_popinfo('add');
	$("#black_block").css("display","none");
}
//清除弹窗数据
function clear_popinfo(str){
	if(str == 'su'){
		clearDetailInfo();
	}else if(str == 'add'){
		clearADDInfo();
	}
}
//查看、修改弹窗显示
function show_su_pop(){
	$("#black_block").css("display","block");
	$("#su_block").css("display","block");
}
//查看、修改弹窗关闭
function close_su_pop(){
	$("#su_block").css("display","none");
	clear_popinfo('su');
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