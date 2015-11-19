package com.easychat.chatserver.utils;

import com.easychat.chatserver.DefaultConfig;
import redis.clients.jedis.JedisPool;

/**
 * Created by yonah on 15-11-3.
 */
public class RedisUtils {

    private static final JedisPool pool = new JedisPool(DefaultConfig.REDIS_HOST, DefaultConfig.REDIS_PORT);

    public static JedisPool getPool() {
        return pool;
    }
}
