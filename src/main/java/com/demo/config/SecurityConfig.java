package com.demo.config;

import com.demo.jwt.JwtAccessDeniedHandler;
import com.demo.jwt.JwtAuthenticationEntryPoint;
import com.demo.jwt.JwtSecurityConfig;
import com.demo.jwt.TokenProvider;
import com.demo.redis.RedisTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration      //설정 클래스임을 선언
@EnableWebSecurity  //SpringSecurity 사용을 위한 어노테이션, 기본적으로 CSRF 활성화
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final RedisTool redisTool;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, RedisTool redisTool) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.redisTool = redisTool;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                //.antMatchers("/","/**").permitAll() //모든 api에 대한 접근 허용
                .antMatchers("/user/save","/user/parent/save").permitAll()
                .antMatchers("/user/login","/user/parent/login").permitAll()
                .antMatchers("/emailConfirm","/parent/check").permitAll()
                .anyRequest().authenticated()


                .and()
                .apply(new JwtSecurityConfig(tokenProvider,redisTool));

        return http.build();
    }
}
