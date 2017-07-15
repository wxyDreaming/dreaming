

$(function() {
	
	$('#account').blur(function(){
		var account = $('#account').val().replace(/\s+/g, "");
		if(account.length != 0){
			$('#user-fish').removeClass("am-icon-user");
	        $('#user-fish').addClass("am-icon-spinner am-icon-spin");
	        $.ajax({
	    		url: getUrl("userController/isExist"), 
	    		type:'POST',
	    		data:{'account':account},
	    		dataType: "json",
	    		cache: false,
	    		success: function(data){
	    			if (data.code == "1") {
	                    $('#account-fish').fadeIn("slow");
	                    $('#user-fish').removeClass("am-icon-spinner am-icon-spin");//加载
	        			$('#user-fish').addClass("am-icon-user");
	        			$('#account-fish').text("该用户不存在");
	                 } else if(data.code == "0"){
	                	 $('#account-fish').hide();
	                	 $('#user-fish').removeClass("am-icon-spinner am-icon-spin");
	                	 $('#user-fish').addClass("am-icon-user");
	                 }
	          }
	    	});
		}else{
			$('#account-fish').text("请输入您的账号");
			$('#account-fish').fadeIn("slow");
			$('#user-fish').removeClass("am-icon-spinner am-icon-spin");
			$('#user-fish').addClass("am-icon-user am-icon-sm am-icon-fw");
		}
	});
	
	$('#login-fish').click(function(){
	    var account = $('#account').val().replace(/\s+/g, "");
	    var password = $('#password').val().replace(/\s+/g, "");
	    if(!account){
	    	swal("请输入您的账号！");
	    }else if(!password){
	    	swal("请输入密码！");
	    }else{
	    	
	    	$.ajax({
	    		url: getUrl("loginController/login"), 
	    		type:'POST',
	    		data:{'account':account,'password':password},
	    		dataType: "json",
	    		cache: false,
//	    		async: false,
	    		success: function(data){
	    			if(data.code == "1"){
	    				sessionStorage.setItem('user', data.user);
	    				window.location.href="index.html";
	    			} 
	    			if(data.code == "0"){
	    				swal(data.message);
	    			}
	           },
	           error:function(){
	        	  
	          }
	    	});
	    }
	});
  
});

