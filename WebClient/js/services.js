var weChatServices = angular.module('weChatServices', []);  //创建服务
weChatServices.service('chatSocket', ['$scope',  
    function($scope) {
    	var stompClient=null;
    	function connect() {
            var socket = new SockJS('http://119.29.26.47:8080/chat');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
                var user = $scope.userMwssage.user.name;
                stompClient.subscribe("/queue/channel/"+user, function(msg) {
                    console.log("RECEIVE:"+msg);
					var label =$("<div class='content'>"
					    +"<div class='chatmessage-1-image' style='float:right;'>"
					    +"<img src='image/cong.jpg' width='40px' height='40px'/>"
					    +"</div>"
					    +"<div class='bubble'>"
					    +"<div class='bubble_cont'>"
					    +"<div class='plain'>"
					    +"<pre class='js_message_plain ng-binding'>"+msg+"</pre>"
					    +"</div>"
					    +"</div>"
					    +"</div>"
					    +"</div>");
					$(".main-right-chatmessage").append(label);
                });
            });
        }
        //发送消息函数
		function sendMessage()
		{
		    if($(".main-right-writemessage").val()==""){
		        alert("内容不能为空！");
		    }
		    else {
		        var msg = {
		            "fromUser": $(".my-name").text(),
		            "message": $(".main-right-writemessage").val(),
		            "toUser": $(".you-name").text()
		        };
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
	}
]);