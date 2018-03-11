package com.lc.common.jedis;

import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {
    private JedisCluster jedisCluster;


    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    @Override
    public Long hdel(String key, String... field) {
        return jedisCluster.hdel(key, field);
    }

    @Override
    public Long del(String key) {
        return jedisCluster.del(key);
    }
}
