package com.demo.jwt;

import com.demo.dto.response.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //필요한 권한이 없이 접근하려 할때 403
        Response r = new Response("403 Forbidden",FORBIDDENEROORMESSAGE,FORBIDDENEROORCODE);
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