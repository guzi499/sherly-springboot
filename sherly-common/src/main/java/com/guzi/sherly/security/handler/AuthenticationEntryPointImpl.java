package com.guzi.sherly.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.sherly.model.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.guzi.sherly.model.exception.enums.CommonErrorEnum.UNAUTHORIZED;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Result result = Result.error(UNAUTHORIZED);
        String jsonResult = OBJECTMAPPER.writeValueAsString(result);

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonResult);
    }
}
