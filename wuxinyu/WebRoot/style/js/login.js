/**
 * Created by limbo on 2016/11/30.
 */
$(function () {
    //当鼠标焦点在密码框的时候，猫头鹰遮眼
    $(".password").focus(function () {
        $("#left_hand").animate({
            left: "150",
            top: " -38"
        }, {
            step: function () {
                if (parseInt($("#left_hand").css("left")) > 140) {
                    $("#left_hand").attr("class", "left_hand");
                }
            }
        }, 2000);
        $("#right_hand").animate({
            right: "-64",
            top: "-38px"
        }, {
            step: function () {
                if (parseInt($("#right_hand").css("right")) > -70) {
                    $("#right_hand").attr("class", "right_hand");
                }
            }
        }, 2000);
    });
    //失去焦点,切换css样式
    $(".password").blur(function () {
        $("#left_hand").attr("class", "initial_left_hand");
        $("#left_hand").attr("style", "left:100px;top:-12px;");
        $("#right_hand").attr("class", "initial_right_hand");
        $("#right_hand").attr("style", "right:-112px;top:-12px");
    });

    //切换登录注册标签页的特效
    //也就是切换css样式
    $("#tab_login").mouseover(function () {
        $("#tab_login").attr("class","tab_chosen");
        $("#tab_register").attr("class","tab_default");

        $("#login_frame").show();
        $("#register_frame").hide();
    });

    $("#tab_register").mouseover(function () {
        $("#tab_login").attr("class","tab_default");
        $("#tab_register").attr("class","tab_chosen");

        $("#login_frame").hide();
        $("#register_frame").show();
    })

});
