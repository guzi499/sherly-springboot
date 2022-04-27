package com.guzi.upr.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.model.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new Result("999999", "你个吊毛无权限！");
        String jsonResult = OBJECTMAPPER.writeValueAsString(result);

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonResult);
    }
}
