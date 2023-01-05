package com.guzi.sherly.modules.security.handler;

import cn.hutool.json.JSONUtil;
import com.guzi.sherly.common.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.guzi.sherly.common.exception.enums.CommonErrorEnum.ACCESS_DENY;

/**
 * @author 谷子毅
 * @date 2022/4/27
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Result result = Result.error(ACCESS_DENY);
        String jsonResult = JSONUtil.toJsonStr(result);

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonResult);
    }
}
