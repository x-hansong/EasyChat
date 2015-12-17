
//浏览器兼容
if (!window.WebSocket && window.MozWebSocket)
    window.WebSocket=window.MozWebSocket;
if (!window.WebSocket)
    alert("No Support ");

//webSocket对象
var ws;

//开启websocket连接，连接服务器
startWebSocket();

//发送消息函数
function sendMessage()
{
    if($(".main-right-writemessage").val()==""){
        alert("内容不能为空！");
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

//子发送消息函数
function send(data)
{
    console.log("Send:"+data);
    ws.send(data);
}

//开启websocket连接函数
function startWebSocket()
{
    ws = new WebSocket("ws://119.29.26.47:8000/ws");
    //连接开启触发函数
    ws.onopen = function(event){
        console.log("success open");
    };
    //收到消息触发函数
    ws.onmessage = function(event)
    {
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
    };
    //连接关闭触发函数
    ws.onclose = function(event) {
        console.log("Client notified socket has closed",event);
    };
}
function keysend(event){
    if (event.ctrlKey && event.keyCode == 13) {
        sendMessage();
        $('.main-right-chatmessage')[0].scrollTop = $('.main-right-chatmessage')[0].scrollHeight;
    }
}

