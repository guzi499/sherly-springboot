package com.guzi.upr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.interceptor.LoginUserDetails;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class LoginService {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 登录
     *
     * @param dto
     * @return
     */
    public LoginVO login(LoginDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getTenantCode() + ":" + dto.getPhone(), dto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUserDetails loginUser = (LoginUserDetails) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String token = JwtUtil.generateToken(userId);

        return new LoginVO(token);
    }
}
