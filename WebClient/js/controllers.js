//注册控制模块
var registerCtrls=angular.module('registerCtrls', []);
registerCtrls.controller('registerCtrl1',function($scope,$http){
	$scope.registerMessage={
		name:"",
		password:""
	};
	$scope.registerSub=function(){
		 $http.post('http://119.29.26.47/v1/users',$scope.registerMessage)
	        .success(function(data) {
	            console.log(data);
				})
	        .error(function(data){
	        	alert("an unexpected error ocurred!");
	        	 console.log(data);
    });

	};
})
