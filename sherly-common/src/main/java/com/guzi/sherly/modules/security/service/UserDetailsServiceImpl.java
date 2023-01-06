package com.guzi.sherly.modules.security.service;

import cn.hutool.core.util.IdUtil;
import com.guzi.sherly.admin.accountuser.dao.AccountUserDao;
import com.guzi.sherly.admin.accountuser.model.AccountUser;
import com.guzi.sherly.admin.menu.dao.MenuDao;
import com.guzi.sherly.admin.menu.model.Menu;
import com.guzi.sherly.admin.role.dao.RoleMenuDao;
import com.guzi.sherly.admin.role.model.RoleMenu;
import com.guzi.sherly.admin.tenant.dao.TenantDao;
import com.guzi.sherly.admin.tenant.model.Tenant;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.dao.UserRoleDao;
import com.guzi.sherly.admin.user.model.User;
import com.guzi.sherly.admin.user.model.UserRole;
import com.guzi.sherly.common.exception.BizException;
import com.guzi.sherly.modules.security.model.LoginUserDetails;
import com.guzi.sherly.modules.security.util.SecurityUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.sherly.admin.menu.enums.MenuTypeEnum.DIR;
import static com.guzi.sherly.common.contants.CommonConstants.DISABLE;
import static com.guzi.sherly.common.exception.enums.AdminErrorEnum.*;

/**
 * @author 谷子毅
 * @date 2022/5/5
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private AccountUserDao accountUserDao;

    @Resource
    private TenantDao tenantDao;

    @Override
    public UserDetails loadUserByUsername(String phone) {

        // 查询用户账户信息
        AccountUser accountUser = accountUserDao.getByPhone(phone);
        if (accountUser == null) {
            throw new BizException(NO_REGISTER);
        }

        // 租户过期校验
        String lastLoginTenantCode = accountUser.getLastLoginTenantCode();
        Tenant tenant = tenantDao.getByTenantCode(lastLoginTenantCode);
        if (tenant.getExpireTime().getTime() <= System.currentTimeMillis()) {
            throw new BizException(TENANT_EXPIRED, tenant.getTenantName());
        }

        SecurityUtil.setOperateTenantCode(accountUser.getLastLoginTenantCode());

        // 根据查询参数查询用户信息
        User user = userDao.getByPhone(phone);

        if (Objects.equals(user.getEnable(), DISABLE)) {
            throw new BizException(FORBIDDEN);
        }

        List<UserRole> userRoles = userRoleDao.listByUserId(user.getUserId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<RoleMenu> roleMenus = roleMenuDao.listByRoleIds(roleIds);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());

        List<String> permissions = Collections.emptyList();
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<Menu> menus = menuDao.listByIds(menuIds);
            permissions = menus.stream().filter(e -> !Objects.equals(e.getMenuType(), DIR.getType())).map(Menu::getPermission).filter(StringUtils::hasText).collect(Collectors.toList());
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
