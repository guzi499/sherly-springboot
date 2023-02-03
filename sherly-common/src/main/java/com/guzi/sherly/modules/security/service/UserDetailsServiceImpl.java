package com.guzi.sherly.modules.security.service;

import cn.hutool.core.util.IdUtil;
import com.guzi.sherly.admin.accountuser.dao.AccountUserDao;
import com.guzi.sherly.admin.accountuser.model.AccountUserDO;
import com.guzi.sherly.admin.menu.dao.MenuDao;
import com.guzi.sherly.admin.menu.model.MenuDO;
import com.guzi.sherly.admin.role.dao.RoleMenuDao;
import com.guzi.sherly.admin.role.model.RoleMenuDO;
import com.guzi.sherly.admin.tenant.dao.TenantDao;
import com.guzi.sherly.admin.tenant.model.TenantDO;
import com.guzi.sherly.admin.user.dao.UserDao;
import com.guzi.sherly.admin.user.dao.UserRoleDao;
import com.guzi.sherly.admin.user.model.UserDO;
import com.guzi.sherly.admin.user.model.UserRoleDO;
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
        AccountUserDO accountUserDO = accountUserDao.getByPhone(phone);
        if (accountUserDO == null) {
            throw new BizException(NO_REGISTER);
        }

        // 租户过期校验
        String lastLoginTenantCode = accountUserDO.getLastLoginTenantCode();
        TenantDO tenantDO = tenantDao.getByTenantCode(lastLoginTenantCode);
        if (tenantDO.getExpireTime().getTime() <= System.currentTimeMillis()) {
            throw new BizException(TENANT_EXPIRED, tenantDO.getTenantName());
        }

        SecurityUtil.setOperateTenantCode(accountUserDO.getLastLoginTenantCode());

        // 根据查询参数查询用户信息
        UserDO userDO = userDao.getByPhone(phone);

        if (Objects.equals(userDO.getEnable(), DISABLE)) {
            throw new BizException(FORBIDDEN);
        }

        List<UserRoleDO> userRoleDOs = userRoleDao.listByUserId(userDO.getUserId());
        List<Long> roleIds = userRoleDOs.stream().map(UserRoleDO::getRoleId).collect(Collectors.toList());
        List<RoleMenuDO> roleMenuDOs = roleMenuDao.listByRoleIds(roleIds);
        List<Long> menuIds = roleMenuDOs.stream().map(RoleMenuDO::getMenuId).distinct().collect(Collectors.toList());

        List<String> permissions = Collections.emptyList();
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<MenuDO> menuDOs = menuDao.listByIds(menuIds);
            permissions = menuDOs.stream().filter(e -> !Objects.equals(e.getMenuType(), DIR.getType())).map(MenuDO::getPermission).filter(StringUtils::hasText).collect(Collectors.toList());
        }

        // 响应userDetails用于登录校验
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        loginUserDetails.setUserDO(userDO);
        loginUserDetails.setAccountUserDO(accountUserDO);
        loginUserDetails.setPermissions(permissions);
        loginUserDetails.setSessionId(IdUtil.randomUUID());

        return loginUserDetails;
    }
}
