/**
 * Created by limbo on 2016/12/2.
 */
$(function () {
    $("#registerName").change(function () {
        var registerName = $("#registerName").val().replace(/\s+/g, "");
        $.post("loginController/isExist.do",
            {
                userName: registerName
            }, function (data) {
                if (data != "fail") {
                    swal({
                        title: "用户已存在",
                        type: "error"
                    });
                }
            })
    })

    $("#verifyPassword").change(function () {

        var registerPassword = $("#registerPassword").val().replace(/\s+/g, "");
        var verifyPassword = $("#verifyPassword").val().replace(/\s+/g, "");

        if(verifyPassword == ""){
            swal({
                title: "再次输入密码不为空",
                type: "error"
            });
            return;
        }

        if(verifyPassword != registerPassword){
            swal({
                title: "两次输入的密码不一样",
                type: "error"
            });
        }
    })

    $("#registerPassword").change(function () {
        var registerPassword = $("#registerPassword").val().replace(/\s+/g, "");
        if(registerPassword == ""){
            swal({
                title: "密码不为空",
                type: "error"
            });
        }
    })

})