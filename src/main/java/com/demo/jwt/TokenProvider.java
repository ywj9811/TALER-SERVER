package com.demo.jwt;

import com.demo.dto.TokenDto;
import com.demo.redis.RedisTool;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final RedisTool redisTool;

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String USERNAME_KEY = "username";
    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final long now = (new Date()).getTime();
    private Key key;

    /**
     * (유효기간 설정 관련 설명)
     * Date 형식 -> 밀리세컨드로 구성
     * 따라서 Date + ??? -> ???는 밀리세컨드 단위여야 함
     * application.properties 기본유효기간(expiration) : 초단위 (3600초 = 1시간)
     * accessTokenValidityInMilliseconds, refreshTokenValidityInMilliseconds
     * -> 가져와서 1000을 곱해 밀리세컨드 단위로 변환환
    * */

    public TokenProvider(
            RedisTool redisTool,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long tokenValidityInSeconds) {
        this.redisTool = redisTool;
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = tokenValidityInSeconds * 1000; //1시간 -> 임의 설정 값
        this.refreshTokenValidityInMilliseconds = tokenValidityInSeconds * 2 * 1000; //2시간 -> 임의 설정 값
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createTokenDto(Authentication authentication){
        String nickname = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = createAccessToken(nickname, authorities);
        String refreshToken = createRefreshToken(nickname, authorities);

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(String nickname, String authorities) {
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        String accessToken = Jwts.builder()
                .setSubject("Access")
                .claim(USERNAME_KEY,nickname)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(String nickname, String authorities) {
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        String refreshToken = Jwts.builder()
                .setSubject("Refresh")
                .claim(USERNAME_KEY,nickname)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
        //redisTool.setRedisValues(refreshToken,nickname);

        return refreshToken;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // original : User principal = new User(claims.getSubject(), "", authorities);
        User principal = new User(claims.get(USERNAME_KEY).toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
