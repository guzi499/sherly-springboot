package com.guzi.upr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.UserManager;
import com.guzi.upr.interceptor.TokenParam;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.LoginDTO;
import com.guzi.upr.model.vo.LoginVO;
import com.guzi.upr.util.JwtUtil;
import com.guzi.upr.util.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
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
    private UserManager userManager;

    /**
     * 登录
     * @param dto
     * @return
     */
    public LoginVO login(LoginDTO dto) throws JsonProcessingException {

        // 往 threadLocal中放入 tenantCode 目的是实现动态表名
        TokenParam tokenParam = new TokenParam();
        tokenParam.setTenantCode(dto.getTenantCode());
        ThreadLocalUtil.set(tokenParam);

        User user = userManager.getOneByPhoneAndPwd(dto.getPhone(), dto.getPassword());
        if (user == null) {
            throw new BizException(ResultAdminEnum.LOGIN_ERROR);
        }
        BeanUtils.copyProperties(user, tokenParam);

        // 生成 token
        String token = JwtUtil.generateToken(OBJECTMAPPER.writeValueAsString(tokenParam));

        return new LoginVO(token);
    }
}
