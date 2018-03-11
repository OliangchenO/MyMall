package com.lc.common.jedis;

public interface JedisClient {

    Long hdel(String key, String... field);

    Long del(String key);
}
