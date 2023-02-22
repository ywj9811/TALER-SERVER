package com.demo.jwt;

import com.demo.dto.response.Response;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class FilterErrorResponse {
    public static void sendError(Response r,HttpServletResponse response) throws IOException {
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
