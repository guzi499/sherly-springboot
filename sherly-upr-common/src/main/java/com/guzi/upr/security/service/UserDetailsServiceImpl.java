package com.guzi.upr.security.service;

import com.guzi.upr.exception.BizException;
import com.guzi.upr.manager.*;
import com.guzi.upr.model.admin.*;
import com.guzi.upr.security.model.LoginUserDetails;
import com.guzi.upr.security.model.SecurityModel;
import com.guzi.upr.security.util.SecurityUtil;
import com.guzi.upr.util.LogRecordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.guzi.upr.model.contants.CommonConstants.*;

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
    private LogRecordUtil logRecordUtil;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        // 因为登录前不存在Authentication，必须手动设置特殊操作数据库code，
        SecurityModel securityModel = new SecurityModel();
        securityModel.setTenantCode("sherly");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(securityModel, null));

        // 查询用户租户信息
        AccountUser accountUser = accountUserManager.getByPhone(phone);
        if (accountUser == null) {
            throw new BadCredentialsException("当前账号未注册！");
        }

        SecurityUtil.setOperateTenantCode(accountUser.getLastLoginTenantCode());

        // 根据查询参数查询用户信息
        User user = userManager.getByPhone(phone);

        if (Objects.equals(user.getEnable(), DISABLE)) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            logRecordUtil.recordLoginLog(request, phone, LOGIN_LOG_DISABLE, LOGIN_TYPE_PASSWORD);
            throw new BizException("999999", "当前账号已被禁用！");
        }

        List<UserRole> userRoles = userRoleManager.listByUserId(user.getUserId());
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<RoleMenu> roleMenus = roleMenuManager.listByRoleIds(roleIds);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());
        List<Menu> menus = menuManager.listByIds(menuIds);
        List<String> permissions = menus.stream().filter(e -> !Objects.equals(e.getMenuType(), DIR)).map(Menu::getPermission).filter(StringUtils::hasText).collect(Collectors.toList());

        // 响应userDetails用于登录校验
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        loginUserDetails.setUser(user);
        loginUserDetails.setAccountUser(accountUser);
        loginUserDetails.setPermissions(permissions);

        return loginUserDetails;
    }
}
