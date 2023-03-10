package com.demo.jwt;

import com.demo.dto.response.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.demo.domain.responseCode.ResponseCodeMessage.UNAUTHORIZEROORCODE;
import static com.demo.domain.responseCode.ResponseCodeMessage.UNAUTHORIZEROORMESSAGE;

/**
 * 유요한 자격증명을 제공하지 않을 때(ex: 토큰이 없을때, 토큰이 잘못 되었을때 등) 401 에러 handler
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
       // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        Response r = new Response("401 Unauthorized",UNAUTHORIZEROORMESSAGE,UNAUTHORIZEROORCODE);
        FilterErrorResponse.sendError(r,response);


    }

}