

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