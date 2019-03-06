//消息框
var timer_0 = null;
function msg(mag){
	if(timer_0 != null){
		clearTimeout(timer_0);
	}
	var msgobj = $("#msg",window.top.document);
	msgobj.find("span").html(mag);
	msgobj.css("display","block");
	msgobj.animate({"display":"block","bottom":"7%"},1500);
	timer_0=setTimeout(function(){
		msgobj.animate({"bottom":"-40%","display":"none"},1500);
	},3000);
}

function getBathpath(){
	return $("#basePath",window.top.document).attr("basePath");
}
/*//验证session
function check_sessionIsOutdate(){
	var url = basepath+"system/checkSession.spring";
	$.post(url,{},function(data){
	});
}*/

/*时间格式*/
Date.prototype.Format = function (fmt) { //author: meizz
var o = {
"M+": this.getMonth() + 1, //月份
"d+": this.getDate(), //日
"h+": this.getHours(), //小时
"m+": this.getMinutes(), //分
"s+": this.getSeconds(), //秒
"q+": Math.floor((this.getMonth() + 3) / 3), //季度
"S": this.getMilliseconds() //毫秒
};
if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
for (var k in o)
if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
return fmt;
}



//阻止事件冒泡
function stopPropagation(e) {  
    e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }  
}

/*身份证合法性验证*/
//支持15位和18位身份证号
//支持地址编码、出生日期、校验位验证
function IdentityCodeValid(code) { 
var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
var tip = "";
var pass= true;

if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
    tip = "身份证号格式错误";
    pass = false;
}

else if(!city[code.substr(0,2)]){
    tip = "地址编码错误";
    pass = false;
}
else{
    //18位身份证需要验证最后一位校验位
    if(code.length == 18){
        code = code.split('');
        //∑(ai×Wi)(mod 11)
        //加权因子
        var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
        //校验位
        var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
        var sum = 0;
        var ai = 0;
        var wi = 0;
        for (var i = 0; i < 17; i++)
        {
            ai = code[i];
            wi = factor[i];
            sum += ai * wi;
        }
        var last = parity[sum % 11];
        if(parity[sum % 11] != code[17]){
            tip = "校验位错误";
            pass =false;
        }
    }
}
console.log(tip);
return pass;
}


//重定向
$.ajaxSetup( {
	//设置ajax请求结束后的执行动作
	complete : 
	function(XMLHttpRequest, textStatus) {
	// 通过XMLHttpRequest取得响应头，sessionstatus
	var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
	if (sessionstatus == "TIMEOUT") {
	var win = window;
	while (win != win.top){
	win = win.top;
	}
	win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");
	alert('登录已过期，请重新登录！');
	}
	}
	});
