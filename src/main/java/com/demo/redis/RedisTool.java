package com.demo.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
/**
 * refresh 토큰을 저장하는 redis 사용을 위한 클래스
 * redis는 별도의 프로그램을 설치해야 해서 일단 주석처리 해놓았습니다
 * redis : 메인 메모리인 RAM에 데이터를 올려 사용하기 때문에 DB들보다 빠르고 가볍고, 굉장히 빠른 액세스가 가능하다.
 */
@Component
@RequiredArgsConstructor
public class RedisTool {
    private final RedisTemplate redisTemplate;
    public void setRedisValues(String token, String nickname){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(token, nickname, Duration.ofMinutes(10));  // 1분 뒤 메모리에서 삭제
        redisTemplate.expire(token, 10, TimeUnit.MINUTES);
    }

    // 키값으로 벨류 가져오기
    public String getRedisValues(String nickname){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(nickname);
    }

    public void delRedisValues(String token) {
        redisTemplate.delete(token.substring(7));
    }

}
