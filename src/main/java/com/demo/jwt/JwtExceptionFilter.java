package com.demo.jwt;

import com.demo.dto.response.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.demo.domain.responseCode.ResponseCodeMessage.JWTEXPIREDCODE;
import static com.demo.domain.responseCode.ResponseCodeMessage.UNAUTHORIZEROORCODE;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response); // go to 'JwtFilter'
        } catch (JwtException ex) {
            setErrorResponse(response,ex);
        }
    }

    public void setErrorResponse(HttpServletResponse response,JwtException ex) throws IOException {
        Response r;

        if(ex.getMessage().equals("토큰 기한이 만료되었습니다")){
            r = new Response("401 Unauthorized",ex.getMessage(),JWTEXPIREDCODE);
        }else{
            r = new Response("401 Unauthorized",ex.getMessage(),UNAUTHORIZEROORCODE);
        }

        FilterErrorResponse.sendError(r,response);
    }
}