/**
 * 登录注册相关
 */

//绑定
$('#toLogin').click(function(){
    var mobile = $('#mobile').val();
    var code = $('#code').val();
    if(!mobile){
    	alertFun('请输入手机号码！')
    }else if(!code){
    	alertFun('请输入验证码！')
    }else{
//    	请求注册接口
    	$.ajax({
    		url: getUrl("/pc/bindPhone"), 
    		type:'POST',
    		data:{
    			'mobile':mobile,
    			'openId':openId,
    			'code':code
    			},
    		dataType: "json",
    		cache: false,
    		success: function(respond){
    			switch(respond.code){
    			case '0':
    				console.log('绑定成功！')
    				sessionStorage.setItem('mobile', mobile);
    				window.location.href = 'index.html';
    				break;
    			case '-1':
    				alertFun('参数为空！');
    				break;
    			case '-2':
    				alertFun('该用户已经被注册！');
    				break;
    			case '-3':
    				alertFun('验证码无效！');
    				break;
    			}
          }
    	});
    }
    
});

//获取验证码

$('.input-group-btn').click(function(){
	var mobile = $('#mobile').val();
	var time = 10,
    timePromise = undefined;
	if (!mobile) {
		alert("请输入手机号码！");
	} else {
		$.ajax({
			url: getUrl('/pc/sendCode'), 
			type:'POST',
			data:{
				'mobile':mobile
				},
			dataType: "json",
			cache: false,
			success: function(respond){
				switch(respond.code){
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
					alert('用户不存在！');
					break;
				case '-2':
					alert('未绑定！');
					break;
				}
	      }
		});
	}
})
	