package com.maoyou.springdataredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringDataRedisApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() throws InterruptedException {
        // string
        ValueOperations<String, String> stringOps = stringRedisTemplate.opsForValue();
        stringOps.set("name", "maoyou");
        String name = stringOps.get("name");
        System.out.println(name);

        stringRedisTemplate.expire("name", 3, TimeUnit.SECONDS);
        Thread.sleep(4000);
        String name2 = stringOps.get("name");
        System.out.println(name2);

        // hash
        HashOperations<String, Object, Object> hashOps = stringRedisTemplate.opsForHash();
        hashOps.put("user1", "name", "maoyou");
        hashOps.put("user1", "age", "18");
        Object user_name = hashOps.get("user1", "name");
        Object user_age = hashOps.get("user1", "age");
        System.out.println(user_name);
        System.out.println(user_age);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "maoyou");
        map.put("age", "20");
        hashOps.putAll("user2", map);
        Map<Object, Object> user = hashOps.entries("user2");
        System.out.println(user);

        // set
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        Set<String> set = new HashSet<>();
        set.add("zhangsan");
        set.add("lisi");
        Long add = setOps.add("names", set.toArray(new String[0]));
        System.out.println(add);
        Set<String> users = setOps.members("names");
        System.out.println(users);
    }

}
