weChat.controller('weChatCtrl', function($scope,$http,$state){
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
			"sign_info":"我是说在座的各位" 
		    }
	};
	$scope.chatList={
		"friends":[{
		"id":1,
		"name":"hello1",
		"nick":"Tom",  
		"sex":"1",
		"phone":"18812123456",
		"email":"15666@qq.com",
		"avatar":"http://www.qqtn.com/up/2014-10/201410311103454469999.png",
		"sign_info":"我是说在座的各位"
		}
	]
	};
	$scope.chatPools=[{
			"name":"",
			"message":[
				{
					'from':true,
					'content':""
				}
			]
		}
	];
		$scope.currentChat={
		'name':''
	};
	var stompClient = null;

function connect() {
    var socket = new SockJS('http://119.29.26.47:8080/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        var user = $scope.userMessage.user.name;
        stompClient.subscribe("/queue/channel/"+user, function(msg) {
            // handle position update
            console.log(msg);
            var message=JSON.parse(msg.body);
            // var i=0;
            // for(;$scope.chatPools[i].name!=message.fromUser;i++);

            // $scope.chatPools[i].message.push({
            // 	'from':true,
            // 	'content':message.content
            // });		
            // if(message.fromUser==$scope.currentChat.name){
	            var label =$("<div class='content'>"
	            +"<div class='chatmessage-1-image' style='float:left;'>"
	            +"<img src='image/cong.jpg' width='40px' height='40px'/>"
	            +"</div>"
	            +"<div class='bubble_1'>"
	            +"<div class='bubble_cont'>"
	            +"<div class='plain'>"
	            +"<pre class='js_message_plain ng-binding'>"+message.content+"</pre>"
	            +"</div>"
	            +"</div>"
	            +"</div>"
	            +"</div>");
	        	$(".main-right-chatmessage").append(label);
    		// }
    		// else{

    		// }
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
$scope.sendMessageSub=function(){
	sendMessage();
};
//发送消息函数
function sendMessage()
{
    if($(".main-right-writemessage").val()==""){
        alert("内容不能为空！");
    }
    else {
        var msg = {
            "fromUser": $(".my-name").text(),
            "content": $(".main-right-writemessage").val(),
            "toUser": $(".you-name").text()
        };
        // var i=0;
        // for(;$scope.chatPools[i].name!=msg.fromUser;i++);
        // $scope.chatPools[i].message.push({
        //     	'from':true,
        //     	'content':msg.content
        //     });		
        // console.log($scope.chatPools);		
         var label =$("<div class='content'>"
            +"<div class='chatmessage-1-image' style='float:right;'>"
            +"<img src='image/cong.jpg' width='40px' height='40px'/>"
            +"</div>"
            +"<div class='bubble'>"
            +"<div class='bubble_cont'>"
            +"<div class='plain'>"
            +"<pre class='js_message_plain ng-binding'>"+msg.content+"</pre>"
            +"</div>"
            +"</div>"
            +"</div>"
            +"</div>");
        $(".main-right-chatmessage").append(label);
        var msg_str = JSON.stringify(msg);
        send(msg_str);
        $(".main-right-writemessage").val("");
    }
}




//子发送消息函数
function send(data)
{
    console.log("Send:"+data);
    stompClient.send("/app/chat",{},data);
}
	// 
	//注册信息
	$scope.registerMessage={
		name:"",
		password:""
	};
	//登陆信息
	$scope.loginMessage={
		name:"zl123456",
		password:"zl123456"
	};
	//获取用户信息处理
	$scope.getUserMessage=function(){
		$http({
			url:'http://119.29.26.47:8080/v1/users/'+$scope.loginMessage.name,
			method:'get',
			headers:{
				'x-auth-token':$scope.userMessage.token
			}
		})
			.success(function(data){
				$scope.userMessage.user=data;
				connect();
				$scope.getChatListSub();
			})
	};
	//获取聊天列表处理
	$scope.getChatListSub=function(){
		$http({
			method:'get',
			url:'http://119.29.26.47:8080/v1/users/'+$scope.userMessage.user.name+'/friends',
			headers:{
				'x-auth-token':$scope.userMessage.token
			}
		}).success(function(data, status, headers, config){
			$scope.chatList.friends=data.friends;
			// for(var i=0;data.friends[i]!=null;i++){
			// 	$scope.chatPools[i]={
			// 		"name":data.friends[i].name,
			// 		"message":[
			// 			{
			// 			'from':true,
			// 			'content':""
			// 			}
			// 		]
			// 	};
			// }
			// console.log($scope.chatPools);
			})
		.error(function(data, status, headers, config){
	        	alert("未知错误!");
			})
	}
});
//注册控制模块
var registerCtrls=angular.module('registerCtrls', []);
registerCtrls.controller('registerCtrl1',function($scope,$http,$state,$rootScope){
	//登陆提交处理
	$scope.loginSub=function(){
		 $http.post('http://119.29.26.47:8080/v1/users/authorization',$scope.loginMessage)
	        .success(function(data, status, headers, config) {
	            $scope.userMessage.token=headers('x-auth-token');
	            // console.log(headers('x-auth-token'));
	            $scope.getUserMessage();
	            $state.go('main',{},{reload:true});
				})
	        .error(function(data, status, headers, config){
	     		if(data.error=="invalid_grant")
	     			alert("用户名或密码错误！");
	     		else
	        		alert("未知错误!");
	        	//console.log(data);
    });
	   //      $http({
				// method: 'post',
				// url: 'http://119.29.26.47:8080/v1/users/authorization',
				// data:$scope.loginMessage
				// }).then(function(resp) {
				// // 读取X-Auth-ID
				// $scope.userMessage.token=resp.headers('x-auth-token');
	   //          console.log(resp.headers('x-auth-token'));
	   //          $scope.getUserMessage();
	   //          $state.go('main',{},{reload:true});
				// });
   		// $.ajax({
   		// 	type:'post',
   		// 	url:'http://119.29.26.47:8080/v1/users/authorization',
   		// 	data:$scope.loginMessage,
   		// 	success:function(data,request){
   		// 		$scope.userMessage.token=request.getResponseHeader('x-auth-token');
   		// 		console.log(request.getResponseHeader('x-auth-token'));
   		// 	}
   		// })

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
});
//主页控制模块
var mainCtrls=angular.module('mainCtrls',[]);
mainCtrls.controller('mainCtrl1',function($scope,$http,$state){
	// 注销处理
	$scope.logoffSub=function(){
		$http({
			method:'delete',
			url:'http://119.29.26.47:8080/v1/users/authentication',
			headers:{
				'x-auth-token':$scope.userMessage.token
			}
		}).success(function(data, status, headers, config){
			alert("注销成功！");
			$state.go('login',{},{reload:true});
			})
		.error(function(data, status, headers, config){
			if(data.error=="auth_bad_access_token")
	     		alert("未授权！");
	     	else
	        	alert("未知错误!");
			})
	}
	// 跳转至修改信息页面函数
	$scope.goAlterMessageSub=function(){
		// $scope.changeMessage.user=$scope.userMessage.user;
		$state.go('main.settingChange',{},{});
	}
	//修改用户信息处理
	$scope.alterUserMessageSub=function(){
		$http({
			method:'put',
			url:'http://119.29.26.47:8080/v1/users/'+$scope.userMessage.user.name,
			data:$scope.userMessage.user,
			headers:{
				'x-auth-token':$scope.userMessage.token
			}
		}).success(function(data, status, headers, config){
			alert("修改成功！");
			$state.go('main.setting',{},{reload:false});
			})
		.error(function(data, status, headers, config){
			// $scope.userMessage.user=$scope.changeMessage.user;
			// console.log($scope.changeMessage.user);
			$scope.getUserMessage();
			console.log($scope.userMessage.user);
			if(status==400)
				alert("服务器无法解析！");
			if(status==401)
				alert("未授权！");
			if(status==413)
				alert("请求过大！");
			if(status>=500)
				alert("未知错误！");
			$state.go('main.setting',{},{reload:false});
			})
	}
	// 添加好友处理
	$scope.addFriendName="zl36192031";
	$scope.addFriendSub=function(){
		$http({
			method:'post',
			url:'http://119.29.26.47:8080/v1/users/'+$scope.userMessage.user.name+'/contacts/users/'+$scope.addFriendName,
			headers:{
				'x-auth-token':$scope.userMessage.token
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
			url:'http://119.29.26.47:8080/v1/users/'+$scope.userMessage.user.name+'/contacts/users/'+$scope.delFriendName,
			headers:{
				'x-auth-token':$scope.userMessage.token
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
			url:'http://119.29.26.47:8080/v1/users/'+$scope.userMessage.user.name+'/contacts/users/'+$scope.searchFriendName,
			headers:{
				'x-auth-token':$scope.userMessage.token
			}
		}).success(function(data){
			$scope.friendMessage=data;
			$state.go('friendMsg',{},{reload:false});
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
			url:'http://119.29.26.47:8080/v1/users/'+$scope.userMessage.user.name+'/contacts/users/'+$scope.searchStrangerName,
			headers:{
				'x-auth-token':$scope.userMessage.token
			}
		}).success(function(data){
			$scope.strangerMessage=data;
			$state.go('strangerMsg',{},{reload:false});
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
			url:'http://119.29.26.47:8080/v1/groups',
			data:$scope.createGroupMessage,
			headers:{
				'x-auth-token':$scope.userMessage.token
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
			url:'http://119.29.26.47:8080/v1/groups/'+$scope.userMessage.id+'/'+'group_id',
			headers:{
				'x-auth-token':$scope.userMessage.token
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
			url:'http://119.29.26.47:8080/v1/groups/'+$scope.userMessage.id+'/'+'group_id',
			data:$scope.alterGroupMessage,
			headers:{
				'x-auth-token':$scope.userMessage.token
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
			url:'http://119.29.26.47:8080/v1/groups/'+'group_id'+'/users/'+$scope.userMessage.id,
			headers:{
				'x-auth-token':$scope.userMessage.token
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
			url:'http://119.29.26.47:8080/v1/groups/'+'group_id'+'/users',
			data:{'user_ids':['uid1','uid2']},
			headers:{
				'x-auth-token':$scope.userMessage.token
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
});

