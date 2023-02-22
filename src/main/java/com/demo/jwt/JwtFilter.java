package com.demo.jwt;
import com.demo.dto.TokenDto;
import com.demo.dto.response.Response;
import com.demo.redis.RedisTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

/**
 * spring security와 jwt를 사용하기 위한 jwt용 커스텀 필터
 * UsernamePasswordAuthenticationFilter 앞에 등록
 * 토큰을 받아서 디코딩 후에 authentication객체를 SecurityContextHolder에 저장
 */
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private TokenProvider tokenProvider;
    private RedisTool redisTool;
    public JwtFilter(TokenProvider tokenProvider, RedisTool redisTool) {
        this.tokenProvider = tokenProvider;
        this.redisTool = redisTool;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(token) && tokenProvider.validateToken(token) && redisTool.checkBlackList(token)) {
            if(httpServletRequest.getRequestURI().equals("/user/ReIssueAccessToken")){
                checkRefreshTokenAndReIssueAccessToken(servletResponse,token);
                return;
            }
            Authentication authentication = tokenProvider.getAuthentication(token);
            this.setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private void checkRefreshTokenAndReIssueAccessToken(ServletResponse servletResponse,String token) throws IOException {
        String username = tokenProvider.getUsername(token);
        String rtk = redisTool.getRedisValues(username);

        if(!tokenProvider.getSubject(token).equals("Refresh")){
            Response r = new Response("리프레시 토큰이 아닙니다",1012);
            FilterErrorResponse.sendError(r, (HttpServletResponse) servletResponse);
            return;
        }
        if (!StringUtils.hasText(rtk)){
            Response r = new Response(REFRESHTOKENNULLMESSAGE,REFRESHTOKENNULLCODE);
            FilterErrorResponse.sendError(r, (HttpServletResponse) servletResponse);
            return;
        }
        if(!rtk.equals(token)){
            Response r = new Response(REFRESHTOKENEXCETIONMESSAGE,REFRESHTOKENEXCETIONCODE);
            FilterErrorResponse.sendError(r, (HttpServletResponse) servletResponse);
            return;
        }
        Authentication authentication = tokenProvider.getAuthentication(token);
        TokenDto tokenDto = tokenProvider.createTokenDto(authentication);

        Response r = new Response(tokenDto,SUCCESSMESSAGE,SUCCESSCODE);
        FilterErrorResponse.sendError(r, (HttpServletResponse) servletResponse);
    }
}