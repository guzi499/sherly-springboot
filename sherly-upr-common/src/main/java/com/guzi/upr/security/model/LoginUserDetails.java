package com.guzi.upr.security.model;

import com.guzi.upr.model.admin.AccountUser;
import com.guzi.upr.model.admin.UserOnline;
import com.guzi.upr.model.admin.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录身份比对Model，用于SpringSecurity登录身份验证
 *
 * @author 谷子毅
 * @date 2022/4/26
 */
@Data
public class LoginUserDetails implements UserDetails {

    /** 用户信息 */
    private User user;

    /** 用户账户信息 */
    private AccountUser accountUser;

    /** 权限list */
    private List<String> permissions;

    /**
     * 获取用户登录缓存对象
     *
     * @return
     * @param request
     */
    public RedisSecurityModel getRedisSecurityModel(HttpServletRequest request) {
        RedisSecurityModel redisSecurityModel = new RedisSecurityModel();

        SecurityModel securityModel = new SecurityModel();
        BeanUtils.copyProperties(user, securityModel);
        securityModel.setTenantCode(accountUser.getLastLoginTenantCode());

        UserOnline userOnline = new UserOnline();
        BeanUtils.copyProperties(user, userOnline);
        userOnline.setLoginTenantCode(accountUser.getLastLoginTenantCode());
        userOnline.setLoginTime(new Date());
        userOnline.setIp(request.getRemoteAddr());
        userOnline.setOs(null);
        userOnline.setAddress(null);
        userOnline.setBrowser(null);

        redisSecurityModel.setSecurityModel(securityModel);
        redisSecurityModel.setUserOnline(userOnline);
        redisSecurityModel.setPermissions(permissions);

        return redisSecurityModel;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
