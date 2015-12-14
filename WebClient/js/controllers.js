weChat.controller('weChatCtrl', function($scope){
	$scope.userMessage={
		"token" : '4d7e4ba0-dc4a-11e3-90d5-e1ffbaacdaf5',
		"user":{
			"id":"7f90f7ca-bb24-11e2-b2d0-6d8e359945e4",
			"name":"hello123",
			"nick":"张文聪",  
			"sex":"男",
			"phone":"18812123456",
			"email":"15666@qq.com",
			"avatar":"image/cong.jpg",
			"sigh_info":"我是说在座的各位" 
		    }
	};

})
//注册控制模块
var registerCtrls=angular.module('registerCtrls', []);
registerCtrls.controller('registerCtrl1',function($scope,$http,$state,$rootScope){
	//注册信息
	$scope.registerMessage={
		name:"",
		password:""
	};
	//登陆信息
	$scope.loginMessage={
		name:"",
		password:""
	};
	//获取用户信息处理
	$scope.getUserMessage=function(){
		$http.get('http://119.29.26.47:8080/v1/users/'+$scope.loginMessage.name)
			.success(function(data){
				$scope.userMessage.user=data;
			})
	}

	//登陆提交处理
	$scope.loginSub=function(){
		 $http.post('http://119.29.26.47:8080/v1/users/authorization',$scope.loginMessage)
	        .success(function(data) {
	            //console.log(data);
	            $scope.userMessage.token=data.uuid;
	            $scope.getUserMessage();
	            $state.go('main',{},{reload:true});
				})
	        .error(function(data){
	     		if(data.error=="invalid_grant")
	     			alert("用户名或密码错误！");
	     		else
	        		alert("未知错误!");
	        	//console.log(data);
    });

	};
	//注册提交处理
	$scope.registerSub=function(){
		 $http.post('http://119.29.26.47:8080/v1/users',$scope.registerMessage)
	        .success(function(data) {
	            // console.log(data);
	            // $state.go('main',{},{reload:true});
	            $scope.loginMessage=data;
	            $scope.loginSub();
				})
	        .error(function(data){
	     		if(data.error=="duplicate_unique_property_exists")
	     			alert("用户名以存在！");
	     		else
	        		alert("未知错误!");
	        	//console.log(data);
    });
	};
})
//主页控制模块
var mainCtrls=angular.module('mainCtrls',[]);
mainCtrls.controller('mainCtrl1',function($scope,$http,$state){
	$scope.chatLists=[
		{"name":"张力","id":"1","img_src":"image/cong.jpg"},
	    {"name":"肖航","id":"2","img_src":"image/cong.jpg"},
	    {"name":"肖劲","id":"3","img_src":"image/cong.jpg"},
	    {"name":"肖汉松","id":"4","img_src":"image/cong.jpg"},
	    {"name":"吴涛宇","id":"5","img_src":"image/cong.jpg"},
	    {"name":"杨民浩","id":"6","img_src":"image/cong.jpg"}
	];
	// 获取用户
	// 注销处理
	$scope.logoffSub=function(){
		$http({
			method:'delete',
			url:'http://119.29.26.47/v1/users/authentication/'+$scope.userMessage.token,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			$state.go('main',{},{reload:true});
			})
		.error(function(data){
			if(data.error=="auth_bad_access_token")
	     		alert("未授权！");
	     	else
	        	alert("未知错误!");
			})
	}
	//修改用户信息处理
	$scope.alterUserMessageSub=function(){
		$http({
			method:'put',
			url:'http://119.29.26.47/v1/users/'+$scope.userMessage.user.name,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			alert("修改成功！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==413)
				alert("请求过大！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 添加好友处理
	$scope.addFriendName="zl36192031";
	$scope.addFriendSub=function(){
		$http({
			method:'post',
			url:'http://119.29.26.47/v1/users/'+$scope.userMessage.name+'/contacts/users/'+$scope.addFriendName,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			alert("请求已发送！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 删除好友处理
	$scope.delFriendName="";
	$scope.addFriendSub=function(){
		$http({
			method:'delete',
			url:'http://119.29.26.47/v1/users/'+$scope.userMessage.name+'/contacts/users/'+$scope.delFriendName,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			alert("删除成功！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 查看好友信息处理
	$scope.searchFriendName="";
	$scope.searchFriendSub=function(){
		$http({
			method:'get',
			url:'http://119.29.26.47/v1/users/'+$scope.userMessage.name+'/contacts/users/'+$scope.searchFriendName,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			$scope.friendMessage=data;
			$state.go('friendMsg',{},{reload:true});
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 查看陌生人信息处理
	$scope.searchStrangerName="";
	$scope.searchStrangerSub=function(){
		$http({
			method:'get',
			url:'http://119.29.26.47/v1/users/'+$scope.userMessage.name+'/contacts/users/'+$scope.searchStrangerName,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			$scope.strangerMessage=data;
			$state.go('strangerMsg',{},{reload:true});
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 创建群处理
	$scope.createGroupMessage={
		"name":"hellogroup",
		"avatar":"image/cong.jpg",
		"creater":$scope.userMessage.id,
		"userCnt":"3",
		"announcement":"this is king's group",
		"members":["jma2","jam3"]
	};
	$scope.createGroupSub=function(){
		$http({
			method:'post',
			url:'http://119.29.26.47/v1/groups',
			data:$scope.createGroupMessage,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			$scope.groupMessage=data;
			// $state.go('strangerMsg',{},{reload:true});
			alert("创建成功！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 解散群处理
	$scope.delGroupSub=function(){
		$http({
			method:'delete',
			url:'http://119.29.26.47/v1/groups/'+$scope.userMessage.id+'/'+'group_id',
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			// $scope.groupMessage=data;
			// $state.go('strangerMsg',{},{reload:true});
			alert("解散成功！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 修改群信息处理
	$scope.alterGroupMessage={
		'announcement':'this is new announcement',
		'avatar':''
	};
	$scope.alterGroupMessageSub=function(){
		$http({
			method:'patch',
			url:'http://119.29.26.47/v1/groups/'+$scope.userMessage.id+'/'+'group_id',
			data:$scope.alterGroupMessage,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			// $scope.groupMessage=data;
			// $state.go('strangerMsg',{},{reload:true});
			alert("修改成功！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 退出群处理
	$scope.quitGroupSub=function(){
		$http({
			method:'delete',
			url:'http://119.29.26.47/v1/groups/'+'group_id'+'/users/'+$scope.userMessage.id,
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			// $scope.groupMessage=data;
			// $state.go('strangerMsg',{},{reload:true});
			alert("退出成功！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 邀请入群处理

	$scope.inviteGroupSub=function(){
		$http({
			method:'post',
			url:'http://119.29.26.47/v1/groups/'+'group_id'+'/users',
			data:{'user_ids':['uid1','uid2']},
			headers:{
				'Authorization':'bearer'+$scope.userMessage.token
			}
		}).success(function(data){
			$scope.groupMessage=data;
			// $state.go('strangerMsg',{},{reload:true});
			alert("创建成功！");
			})
		.error(function(status){
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==404)
				alert("请求的用户不存在！");
			if(status>=500)
				alert("未知错误！");
				
			})
	}
	// 
})