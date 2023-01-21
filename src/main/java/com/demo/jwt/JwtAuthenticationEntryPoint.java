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

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
       // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        Response r = new Response("401 Unauthorized",UNAUTHORIZEROORMESSAGE,UNAUTHORIZEROORCODE);
        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

        String res = objectMapper.writeValueAsString(r);
        PrintWriter writer = response.getWriter();
        writer.write(res);
        writer.flush();


    }

}