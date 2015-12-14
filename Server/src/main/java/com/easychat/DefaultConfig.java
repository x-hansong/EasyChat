package com.easychat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by yonah on 15-11-4.
 */
@Configuration
@EnableRedisHttpSession
@PropertySource("classpath:chatserver.properties")
public class DefaultConfig {
    /*
    Redis Config
     */
    @Bean
    StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

//    /*
//    ChatServer Config
//     */
//    @Value("${netty.port}")
//    private int port;
//
//    @Bean(destroyMethod = "shutdownGracefully")
//    public EventLoopGroup bossGroup() {
//        return new NioEventLoopGroup();
//    }
//
//    @Bean(destroyMethod = "shutdownGracefully")
//    public EventLoopGroup workerGroup() {
//        return new NioEventLoopGroup();
//    }
//
//
//    @Bean
//    public ServerBootstrap bootstrap() {
//        ServerBootstrap b = new ServerBootstrap();
//        b.group(bossGroup(),workerGroup())
//                .channel(NioServerSocketChannel.class)
//                .childHandler(new ChatServerInitializer())
//                .option(ChannelOption.SO_BACKLOG, 128)
//                .childOption(ChannelOption.SO_KEEPALIVE, true);
//        return b;
//    }
//
//    @Bean
//    public int port() {
//        return port;
//    }
//
//    /**
//     * Necessary to make the Value annotations work.
//     *
//     * @return PropertySourcesPlaceholderConfigurer
//     */
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

}
