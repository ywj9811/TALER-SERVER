package com.demo.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;
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
    public void setRedisValues(String nickname, String token,Date date){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(nickname, token);
        redisTemplate.expireAt(nickname, date);
    }
    // 키값으로 벨류 가져오기
    public String getRedisValues(String nickname){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(nickname);
    }

    public void delRedisValues(String nickname) {
        redisTemplate.delete(nickname);
    }

    public void setBlackList(String nickname, String token, Date date){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set("blackList :"+token,nickname);
        redisTemplate.expireAt(nickname, date);
    }

    public boolean checkBlackList(String token){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String blackList = values.get("blackList :"+token);
        if(StringUtils.hasText(blackList)){
            return false;
        }else{
            return true;
        }
    }

}
