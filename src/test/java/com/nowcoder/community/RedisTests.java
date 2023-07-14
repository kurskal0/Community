package com.nowcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testHyperLogLog(){
        String key = "test:hll:01";

        for (int i = 0; i <= 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(key, i);
        }

        for (int i = 0; i <= 100000; i++) {
            int ran = (int) (Math.random() * 100000 + 1);
            redisTemplate.opsForHyperLogLog().add(key, ran);
        }

        long ans = redisTemplate.opsForHyperLogLog().size(key);
        System.out.println(ans);
    }
}
