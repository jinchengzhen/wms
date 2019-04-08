var basepath;
$(function(){
	basepath = getBathpath();
	select_status($("#all"));
	searchInfo();
	$("#model_download").attr("href","../../../static/fileModel/supplyModel.xls");
});
/*--------------按条件查询-----------------*/
function searchInfo(){
	
}
/*--------------审批--------------*/
function deal_approve(){
	
}

/*------------上传文件------------*/
//上传文件
var file_url="";
uploading=false;	
function upfile(){
    if(uploading){
        alert("文件正在上传中，请稍候");
        return false;
    }
	$('#fileShowName').val($("#submitFile").val());
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
       	var state=data.state;
		var mag=data.message;
		if(state ==1){
			file_url=mag;
		}else {
			alert(mag);
		}
        uploading = false;
    }
    });
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
