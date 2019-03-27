var loginer = null;//登录信息
//登录
function login(){
	var openid = $.openLoadForm({ gif:'/WMS-JL/static/gif/load.gif', msg:'正在登录...'   });
	var url = "system/login.spring";
	var userID = $("#userID").val();
	var orgPwd = $("#password").val();
	RSAUtils.setMaxDigits(200);  
	var publicKeyExponent = $("#basePath").attr("publicKeyExponent");
	var publicKeyModulus = $("#basePath").attr("publicKeyModulus");
	var basepath = $("#basePath").attr("basePath");
	url = basepath +url;
	var key = new RSAUtils.getKeyPair(publicKeyExponent, "", publicKeyModulus);  
	var password = RSAUtils.encryptedString(key,orgPwd.split("").reverse().join(""));  
	var args = {"userID":userID,"password":password};
	$.post(url,args,function(data){
		loginer = JSON.parse(data);
		var mag = loginer.message;
		$.closeLoadForm(openid);
		if(loginer.state == 1){
			location.href = mag;
		}else{
			msg(mag);
		}
	});
}




/*---AOP编程------*/
Function.prototype.before = function(beforefn){
	var _self = this;      //保存原函数引用
	return function(){    
		beforefn.apply(this,arguments);      //执行新函数，修正this
		return _self.apply(this,arguments);  //执行原函数
	}
};

Function.prototype.after = function(afterfn){
	var _self = this;
	return function(){
	    var ret = _self.apply(this, arguments);   //【不要直接写在return中】
            afterfn.apply(this, arguments);
            return ret;		
	}
}

