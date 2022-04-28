package com.guzi.upr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.interceptor.LoginUserDetails;
import com.guzi.upr.interceptor.ThreadLocalModel;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class LoginService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 登录
     *
     * @param dto
     * @return
     */
    public LoginVO login(LoginDTO dto) throws JsonProcessingException {
        // 封装登录参数
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getPassword());

        // 登录校验
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 获取登录用户信息
        LoginUserDetails loginUser = (LoginUserDetails) authentication.getPrincipal();

        // 生成key标签
        String keyLabel = dto.getPhone() + "#" + System.currentTimeMillis();

        // 权限信息更新到redis
        String redisString = OBJECTMAPPER.writeValueAsString(loginUser);
        redisTemplate.opsForValue().set(RedisKey.GENERATE_USER + dto.getPhone(), redisString, 6, TimeUnit.HOURS);

        // 生成token返回前端
        String token = JwtUtil.generateToken(keyLabel);

        return new LoginVO(token);
    }

    /**
     * 登出
     */
    public void logout() {
        ThreadLocalModel threadLocalModel = (ThreadLocalModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String phone = threadLocalModel.getPhone();

        redisTemplate.delete(RedisKey.GENERATE_USER + phone);

    }
}
