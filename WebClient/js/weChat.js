if (!window.WebSocket && window.MozWebSocket)
    window.WebSocket=window.MozWebSocket;
if (!window.WebSocket)
    alert("No Support ");
var ws;
startWebSocket();

function sendMessage()
{
    var msg={"from":$(".my-name").text(),"message":$(".main-right-writemessage").val(),"to":$(".you-name").text()};
    var msg_str=JSON.stringify(msg);
    send(msg_str);
    $(".main-right-writemessage").val("");
}
function send(data)
{
    console.log("Send:"+data);
    ws.send(data);
}
function startWebSocket()
{
    ws = new WebSocket("ws://localhost:8080/ws");
    ws.onopen = function(event){
        console.log("success open");
    };
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
    ws.onclose = function(event) {
        console.log("Client notified socket has closed",event);
    };

}
