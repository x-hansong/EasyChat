//生成左侧列表
var liststr;
var listarray=[
    {"name":"张力","id":"1","img_src":"image/cong.jpg"},
    {"name":"肖航","id":"2","img_src":"image/cong.jpg"},
    {"name":"肖劲","id":"3","img_src":"image/cong.jpg"},
    {"name":"肖汉松","id":"4","img_src":"image/cong.jpg"},
    {"name":"吴涛宇","id":"5","img_src":"image/cong.jpg"},
    {"name":"杨民浩","id":"6","img_src":"image/cong.jpg"}
];
function createlist() {
    for (var i = 0;listarray[i].name!=null;i++) {
        var $list = $(
            "<div class='chatmessage'>"
            + "<a href='#' onclick='chatwith(this)' >"
            + "<div class='chatmessage-1'>"
            + "<div class='chatmessage-1-image'>"
            + "<img class='chat-1-image' width='40px' height='40px'  />"
            + "</div>"
            + "<div class='chatmessage-1-info'>"
            + "<h4>" + listarray[i].name + "</h4>"
            + "</div>"
            + "</div>"
            + "</a>"
            + "</div>"
        );
        $(".allchatmessage").append($list);
        $(".chat-1-image").attr("src",listarray[i].img_src);
    };
}

