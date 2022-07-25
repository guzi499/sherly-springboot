package com.guzi.upr.security.manager;

import com.guzi.upr.security.model.LoginUserDetails;
import com.guzi.upr.security.service.UserDetailsServiceImpl;
import com.guzi.upr.util.LogRecordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.guzi.upr.model.contants.CommonConstants.LOGIN_LOG_FAIL;
import static com.guzi.upr.model.contants.CommonConstants.LOGIN_TYPE_PASSWORD;

/**
 * 自定义登录校验方法
 *
 * @author 谷子毅
 * @date 2022/5/11
 */
@Component
public class SherlyAuthenticationManager implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private LogRecordUtil logRecordUtil;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String phone = authentication.getName();
        String password = authentication.getCredentials().toString();

        LoginUserDetails loginUserDetails = (LoginUserDetails) userDetailsServiceImpl.loadUserByUsername(phone);

        if (passwordEncoder.matches(password, loginUserDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(loginUserDetails, null, loginUserDetails.getAuthorities());
        } else {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            // 记录日志
            logRecordUtil.recordLoginLog(request, phone, LOGIN_LOG_FAIL, LOGIN_TYPE_PASSWORD);
            throw new BadCredentialsException("用户名或密码错误!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
