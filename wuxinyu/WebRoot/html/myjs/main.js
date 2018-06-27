
$(function(){
	loginOut();//是否登录
	
	//退出
	$('#logOut-fish').click(function(){
		sessionStorage.removeItem('user');
		window.location.href='index.html';
	});
});

//请求地址
function getUrl(url){
	var IP = 'http://localhost:8080/wuxinyu/';
	return IP + url;
}

//获取当前地址栏参数值-公共方法
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

/*是否登录*/
function loginOut(){
	 if(sessionStorage.getItem('user')){//登录
	    	$('#photo-fish').show();
	    	$('#login-fish').hide();
	    }else{//未登录
	    	$('#photo-fish').hide();
	    	$('#login-fish').show();
	    }
}

// 字符串替换；
function replace(obj) {
	alert(obj.value.Replace("a", "d"));
}
// 另存为文件
function SaveCode(obj, filename) {
	var win = window.open('', '_blank', 'top=100');
	var code = obj.innerText;
	code = code == null || code == "" ? obj.value : code;
	win.opener = null;
	win.document.write(code);
	win.document.execCommand('saveas', true, filename);
	win.close();
}
// 问候
window.onload = function() {
	var now = new Date();
	var hour = now.getHours();
	var greeting;
	if (hour < 6)
		greeting = "凌晨好";
	else if (hour < 10)
		greeting = "早上好";
	else if (hour < 14)
		greeting = "中午好";
	else if (hour < 18)
		greeting = "下午好";
	else
		greeting = "晚上好";

	document.getElementById("hi").innerHTML = "<font color=red>" + greeting
			+ "</font>";
}
// 将光标停在对象的最后
function PutCursorAtLast(obj) {
	obj.focus();
	var range = obj.createTextRange();
	range.moveStart('character', obj.value.length);
	range.collapse(true);
	range.select();
}
// 将光标停在对象的最前
function PutCursorAtFirst(obj) {
	obj.focus();
	var range = obj.createTextRange();
	range.moveStart('character', 0);
	range.collapse(true);
	range.select();
}

    
// 去所有空格
String.prototype.trimAll = function (){
	return this.replace(/\s+/g, "");
}

// 去首尾空格
String.prototype.Trim = function() { 
	return this.replace(/(^s*)|(s*$)/g, ""); 
}

String.prototype.LTrim = function() { 
	return this.replace(/(^s*)/g, ""); 
}

String.prototype.RTrim = function() { 
	return this.replace(/(s*$)/g, ""); 
}

// 字符串替换
String.prototype.Replace = function(oldValue,newValue){ 
        var reg = new RegExp(oldValue,"g"); 
        return this.replace(reg, newValue); 
}