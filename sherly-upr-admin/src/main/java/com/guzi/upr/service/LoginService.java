package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.interceptor.TokenParam;
import com.guzi.upr.mapper.admin.UserMapper;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.util.JwtUtil;
import com.guzi.upr.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 谷子毅
 * @email guzyc@digitalchina.com
 * @date 2022/3/24
 */
@Service
public class LoginService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param dto
     * @return
     */
    public LoginVO login(LoginDTO dto) throws JsonProcessingException {

        TokenParam tokenParam = new TokenParam();
        tokenParam.setTenantCode(dto.getTenantCode());
        ThreadLocalUtil.set(tokenParam);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, dto.getPhone())
                .eq(User::getPassword, dto.getPassword());

        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BizException(ResultAdminEnum.LOGIN_ERROR);
        }

        tokenParam.setNickname(user.getNickname());
        tokenParam.setUserId(user.getUserId());
        tokenParam.setRealName(user.getRealName());
        tokenParam.setTenantCode(dto.getTenantCode());

        String token = JwtUtil.generateToken(OBJECTMAPPER.writeValueAsString(tokenParam));
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        return loginVO;
    }
}
