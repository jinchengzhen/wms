var basepath;
$(function(){
	basepath = getBathpath();
});
/*----------------打印预览-------------*/

/*----------------打印样式-------------*/
//$("#myElementId").print({
//
//globalStyles:true,//是否包含父文档的样式，默认为true
//
//mediaPrint:false,//是否包含media='print'的链接标签。会被globalStyles选项覆盖，默认为false
//
//stylesheet:null,//外部样式表的URL地址，默认为null
//
//noPrintSelector:".no-print",//不想打印的元素的jQuery选择器，默认为".no-print"
//
//iframe:true,//是否使用一个iframe来替代打印表单的弹出窗口，true为在本页面进行打印，false就是说新开一个页面打印，默认为true
//
//append:null,//将内容添加到打印内容的后面
//
//prepend:null,//将内容添加到打印内容的前面，可以用来作为要打印内容
//
//deferred: $.Deferred()//回调函数
//
//});
function generateCode(sku,goodsName){
	var url = basepath+"printor/generateCode.spring";
	var args = {"SKU":sku,"goodsName":goodsName};
	$.post(url,args,function(data){
		var jsonobj = JSON.parse(data);
		$("#print_preview").attr("src",jsonobj.url);
	});
	show_print_pop();
}
/*------------弹窗开启与关闭------------*/
//打开标签打印
function show_print_pop(){
	$("#black_block").css("display","block");
	$("#print_block").css("display","block");
}
//关闭标签打印
function close_print_pop(){
	$("#print_block").css("display","none");
	$("#black_block").css("display","none");
}
//打开快速入库
function show_storein_pop(){
	$("#black_block").css("display","block");
	$("#storein_block").css("display","block");
}
//关闭快速入库
function close_storein_pop(){
	$("#storein_block").css("display","none");
	$("#black_block").css("display","none");
}
//打开快速出库
function show_storeout_pop(){
	$("#black_block").css("display","block");
	$("#storeout_block").css("display","block");
}
//关闭快速出库
function close_storeout_pop(){
	$("#storeout_block").css("display","none");
	$("#black_block").css("display","none");
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