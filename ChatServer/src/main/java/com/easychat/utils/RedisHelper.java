package com.easychat.utils;

import redis.clients.jedis.JedisPool;

/**
 * Created by yonah on 15-11-3.
 */
public class RedisHelper {

    private static final JedisPool pool = new JedisPool(Config.REDIS_HOST, Config.REDIS_PORT);

    public static JedisPool getPool() {
        return pool;
    }
}
