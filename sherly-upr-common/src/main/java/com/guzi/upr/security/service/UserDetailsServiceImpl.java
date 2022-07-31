package com.guzi.upr.security.service;

import com.guzi.upr.manager.*;
import com.guzi.upr.model.admin.*;
import com.guzi.upr.model.exception.BizException;
import com.guzi.upr.security.model.LoginUserDetails;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.LogRecordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.*;
import static com.guzi.upr.model.exception.enums.AdminErrorEnum.*;

/**
 * @author 谷子毅
 * @date 2022/5/5
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserRoleManager userRoleManager;

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private AccountUserManager accountUserManager;

    @Autowired
    private TenantManager tenantManager;

    @Autowired
    private LogRecordUtil logRecordUtil;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        // 查询用户账户信息
        AccountUser accountUser = accountUserManager.getByPhone(phone);
        if (accountUser == null) {
            throw new BizException(NO_REGISTER);
        }

        // 租户过期校验
        String lastLoginTenantCode = accountUser.getLastLoginTenantCode();
        Tenant tenant = tenantManager.getByTenantCode(lastLoginTenantCode);
        if (tenant.getExpireTime().getTime() <= System.currentTimeMillis()) {
            throw new BizException(TENANT_EXPIRED, tenant.getTenantName());
        }

        SecurityUtil.setOperateTenantCode(accountUser.getLastLoginTenantCode());

        // 根据查询参数查询用户信息
        User user = userManager.getByPhone(phone);

        if (Objects.equals(user.getEnable(), DISABLE)) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            logRecordUtil.recordLoginLog(request, phone, LOGIN_LOG_DISABLE, LOGIN_TYPE_PASSWORD);
            throw new BizException(FORBIDDEN);
        }

        List<UserRole> userRoles = userRoleManager.listByUserId(user.getUserId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<RoleMenu> roleMenus = roleMenuManager.listByRoleIds(roleIds);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());

        List<String> permissions = Collections.emptyList();
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<Menu> menus = menuManager.listByIds(menuIds);
            permissions = menus.stream().filter(e -> !Objects.equals(e.getMenuType(), DIR)).map(Menu::getPermission).filter(StringUtils::hasText).collect(Collectors.toList());
        }

        // 响应userDetails用于登录校验
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        loginUserDetails.setUser(user);
        loginUserDetails.setAccountUser(accountUser);
        loginUserDetails.setPermissions(permissions);

        return loginUserDetails;
    }
}
