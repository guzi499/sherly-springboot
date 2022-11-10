package com.guzi.sherly.modules.security.manager;

import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.modules.security.model.LoginUserDetails;
import com.guzi.sherly.modules.security.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.guzi.sherly.model.contants.CommonConstants.LOGIN_LOG_FAIL;
import static com.guzi.sherly.model.contants.CommonConstants.LOGIN_TYPE_PASSWORD;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.ERR_USR_PWD;

/**
 * 自定义登录校验方法
 *
 * @author 谷子毅
 * @date 2022/5/11
 */
@Component
public class SherlyAuthenticationManager implements AuthenticationProvider {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String phone = authentication.getName();
        String password = authentication.getCredentials().toString();

        LoginUserDetails loginUserDetails = (LoginUserDetails) userDetailsServiceImpl.loadUserByUsername(phone);

        if (passwordEncoder.matches(password, loginUserDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(loginUserDetails, null, loginUserDetails.getAuthorities());
        } else {
            throw new BizException(ERR_USR_PWD);
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
