var menuid = new Array();
var jsonarray;//拥有权限
var basepath;
$(function(){
	basepath = $("#basePath").attr("basePath");
	initmenu();
});
//菜单初始化
function initmenu(){
	var url = basepath +"system/initMenulist.spring";
	$.post(url,{},function(data){
		var jsonobj = JSON.parse(data);
		jsonarray = [jsonobj.A,jsonobj.B,jsonobj.C,jsonobj.D,jsonobj.E];
		var htmlstr_1 = "";
		var htmlstr_2 = "";
		for(var i = 0;i < jsonarray.length;i++ ){
			var menuobj = jsonarray[i];
			if(menuobj != undefined){
				menuid.push("tmenu-"+i);
				htmlstr_1 += "<li url=\""+menuobj.menuUrl+"\" onclick=\"magmenu('tmenu-"+i+"')\">"+menuobj.menuName;
				htmlstr_2 += "<div class=\"menutitle\"><div class=\"menuparent\" url=\""+menuobj.menuUrl+"\" >"+menuobj.menuName+"</div>";
				if(menuobj.childmenu != null){
					htmlstr_1 += "<ul id=\"tmenu-"+i+"\" state='0'>";
					for(var l = 0;l < menuobj.childmenu.length;l++){
						var childobj = menuobj.childmenu[l];
						htmlstr_1 += "<li onclick='showConcrete1(\""+childobj.menuUrl+"\",\""+"tmenu-"+i+"\")'>"+childobj.menuName;
						htmlstr_2 += "<div class=\"menublock\" onclick='showConcrete2(\""+childobj.menuUrl+"\",\""+"tmenu-"+i+"\")'>"+childobj.menuName+"</div>";
					}
					htmlstr_1 += "</ul>";
				}
				htmlstr_1 += "</li>";
				htmlstr_2 += "</div>";
			}
		}
		$("#menu_select").html(htmlstr_1);
		$("#menulist").html(htmlstr_2);
	});
}
//具体功能跳转
function showConcrete1(url,tmenu){
	window.parent.document.getElementById("mainFrame").src = url ;//获取父级元素
	magmenu(tmenu);
}
//具体功能跳转
function showConcrete2(url,tmenu){
	window.parent.document.getElementById("mainFrame").src = url ;//获取父级元素
	magtmenu(tmenu);
}
//显示主菜单
function showMenu(){
	window.parent.document.getElementById("mainFrame").src = basepath+'IFrame/jsp/frame-2.jsp' ;//获取父级元素
}
function magmenu(id){
	var state = $("#"+id).attr("state");
	if(state == "0"){
		$("#"+id).attr("state","1");
		$("#"+id).css("display","block");
		setmenuHidden(id);
	}else if(state == "1"){
		$("#"+id).attr("state","0");
		$("#"+id).css("display","none");
	}
}
function setmenuHidden(id){
	for(var i = 0; i < menuid.length; i++){
		if(menuid[i] != id){
			$("#"+menuid[i]).attr("state","0");
			$("#"+menuid[i]).css("display","none");
		}
	}
}
//子菜单管理
function magtmenu(id){
	var pt = window.parent;
	var tmenu = pt.frames["menuFrame"].document.getElementById(id);
	var state = tmenu.getAttribute("state");
	if(state == "0"){
		tmenu.setAttribute("state","1");
		tmenu.style.display = "block";
	}else if(state == "1"){
		tmenu.setAttribute("state","0");
		tmenu.style.display = "none";
	}
}




