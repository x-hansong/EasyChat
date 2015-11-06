$(function(){
    $("#nav-login").bind("click",function(){
        $("#login").show();
        $("#register").hide();
        $(".mainbox ul #nav-login").addClass("active");
        $(".mainbox ul #nav-register").removeClass("active");
    });
    $("#nav-register").bind("click",function(){
        $("#register").show();
        $("#login").hide();
        $(".mainbox ul #nav-register").addClass("active");
        $(".mainbox ul #nav-login").removeClass("active");
    });
});