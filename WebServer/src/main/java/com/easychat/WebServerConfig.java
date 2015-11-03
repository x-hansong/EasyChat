package com.easychat;

import com.easychat.utils.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * Created by yonah on 15-11-4.
 */
@Configuration
public class WebServerConfig {
    @Bean
    public JedisPool pool() {
        return new JedisPool(Config.REDIS_HOST, Config.REDIS_PORT);
    }
}
