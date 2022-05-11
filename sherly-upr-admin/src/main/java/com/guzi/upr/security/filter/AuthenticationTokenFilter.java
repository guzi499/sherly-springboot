package com.guzi.upr.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.security.SherlyUserDetails;
import com.guzi.upr.security.ThreadLocalModel;
import com.guzi.upr.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    private RedisTemplate<String, String> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 获取token
        String token = request.getHeader("token");

        // 如果token不存在则放行
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token数据
        String keyLabel;
        try {
            keyLabel = JwtUtil.parseToken(token);
        } catch(Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        // 获取手机号
        String phone = keyLabel.split("#")[0];

        // 从redis获取loginUser信息
        String redisString = redisTemplate.opsForValue().get(RedisKey.GENERATE_USER + phone);
        if (redisString == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // redis续期
        redisTemplate.expire(RedisKey.GENERATE_USER + phone, 6, TimeUnit.HOURS);

        // loginUser类型转换
        SherlyUserDetails loginUser = OBJECTMAPPER.readValue(redisString, new TypeReference<SherlyUserDetails>() {});

        // 设置threadLocalModel
        ThreadLocalModel threadLocalModel = new ThreadLocalModel();
        BeanUtils.copyProperties(loginUser.getUser(), threadLocalModel);
        threadLocalModel.setTenantCode(loginUser.getAccountUser().getLastLoginTenantCode());

        List<SimpleGrantedAuthority> authorities = loginUser.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        // threadLocalModel存入当前执行线程
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(threadLocalModel,null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
