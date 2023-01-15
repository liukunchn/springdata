package com.maoyou.jedis;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class ClusterJedisUtil {
    private static JedisCluster jedis;

    static {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("192.168.56.20", 7000));
        jedisClusterNodes.add(new HostAndPort("192.168.56.20", 7001));

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .build();

        jedis = new JedisCluster(jedisClusterNodes, config);
    }

    public static JedisCluster getJedis() {
        return jedis;
    }

    public static void main(String[] args) {
        for (int i=0;i<1000;i++) {
            JedisCluster jedis = ClusterJedisUtil.getJedis();
            String result = jedis.set("clientName" + i, "Jedis" + i);
            System.out.println(result);
            String clientName = jedis.get("clientName" + i);
            System.out.println(clientName);
        }

    }
}
