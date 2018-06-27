

$(function(){
	
	//昵称
	$('#register-nickname').blur(function(){
		var nickname = $('#register-nickname').val().replace(/\s+/g, "");
		if(!nickname){
			$('#register-nickname').popover({
				theme: 'danger sm',
			    content: '请输入你的昵称'
			});
			$('#register-nickname').popover('open');
		}else{
			$('#register-nickname').popover('close');
		}
	});
	
	//账号
	$('#register-account').blur(function(){
		var account = $('#register-account').val().replace(/\s+/g, "");
		if(account.length != 0){
			$('#user-fish').removeClass("am-icon-user");
	        $('#user-fish').addClass("am-icon-spinner am-icon-spin");
	        var a = /^[A-Za-z0-9]+$/;
	        if(!a.test(account)){
	        	$('#register-account').popover({
					theme: 'danger sm',
				    content: ''
				});
				$('#register-account').popover('setContent',"只能输入字母或数字");
				$('#register-account').popover('open');
				$('#user-fish').removeClass("am-icon-spinner am-icon-spin");//加载
    			$('#user-fish').addClass("am-icon-user");
				return;
	        }
	        
	        $.ajax({
	    		url: getUrl("userController/isExist"), 
	    		type:'POST',
	    		data:{'account':account},
	    		dataType: "json",
	    		cache: false,
	    		success: function(data){
	    			if (data.code == "1") {//不存在用户可以注册
	                    $('#user-fish').removeClass("am-icon-spinner am-icon-spin");//加载
	        			$('#user-fish').addClass("am-icon-user");
	        			$('#register-account').popover('close');
	                 } else if(data.code == "0"){
	                	 $('#user-fish').removeClass("am-icon-spinner am-icon-spin");
	                	 $('#user-fish').addClass("am-icon-user");
	                	 
	                	 $('#register-account').popover({
	         				theme: 'danger sm',
	         			    content: '该用户已存在'
	         			 });
	                	 $('#register-account').popover('setContent',"该用户已存在");
	                	 $('#register-account').popover('open');
	                 }
	    		}
	    	});
		}else{
			$('#register-account').popover({
				theme: 'danger sm',
			    content: '请输入你的账号'
			});
			$('#register-account').popover('setContent',"请输入你的账号");
			$('#register-account').popover('open');
		}
	});
	
	//密码
	$('#confirm-password').blur(function(){
		var registerPassword = $('#register-password').val().replace(/\s+/g, "");
		var confirmPassword = $('#confirm-password').val().replace(/\s+/g, "");
		if(registerPassword != confirmPassword){
			$('#confirm-password').popover({
				theme: 'danger sm',
			    content: '密码不一致'
			});
			$('#confirm-password').popover('open');
		}
	});
	
	//注册提交
	$('#register-submit').click(function(){
		var nickname = $('#register-nickname').val().replace(/\s+/g, "");
		var account = $('#register-account').val().replace(/\s+/g, "");
		var registerPassword = $('#register-password').val().replace(/\s+/g, "");
		var confirmPassword = $('#confirm-password').val().replace(/\s+/g, "");
		
		if(nickname.length != 0 && account.length != 0 && registerPassword.length != 0 && confirmPassword.length != 0){
			
			var a = /^[A-Za-z0-9]+$/;
	        if(!a.test(account)){
	        	$('#register-account').popover({
					theme: 'danger sm',
				    content: ''
				});
				$('#register-account').popover('setContent',"只能输入字母或数字");
				$('#register-account').popover('open');
				return;
	        }
	        
			if(registerPassword != confirmPassword){
				$('#confirm-password').popover({
					theme: 'danger sm',
				    content: '密码不一致'
				});
				$('#confirm-password').popover('open');
				return;
			}
			$.ajax({
	    		url: getUrl("userController/registerAccount"), 
	    		type:'POST',
	    		data:{'nickname':nickname,'account':account,'password':confirmPassword},
	    		dataType: "json",
	    		cache: false,
	    		success: function(data){
	    			if(data.code == 1){
	    				swal({
	    					  title: "注册成功",
	    					  text: "是否登录？",
	    					  type: "success",
	    					  showCancelButton: true,
	    					  confirmButtonColor: "#6888f7",
	    					  confirmButtonText: "  Yes  ",
	    					  closeOnConfirm: false
	    					},
	    					function(){
	    						sessionStorage.setItem('user', JSON.stringify(data.user));
	    						window.location.href='index.html';
	    				});
	    			}else if(data.code == 0){
	    				swal(data.message);
	    			}
	    		},
	    		error: function(){
	    			 swal("服务器异常，请稍后再试");
	    		}
	    	});
		}else{
			swal("请将信息填写完整");
		}		
	});
	
});