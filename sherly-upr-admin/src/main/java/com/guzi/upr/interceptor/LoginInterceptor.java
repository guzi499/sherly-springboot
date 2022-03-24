package com.guzi.upr.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.util.JwtUtil;
import com.guzi.upr.util.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new BizException(ResultAdminEnum.TOKEN_NOT_FOUND);
        }
        try {
            String tokenParamJson = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(objectMapper.readValue(tokenParamJson, TokenParam.class));
        }catch (Exception e) {
            throw new BizException(ResultAdminEnum.TOKEN_ERROR);
        }

        return true;
    }
}
