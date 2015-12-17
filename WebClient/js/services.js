var weChatServices = angular.module('weChatServices', []);  //创建服务
var ws ;

if (!window.WebSocket && window.MozWebSocket)
    window.WebSocket=window.MozWebSocket;
if (!window.WebSocket)
    alert("No Support ");
  //alet1
weChatServices.service('weChatServices_1', ['$scope',  //服务
    function($scope) {
        function newWebSocket() {                           //socket函数
            var wsURL = "ws://119.29.26.47:8000/ws";
            var wst = new WebSocket(wsURL);
            wst.onopen = function (evnt) {
                onOpen(evnt)
            };
            wst.onmessage = function (evnt) {
                onMessage(evnt)
            };
            wst.onclose = function (evnt) {
                onclose(evnt)
            };
            wst.onerror = function (evnt) {
                onError(evnt)
            };
            return wst;
        }
     ws = newWebSocket();

        function onOpen(){
            console.log("success open");
        }
        function onclose(event)
        {
            console.log("Client notified socket has closed",event);
        }
        function onMessage(){
            console.log("RECEIVE:"+event.data);
            var $html =$("<div class='content'>"
                +"<div class='chatmessage-1-image' style='float:right;'>"
                +"<img src='image/cong.jpg' width='40px' height='40px'/>"
                +"</div>"
                +"<div class='bubble'>"
                +"<div class='bubble_cont'>"
                +"<div class='plain'>"
                +"<pre class='js_message_plain ng-binding'>"+event.data+"</pre>"
                +"</div>"
                +"</div>"
                +"</div>"
                +"</div>");
            $(".main-right-chatmessage").append($html);
        }
        function onError(event){
            console.log('ERROR: ', evnt);
        }

    }
]);

weChatServices.service('weChatServices_2', ['$scope',
    function($scope) {}

]);


//发生消息函数
function sendMessage()
{
    if($(".main-right-writemessage").val()==""){
        alert("内容不能为空");
    }
    else {
        var msg = {
            "from": $(".my-name").text(),
            "message": $(".main-right-writemessage").val(),
            "to": $(".you-name").text()
        };
        var msg_str = JSON.stringify(msg);
        send(msg_str);
        $(".main-right-writemessage").val("");
    }
}


function send(data)
{
    console.log("Send:"+data);
   // ws.send(data);
}

function keysend(event){
    if (event.ctrlKey && event.keyCode == 13) {
        sendto();
        $('.main-right-chatmessage')[0].scrollTop = $('.main-right-chatmessage')[0].scrollHeight;
    }
}