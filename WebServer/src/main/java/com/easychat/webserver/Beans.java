package com.easychat.webserver;

import com.easychat.webserver.repository.RedisSessionRepository;
import com.easychat.webserver.session.SessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * Created by yonah on 15-11-4.
 */
@Configuration
public class Beans {
    @Bean
    public JedisPool pool() {
        return new JedisPool(DefaultConfig.REDIS_HOST, DefaultConfig.REDIS_PORT);
    }

    @Bean
    public SessionManager sessionManager(){
        return new SessionManager(new RedisSessionRepository());
    }
}
