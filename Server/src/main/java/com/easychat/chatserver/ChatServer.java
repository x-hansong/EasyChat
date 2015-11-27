package com.easychat.chatserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by yonah on 15-11-2.
 */
@Component
public class ChatServer {
    static Logger logger = LoggerFactory.getLogger(ChatServer.class);

    @Autowired
    private ServerBootstrap b;

    @Autowired
    private int port;

    private ChannelFuture channelFuture;

    @PostConstruct
    public void start () throws InterruptedException {
        logger.debug("ChatServer on");
        channelFuture = b.bind(port).sync();
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        logger.debug("ChatServer close");
        channelFuture.channel().closeFuture().sync();
    }
//    private static InternalLogger logger = InternalLoggerFactory.getInstance(ChatServer.class);
//
//
//
//    public ChatServer(int port) {
//        this.port = port;
//    }
//
//    public void run() throws Exception {
//        //用于监听端口的事件循环
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        //用于处理接收到的socket的事件循环
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new ChatServerInitializer())
//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);
//
//            logger.debug("WebsocketChatServer on");
//
//            ChannelFuture f = b.bind(port).sync();
//
//            f.channel().closeFuture().sync();
//        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
//
//            logger.debug("WebsocketChatServer close");
//
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        int port;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8000;
//        }
//        new ChatServer(port).run();
//    }
}
