package com.easychat.chatserver;

import com.easychat.chatserver.handler.TextWebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

/**
 * Created by yonah on 15-11-2.
 */
@Component
public class ChatServerInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        /*
        A combination of HttpRequestDecoder and HttpResponseEncoder
        which enables easier server side HTTP implementation.
         */
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        /*
        usually we receive http message infragment,if we want full http message,
        we should bundle HttpObjectAggregator and we can get FullHttpRequest
         */
        pipeline.addLast(new ChunkedWriteHandler());
        /*
        large data streaming such as file transfer requires complicated state management
        in a ChannelHandler implementation. ChunkedWriteHandler manages such complicated
        states so that you can send a large data stream without difficulties.
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        /*
        This handler does all the heavy lifting for you to run a websocket server.
        It takes care of websocket handshaking as well as processing of control frames (Close, Ping, Pong).
        Text and Binary data frames are passed to the next handler in the pipeline (implemented by you) for processing
        Websocket 协议处理器,在握手阶段会把上面的http编码器替换成websocketframe的编码解码器
         */
        pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
