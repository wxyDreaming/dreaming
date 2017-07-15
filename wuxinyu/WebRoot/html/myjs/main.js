
//请求地址
function getUrl(url){
	var IP = 'http://127.0.0.1:8080/wuxinyu/';
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
	 if(sessionStorage.getItem('mobile')){//登录
	    	$('.goLogin').hide();
	    	$('.register').hide();
	    	$('.Setout').show();
	    	$('.myIntegral').show();
	    }else{//未登录
	    	$('.Setout').hide();
	    	$('.goLogin').show();
	    	$('.register').show();
	    	$('.myIntegral').hide();
	    }
}

//退出
$('#setOut').click(function(){
	sessionStorage.removeItem('mobile');
	window.location.href='index.html';
})