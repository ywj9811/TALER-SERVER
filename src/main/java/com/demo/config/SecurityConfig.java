package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration      //설정 클래스임을 선언
@EnableWebSecurity  //SpringSecurity 사용을 위한 어노테이션, 기본적으로 CSRF 활성화
// SpringSecurity란, Spring기반의 애플리케이션의 보안(인증, 권한, 인가 등)을 담당하는 Spring 하위 프레임워크
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * SpringSecurity 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
                /**
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/user/save").permitAll()
                .antMatchers("/user/parent/save").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/parent/login").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
                 **/
        http.csrf().disable();  // CSRF 비활성화,
        // REST API 서버는 stateless하게 개발하기 때문에 사용자 정보를 Session에 저장 안함
        // jwt 토큰을 Cookie에 저장하지 않는다면, CSRF에 어느정도는 안전.

        //csrf를 비활성화 하게 되면 실행시 인증을 하지 않아도 동작할 수 있다.
        //원래 csrf에 의해서 delete, insert, update 등등에서 인증이 필요함
    }
}
