/**
 * Created by limbo on 2016/12/2.
 */

$(function () {
    $("#userName").change(function () {
        var account = $("#userName").val().replace(/\s+/g, "");
        $.post("userController/isExist",
            {
        		account: account
            }, function (data) {
                if (data.code == "1") {
                    swal({
                        title: "该用户不存在",
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