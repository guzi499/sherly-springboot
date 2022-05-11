package com.guzi.upr.security.manager;

import com.guzi.upr.security.SherlyUserDetails;
import com.guzi.upr.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String phone = authentication.getName();
        String password = authentication.getCredentials().toString();

        SherlyUserDetails sherlyUserDetails = (SherlyUserDetails) userDetailsServiceImpl.loadUserByUsername(phone);

        if (passwordEncoder.matches(password, sherlyUserDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(sherlyUserDetails, null, sherlyUserDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("用户名或密码错误!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
