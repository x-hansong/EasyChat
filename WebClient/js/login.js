$(function(){
    $("#nav-login").bind("click",function(){
        $("#loginOrRegister").text("登录");
        $(".mainbox ul #nav-login").addClass("active");
        $(".mainbox ul #nav-register").removeClass("active");
    });
    $("#nav-register").bind("click",function(){
        $("#loginOrRegister").text("注册");
        $(".mainbox ul #nav-register").addClass("active");
        $(".mainbox ul #nav-login").removeClass("active");
    });
});