package com.easychat.chatserver.handler;

import com.easychat.chatserver.utils.RedisUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by yonah on 15-10-4.
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(TextWebSocketFrameHandler.class);
    private JedisPool pool;

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public TextWebSocketFrameHandler() {
        this.pool = RedisUtils.getPool();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text()));
            }
        }
        printHandler(ctx);
   }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " join "));
        }

        try (Jedis jedis = pool.getResource()){
            jedis.rpush("userip", incoming.remoteAddress().toString());
        }

        channels.add(ctx.channel());
        logger.debug("Client:" + incoming.remoteAddress() + " join");
        printHandler(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " leave "));
        }

        channels.add(ctx.channel());
        logger.debug("Client:" + incoming.remoteAddress() + " leave");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.debug("Client:" + incoming.remoteAddress() + " online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.debug("Client:" + incoming.remoteAddress() + " offline");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        logger.debug("Client:" + incoming.remoteAddress() + " exception");

        cause.printStackTrace();
        ctx.close();
    }

    public void printHandler(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        for (String name : pipeline.names()){
            logger.debug("{}",name);
        }
    }
}
