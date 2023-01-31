package com.demo.jwt;

import com.demo.dto.TokenDto;
import com.demo.redis.RedisTool;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * access토큰, refresh토큰을 생성하는 클래스
 * 토큰의 유효성 검증도 포함
 */
@Component
public class TokenProvider implements InitializingBean {

    private final RedisTool redisTool;
    private final UserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String USERNAME_KEY = "username";
    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final long now = (new Date()).getTime();
    private Key key;

    public TokenProvider(
            RedisTool redisTool,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long tokenValidityInSeconds,  //1시간
            UserDetailsService userDetailsService) {
        this.redisTool = redisTool;
        this.userDetailsService = userDetailsService;
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
        System.out.println("acc : " + accessToken);
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(String nickname, String authorities) {
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);
        Date currentTime = new Date();

        String accessToken = Jwts.builder()
                .setSubject("Access")
                .claim(USERNAME_KEY,nickname)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(currentTime) //필수!!
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
        redisTool.setRedisValues(nickname,refreshToken); // redis에 refresh 토큰 저장
        redisTool.setExpire(nickname,2);

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

        UserDetails principal = new User(claims.get(USERNAME_KEY).toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Date getExpiration(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            throw new JwtException("잘못된 JWT 서명입니다.");
        }catch (ExpiredJwtException e) {
            logger.warn("토큰 기한이 만료되었습니다", e);
            throw new JwtException("토큰 기한이 만료되었습니다");
        }catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            throw new JwtException("JWT 토큰이 잘못되었습니다.");
        }
    }
}
