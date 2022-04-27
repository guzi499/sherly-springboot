package com.guzi.upr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.enums.RedisKeyTemplateEnum;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.interceptor.LoginUserDetails;
import com.guzi.upr.interceptor.ThreadLocalModel;
import com.guzi.upr.model.admin.AccountUser;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

        // 如果校验不通过，则抛异常
        if(Objects.isNull(authentication)){
            throw new BizException(ResultAdminEnum.LOGIN_ERROR);
        }

        // 获取登录用户信息
        LoginUserDetails loginUser = (LoginUserDetails) authentication.getPrincipal();
        User user = loginUser.getUser();
        AccountUser accountUser = loginUser.getAccountUser();

        // 封装token信息
        ThreadLocalModel threadLocalModel = new ThreadLocalModel();
        BeanUtils.copyProperties(user, threadLocalModel);
        threadLocalModel.setTenantCode(accountUser.getLastLoginTenantCode());

        // 权限信息更新到redis
        String redisString = OBJECTMAPPER.writeValueAsString(loginUser);
        redisTemplate.opsForValue().set(RedisKeyTemplateEnum.USER_DETAILS.getKey() + dto.getPhone(), redisString, 24, TimeUnit.HOURS);

        // 生成token返回前端
        String tokenParamString = OBJECTMAPPER.writeValueAsString(threadLocalModel);
        String token = JwtUtil.generateToken(tokenParamString);

        return new LoginVO(token);
    }
}
