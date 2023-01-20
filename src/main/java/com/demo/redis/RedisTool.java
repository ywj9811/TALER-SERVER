package com.demo.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisTool {

    private final RedisTemplate redisTemplate;
    public void setRedisValues(String token, String nickname){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(token, nickname, Duration.ofMinutes(1));  // 3분 뒤 메모리에서 삭제
        redisTemplate.expire(token, 1, TimeUnit.MINUTES);
    }

    // 키값으로 벨류 가져오기
    public String getRedisValues(String token){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(token);
    }

    public void delRedisValues(String token) {
        redisTemplate.delete(token.substring(7));
    }
}
