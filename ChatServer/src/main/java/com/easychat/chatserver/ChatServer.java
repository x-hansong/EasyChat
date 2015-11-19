package com.easychat.chatserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by yonah on 15-11-2.
 */
public class ChatServer {
    private static InternalLogger logger = InternalLoggerFactory.getInstance(ChatServer.class);
    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //用于监听端口的事件循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于处理接收到的socket的事件循环
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            logger.debug("WebsocketChatServer on");

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            logger.debug("WebsocketChatServer close");

        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8000;
        }
        new ChatServer(port).run();
    }
}
