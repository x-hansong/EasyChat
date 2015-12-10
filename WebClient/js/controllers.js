weChat.controller('weChatCtrl', function($scope){
	$scope.nameChange={
		"name":""
	};
})
//注册控制模块
var registerCtrls=angular.module('registerCtrls', ['mainCtrls']);
registerCtrls.controller('registerCtrl1',function($scope,$http,$state){
	//注册信息
	$scope.registerMessage={
		name:"",
		password:""
	};
	//注册提交处理
	$scope.registerSub=function(){
		 $http.post('http://119.29.26.47/v1/users',$scope.registerMessage)
	        .success(function(data) {
	            console.log(data);
	            //$scope.userMessage=data;
	            $state.go('main',{},{reload:true});
				})
	        .error(function(data){
	     		if(data.error=="duplicate_unique_property_exists")
	     			alert("用户名以存在！");
	     		else
	        		alert("an unexpected error ocurred!");
	        	//console.log(data);
    });
	};
	$scope.nameChange.name="张文翔";
	//登陆信息
	$scope.loginMessage={
		name:"",
		password:""
	};
	//登陆提交处理
	$scope.loginSub=function(){
		 $http.post('http://119.29.26.47/v1/users/authorization',$scope.loginMessage)
	        .success(function(data) {
	            //console.log(data);
	            $state.go('main',{},{reload:true});
				})
	        .error(function(data){
	     		// if(data.error=="invalid_grant")
	     		// 	alert("用户名或密码错误！");
	     		// else
	       //  		alert("an unexpected error ocurred!");
	        	//console.log(data);
	        	$state.go('main',{},{reload:true});
    });

	};
})
//主页控制模块
var mainCtrls=angular.module('mainCtrls',[]);
mainCtrls.controller('mainCtrl1',function($scope,$http,$state){
	$scope.userMessage={
		"token" : "4d7e4ba0-dc4a-11e3-90d5-e1ffbaacdaf5",
		"user":{
			"id":"7f90f7ca-bb24-11e2-b2d0-6d8e359945e4",
			"name":"hello123",
			"nick":$scope.nameChange.name,  
			"sex":"男",
			"phone":"18812123456",
			"email":"15666@qq.com",
			"avatar":"image/cong.jpg",
			"sigh_info":"我是说在座的各位" 
		    }
};
	$scope.chatLists=[
		{"name":"张力","id":"1","img_src":"image/cong.jpg"},
	    {"name":"肖航","id":"2","img_src":"image/cong.jpg"},
	    {"name":"肖劲","id":"3","img_src":"image/cong.jpg"},
	    {"name":"肖汉松","id":"4","img_src":"image/cong.jpg"},
	    {"name":"吴涛宇","id":"5","img_src":"image/cong.jpg"},
	    {"name":"杨民浩","id":"6","img_src":"image/cong.jpg"}
	];
})
