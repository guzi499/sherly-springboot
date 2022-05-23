package com.guzi.upr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.constants.RedisKey;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.security.model.LoginUserDetails;
import com.guzi.upr.security.model.RedisSecurityModel;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private AuthenticationProvider authenticationProvider;

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
        Authentication authentication = authenticationProvider.authenticate(authenticationToken);

        // 获取登录用户信息
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        // redis缓存登录用户信息
        RedisSecurityModel redisSecurityModel = loginUserDetails.getSecurityModel();

        // 权限信息更新到redis
        String redisString = OBJECTMAPPER.writeValueAsString(redisSecurityModel);
        redisTemplate.opsForValue().set(RedisKey.GENERATE_USER + dto.getPhone(), redisString, 6, TimeUnit.HOURS);

        // 生成key标签作为token内容
        String keyLabel = dto.getPhone() + "#" + System.currentTimeMillis();

        // 生成token返回前端
        return new LoginVO(JwtUtil.generateToken(keyLabel));
    }

    /**
     * 登出
     */
    public void logout() {

        redisTemplate.delete(RedisKey.GENERATE_USER + SecurityUtil.getPhone());

    }
}
