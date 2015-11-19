package com.easychat.chatserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * Created by yonah on 15-11-10.
 */
public class MsgDecoder extends MessageToMessageDecoder<TextWebSocketFrame>{
    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {

    }
}
