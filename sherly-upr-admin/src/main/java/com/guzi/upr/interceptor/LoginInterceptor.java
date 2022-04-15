package com.guzi.upr.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.util.JwtUtil;
import com.guzi.upr.util.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author 谷子毅
 * @date 2022/3/24
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String DEFAULT_RELEASE = "/api/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 登录接口不需要token校验
        if (DEFAULT_RELEASE.equals(request.getRequestURI())) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new BizException(ResultAdminEnum.TOKEN_NOT_FOUND);
        }

        try {
            String tokenParamJson = JwtUtil.parseToken(token);
            // 将token内容解析存储到threadLocal中
            ThreadLocalUtil.set(OBJECT_MAPPER.readValue(tokenParamJson, TokenParam.class));
        } catch (Exception e) {
            throw new BizException(ResultAdminEnum.TOKEN_ERROR);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // threadLocal使用完后必须remove，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
