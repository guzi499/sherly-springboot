package com.guzi.upr.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.util.JwtUtil;
import com.guzi.upr.util.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("/login".equals(request.getRequestURI())) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new BizException(ResultAdminEnum.TOKEN_NOT_FOUND);
        }
        try {
            String tokenParamJson = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(OBJECT_MAPPER.readValue(tokenParamJson, TokenParam.class));
        }catch (Exception e) {
            throw new BizException(ResultAdminEnum.TOKEN_ERROR);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ThreadLocalUtil.remove();
    }
}
