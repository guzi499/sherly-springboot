package com.guzi.sherly.modules.security.model;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.guzi.sherly.admin.accountuser.model.AccountUserDO;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.admin.useronline.model.UserOnline;
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
    private UserDO userDO;

    /** 用户账户信息 */
    private AccountUserDO accountUserDO;

    /** 权限list */
    private List<String> permissions;

    /** 会话编号 */
    private String sessionId;

    /**
     * 获取用户登录缓存对象
     *
     * @return
     * @param request
     */
    public RedisSecurityModel getRedisSecurityModel(HttpServletRequest request) {
        RedisSecurityModel redisSecurityModel = new RedisSecurityModel();

        SecurityModel securityModel = new SecurityModel();
        BeanUtils.copyProperties(userDO, securityModel);
        securityModel.setSessionId(sessionId);
        securityModel.setTenantCode(accountUserDO.getLastLoginTenantCode());

        UserOnline userOnline = new UserOnline();
        BeanUtils.copyProperties(userDO, userOnline);
        userOnline.setLoginTenantCode(accountUserDO.getLastLoginTenantCode());
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
        return accountUserDO.getPassword();
    }

    @Override
    public String getUsername() {
        return accountUserDO.getPhone();
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

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
