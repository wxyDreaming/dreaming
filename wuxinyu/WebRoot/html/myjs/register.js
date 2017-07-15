/**
 * Created by Administrator on 2017/1/12.
 */
'use strict';

//默认手机号码
var mb = sessionStorage.getItem('mb');
$('#mobile').val(mb);
//注册
$('#register').click(function(){
    var mobile = $('#mobile').val();
    var passWord = $('#passWord').val();
    var againPw = $('#againPw').val();
    var paypassword = $('#paypassword').val();
    var qrypaypassword = $('#qrypaypassword').val();
    var email = $('#email').val();
    var code = $('#code').val();
    if(!mobile){
    	alertFun('请输入手机号码！')
    }else if(!passWord){
    	alertFun('请输入登录密码！')
    }else if(!againPw){
    	alertFun('请确认登录密码！')
    }else if(!passWord){
    	alertFun('请输入支付密码！')
    }else if(!qrypaypassword){
    	alertFun('请确认支付密码！')
    }else if(passWord != againPw){
    	alertFun('两次登录密码不相同！')
    }else if(paypassword != qrypaypassword){
    	alertFun('两次支付密码不相同！')
    }else if(!code){
    	alertFun('请输入验证码！')
    }else if(!email){
    	alertFun('请输入邮箱！')
    }else{
//    	请求注册接口  
    	$.ajax({
    		url: getUrl("/pc/register"), 
    		type:'POST',
    		data:{
    			'mobile':mobile,
    			'password':passWord,
    			'qrypassword':againPw,
    			'paypassword':paypassword,
    			'qrypaypassword':qrypaypassword,
    			'email':email,
    			'code':code
    			},
    		dataType: "json",
    		cache: false,
    		success: function(respond){
    			switch(respond.code){
    			case '0':
    				console.log('注册成功！')
    				window.location.href = 'index.html';
    				break;
    			case '-1':
    				alertFun('参数为空！');
    				break;
    			case '-2':
    				alertFun('该用户已经被注册！');
    				break;
    			case '1':
    				alertFun(respond.data);
    				break;
    			}
          }
    	});
    }
    
});

//修改密码
$('#editPassword').click(function(){
    var mobile = $('#mobile').val();
    var passWord = $('#passWord').val();
    var againPw = $('#againPw').val();
    var code = $('#code').val();
    if(!mobile){
    	alert('请输入手机号码！')
    }else if(!passWord){
    	alert('请输入密码！')
    }else if(!againPw){
    	alert('请确认密码！')
    }else if(passWord != againPw){
    	alert('两次密码不相同！')
    }else if(!code){
    	alert('请输入验证码！')
    }else{
//    	请求注册接口
    	$.ajax({
    		url: getUrl("/pc/editPassword"), 
    		type:'POST',
    		data:{
    			'mobile':mobile,
    			'password':passWord,
    			'code':code
    			},
    		dataType: "json",   
    		cache: false,
    		success: function(respond){
    			switch(respond.code){
    			case '0':
    				console.log('更新成功！')
    				window.location.href = 'index.html';
    				break;
    			case '-1':
    				alert('参数为空！');
    				break;
    			case '-2':
    				alert('参数错误！');
    				break;
    			case '-3':
    				alert('验证码无效！');
    				break;
    			}
          }
    	});
    }
    
});

//获取验证码
$('.input-group-btn button').html('获取验证码');

$('.input-group-btn').click(function(){
	var mobile = $('#mobile').val();
	var time = 10,
    timePromise = undefined;
	if (!mobile) {
		alert("请输入手机号码！");
	} else {
		$.ajax({
			url: getUrl('/pc/sendSMSCode'), 
			type:'POST',
			data:{
				'mobile':mobile
				},
			dataType: "json",
			cache: false,
			success: function(respond){
				switch(respond.status){
				case '0':
					timePromise = window.setInterval(function () {
			            if (time <= 0) {
			              window.clearInterval(timePromise)
			              timePromise = undefined;

			              time = 10;
			              $('.input-group-btn button').html('重发验证码')
			            } else {
			            	$('.input-group-btn button').html(time + "秒后可重发")
			            	time--;
			            }
			          }, 1000);
					break;
				case '-1':
					alert('手机号为空！');
					break;
				case '-2':
					alert('发送失败！');
					break;
				}
	      }
		});
	}
})
	