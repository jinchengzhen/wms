var basepath;
var applyType = $("#title").attr("val");
$(function(){
	basepath = getBathpath();
	select_status($("#all"));
	if(applyType == "purcharse"){
		$("#model_download").attr("href","../../../static/fileModel/supplyModel.xls");
	}else if(applyType == "sale"){
		$("#model_download").attr("href","../../../static/fileModel/saleModel.xls");
	}
});
/*--------------按条件查询-----------------*/
function searchInfo(){
	var apply = $("#search_apply").val();
	var state = currstate;
	var url = basepath+"select/applylimitpage.spring";
	var args = {"apply":apply,"state":state,"applyType":applyType,"currpage":currpage,"pagesize":pagesize};
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
				if(mag[i].state == "0"){
					htmlstr += "<tr class=\"select-tr\"><td>"+((currpage-1)*pagesize+i+1)+"</td><td>"+mag[i].applyID+"</td><td>"+mag[i].applydate+"</td><td>"+"未审批"+"</td><td>"+mag[i].approveID+"</td><td>"+mag[i].approvedate+"</td><td><span class=\"option\" onclick=\"dowmload('"+mag[i].applyfile+"')\"></a>"+mag[i].applyfile.substring(mag[i].applyfile.lastIndexOf('/')+1)+"</td><td><span class=\"option\" onclick=\"approve_pop('"+mag[i].applyNumber+"')\">审批</span></td>	</tr>";
				}else if(mag[i].state == "1"){
					htmlstr += "<tr class=\"select-tr\"><td>"+((currpage-1)*pagesize+i+1)+"</td><td>"+mag[i].applyID+"</td><td>"+mag[i].applydate+"</td><td class='font-color-pass'>"+"审批通过"+"</td><td>"+mag[i].approveID+"</td><td>"+mag[i].approvedate+"</td><td><span class=\"option\" onclick=\"dowmload('"+mag[i].applyfile+"')\"></a>"+mag[i].applyfile.substring(mag[i].applyfile.lastIndexOf('/')+1)+"</td><td><span title='"+mag[i].context+"'>备注</span></td>	</tr>";
				}else if(mag[i].state == "no"){
					htmlstr += "<tr class=\"select-tr\"><td>"+((currpage-1)*pagesize+i+1)+"</td><td>"+mag[i].applyID+"</td><td>"+mag[i].applydate+"</td><td class='font-color-failed'>"+"审批未通过"+"</td><td>"+mag[i].approveID+"</td><td>"+mag[i].approvedate+"</td><td><span class=\"option\" onclick=\"dowmload('"+mag[i].applyfile+"')\"></a>"+mag[i].applyfile.substring(mag[i].applyfile.lastIndexOf('/')+1)+"</td><td><span title='"+mag[i].context+"'>备注</span></td>	</tr>";
				}
			}
		}else{
			msg(jsonobj.message);
		}
		$("#goodslistInfo").html(htmlstr);
		
	})
}
/*--------------下载申请文件--------------*/
function dowmload(furl){
	location.href = furl;
}

/*--------------新增申请--------------*/
function deal_add(){
	if($('#fileShowName').val() != ""){
		upfile();
		if(file_url != ""){
			var url = basepath+"insert/apply.spring";
			var args = {"applyfile":file_url,"applyType":applyType};
			$.post(url,args,function(data){
				var jsonobj = JSON.parse(data);
				msg(jsonobj.message);
			});
		}
	}
}
/*--------------审批--------------*/
function deal_approve(applyNumber){
	var url = basepath+"update/approve.spring";
	var result = $("input[name = 'approve_result']:checked").val();
	var remark = $("#add_remark").val();
	var args = {"applyNumber":applyNumber,"result":result,"remark":remark};
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		msg(jsonobj.message);
		if(jsonobj.state == '1'){
			searchInfo();
			close_approve_pop();
		}
	});
}

function approve_pop(applyNumber){
	show_approve_pop();
	$("#ensure_approve").attr("onclick","deal_approve('"+applyNumber+"')");
}

/*------------上传文件------------*/
//上传文件
var file_url="";
uploading=false;	
function upfile(){
	if(checkFileStyle()){
		
		if(uploading){
			msg("文件正在上传中，请稍候");
			return false;
		}
		$.ajax({
			url: basepath+"export/upload.spring",
			type: 'POST',
			async: true,
			cache: false,
			data: new FormData($('#subfile')[0]),
			processData: false,
			contentType: false,
			dataType:"json",
			beforeSend: function(){
				uploading = true;
			},
			success : function(data) {
				file_url=data.furl;
				if(file_url == ""){
					alert(data.message);
				}
				uploading = false;
			}
		});
	}else{
		msg("文件格式错误，请选择.xls文件");
	}
}
//检查文件格式
function checkFileStyle(){
	var filename = $('#fileShowName').val();
	var l = filename.lastIndexOf("\\")+1;
	filename = filename.substring(l);
	if(filename.length > 4 && filename.indexOf(".xls")>0){
		return true;
	}
	return false;
}

var currstate ;
/*------------设置状态------------*/
function select_status(obj){
	$(".select-opntion").each(function(){
		$(this).css("background-color","cornflowerblue");
		$(this).css("color","black");
	})
	obj.css("background-color","#5757d8");
	obj.css("color","lightgoldenrodyellow");
	currstate = obj.attr("val");
	searchInfo();
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
	clear_popinfo("add");
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
function clear_popinfo(flag){
	if(flag == "su"){
		
	}else if(flag = "add"){
		$('#fileShowName').val("");
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
	clear_popinfo("su");
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
