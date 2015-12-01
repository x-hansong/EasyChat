//注册控制模块
var registerCtrls=angular.module('registerCtrls', []);
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
	     		if(data.error=="duplicate_unique_property_exists")
	     			alert("用户名以存在！");
	     		else
	        		alert("an unexpected error ocurred!");
	        	//console.log(data);
    });

	};
})
var mainCtrls=angular.module('mainCtrls',[]);
mainCtrls.controller('mainCtrl1',function($scope,$http,$state){
	$scope.userMessage={
		name:"张文聪",
		password:""
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
