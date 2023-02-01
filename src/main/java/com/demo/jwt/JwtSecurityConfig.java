package com.demo.jwt;

import com.demo.redis.RedisTool;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * JwtFilter를 필터로 등록하기 위한 설정 파일
 */
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;
    private RedisTool redisTool;
    public JwtSecurityConfig(TokenProvider tokenProvider, RedisTool redisTool) {
        this.tokenProvider = tokenProvider;
        this.redisTool = redisTool;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtFilter(tokenProvider,redisTool),
                UsernamePasswordAuthenticationFilter.class
        );
        http.addFilterBefore(new JwtExceptionFilter(), JwtFilter.class);
    }
}