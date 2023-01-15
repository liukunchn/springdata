package com.maoyou.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class StandaloneJedisUtil {
    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        pool = new JedisPool(config, "192.168.56.20", 6379);
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void main(String[] args) {
        for (int i=0;i<1000;i++) {
            // 这里使用了从jedisPool中获取的jedis对象，在使用完后必须关闭
            try (Jedis jedis = StandaloneJedisUtil.getJedis()) {
                String result = jedis.set("clientName" + i, "Jedis" + i);
                System.out.println(result);
                String clientName = jedis.get("clientName" + i);
                System.out.println(clientName);
            }
        }

    }
}
