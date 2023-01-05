package com.guzi.sherly.modules.security.filter;

import com.guzi.sherly.common.constants.RedisKey;
import com.guzi.sherly.common.util.JwtUtil;
import com.guzi.sherly.modules.security.model.RedisSecurityModel;
import com.guzi.sherly.modules.security.model.SecurityModel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 获取token
        String token = request.getHeader("token");

        // 如果token不存在则放行
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token数据
        String sessionId;
        try {
            sessionId = JwtUtil.parseToken(token);
        } catch(Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从redis获取redisSecurityModel
        RedisSecurityModel redisSecurityModel = (RedisSecurityModel) redisTemplate.opsForValue().get(RedisKey.SESSION_ID + sessionId);
        if (redisSecurityModel == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // redis续期
        redisTemplate.expire(RedisKey.SESSION_ID + sessionId, 6, TimeUnit.HOURS);

        SecurityModel securityModel = redisSecurityModel.getSecurityModel();
        List<SimpleGrantedAuthority> authorities = redisSecurityModel.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // threadLocalModel存入当前执行线程
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(securityModel,null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
