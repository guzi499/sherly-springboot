package com.guzi.sherly.modules.security.service;

import cn.hutool.core.util.IdUtil;
import com.guzi.sherly.manager.*;
import com.guzi.sherly.model.admin.*;
import com.guzi.sherly.model.exception.BizException;
import com.guzi.sherly.modules.security.model.LoginUserDetails;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.model.contants.CommonConstants.*;
import static com.guzi.sherly.model.exception.enums.AdminErrorEnum.*;

/**
 * @author 谷子毅
 * @date 2022/5/5
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserManager userManager;

    @Resource
    private UserRoleManager userRoleManager;

    @Resource
    private MenuManager menuManager;

    @Resource
    private RoleMenuManager roleMenuManager;

    @Resource
    private AccountUserManager accountUserManager;

    @Resource
    private TenantManager tenantManager;

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
        loginUserDetails.setSessionId(IdUtil.randomUUID());

        return loginUserDetails;
    }
}
