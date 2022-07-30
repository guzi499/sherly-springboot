package com.guzi.upr.security.model;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.guzi.upr.model.admin.AccountUser;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.admin.UserOnline;
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

        String ip = ServletUtil.getClientIP(request);
        String userAgent = request.getHeader("User-Agent");
        UserAgent agent = UserAgentUtil.parse(userAgent);
        if (userAgent != null) {
            userOnline.setOs(agent.getOs().toString());
            userOnline.setBrowser(agent.getBrowser().toString());
        }
        userOnline.setIp(ip);

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
        return accountUser.getPassword();
    }

    @Override
    public String getUsername() {
        return accountUser.getPhone();
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
