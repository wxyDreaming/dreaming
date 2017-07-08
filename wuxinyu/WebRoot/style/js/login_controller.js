/**
 * Created by limbo on 2016/12/2.
 */

$(function () {
    $("#userName").change(function () {
        var userName = $("#userName").val().replace(/\s+/g, "");
        $.post("loginController/isExist.do",
            {
                userName: userName
            }, function (data) {
                if (data == "fail") {
                    swal({
                        title: "用户不存在",
                        type: "error"
                    });
                }
            })
    })

    $("#userPassword").change(function () {
        var userPassword = $("#userPassword").val().replace(/\s+/g, "");
        if(userPassword == ""){
            swal({
                title: "密码不为空",
                type: "error"
            });
        }
    })
});