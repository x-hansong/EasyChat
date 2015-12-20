var weChatServices = angular.module('weChatServices', []);  //创建服务
weChatServices.factory('chatSocket', ['$scope', '$rootScope' 
   var stompClient;
			
			var wrappedSocket = {
					
					init: function() {
						stompClient = Stomp.over(new SockJS('http://119.29.26.47:8080/chat'));
					},
					connect:function(){
					    stompClient.connect({}, function(frame) {
		                console.log('Connected: ' + frame);
		                var user = 'test';
		                stompClient.subscribe("/queue/channel/"+user, function(msg) {
		                    // handle position update
		                    console.log(msg)
		                });
		               });
		            },
					send: function(object) {
						stompClient.send("/app/chat", {}, object);
					}
			}
			
			return wrappedSocket;
}]);