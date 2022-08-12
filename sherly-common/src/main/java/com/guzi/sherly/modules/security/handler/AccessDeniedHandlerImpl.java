package com.guzi.sherly.modules.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.sherly.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.guzi.sherly.model.exception.enums.CommonErrorEnum.ACCESS_DENY;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Result result = Result.error(ACCESS_DENY);
        String jsonResult = OBJECTMAPPER.writeValueAsString(result);

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonResult);
    }
}
