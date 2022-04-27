package com.guzi.upr.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.enums.RedisKeyTemplateEnum;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.MenuManager;
import com.guzi.upr.manager.RoleManager;
import com.guzi.upr.manager.RoleMenuManager;
import com.guzi.upr.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/4/26
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");

        // 如果token不存在则放行
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 如果token存在则解析token
        ThreadLocalModel threadLocalModel;
        try {
            String jwtParamString = JwtUtil.parseToken(token);
            threadLocalModel = OBJECTMAPPER.readValue(jwtParamString, ThreadLocalModel.class);
        } catch(Exception e) {
            throw new BizException(ResultAdminEnum.TOKEN_ERROR);
        }

        String redisString = redisTemplate.opsForValue().get(RedisKeyTemplateEnum.USER_DETAILS.getKey() + threadLocalModel.getPhone());
        if (redisString == null) {
            throw new BizException(ResultAdminEnum.LOGIN_EXPIRE);
        }

        redisTemplate.expire(RedisKeyTemplateEnum.USER_DETAILS.getKey() + threadLocalModel.getPhone(), 24, TimeUnit.HOURS);

        LoginUserDetails loginUserDetails = OBJECTMAPPER.readValue(redisString, new TypeReference<LoginUserDetails>() {});
        threadLocalModel.setTenantCode(loginUserDetails.getAccountUser().getLastLoginTenantCode());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(threadLocalModel,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        List<SimpleGrantedAuthority> authorities = loginUserDetails.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // threadLocalModel存入当前执行线程
        authenticationToken =
                new UsernamePasswordAuthenticationToken(threadLocalModel,null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
