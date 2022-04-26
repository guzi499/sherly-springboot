package com.guzi.upr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guzi.upr.enums.ResultAdminEnum;
import com.guzi.upr.exception.BizException;
import com.guzi.upr.interceptor.LoginUserDetails;
import com.guzi.upr.interceptor.ThreadLocalModel;
import com.guzi.upr.manager.*;
import com.guzi.upr.model.PageResult;
import com.guzi.upr.model.admin.Menu;
import com.guzi.upr.model.admin.Role;
import com.guzi.upr.model.admin.RoleMenu;
import com.guzi.upr.model.admin.User;
import com.guzi.upr.model.dto.UserInsertDTO;
import com.guzi.upr.model.dto.UserPageDTO;
import com.guzi.upr.model.dto.UserUpdateDTO;
import com.guzi.upr.model.vo.UserPageVo;
import com.guzi.upr.model.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 谷子毅
 * @date 2022/3/24
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private UserRoleManager userRoleManager;

    /**
     * 用户分页
     *
     * @param dto
     * @return
     */
    public PageResult listPage(UserPageDTO dto) {
        // 分页查询
        IPage<User> page = userManager.page(dto.getPage());

        // 对象转换成vo类型
        List<UserPageVo> result = page.getRecords().stream().map(e -> {
            UserPageVo userPageVo = new UserPageVo();
            BeanUtils.copyProperties(e, userPageVo);
            return userPageVo;
        }).collect(Collectors.toList());

        return PageResult.build(result, page.getCurrent(), page.getSize(), page.getTotal());
    }

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    public UserVo getOne(Long userId) {

        User user = userManager.getById(userId);

        // 查询角色
        List<Role> roles = roleManager.listByUserId(userId);
        List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());

        // 组装vo
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        vo.setRoleIds(roleIds);

        return vo;
    }

    /**
     * 新增用户
     *
     * @param dto
     */
    public void saveOne(UserInsertDTO dto) {
        // 去重
        User one = userManager.getByPhone(dto.getPhone());
        if (one != null) {
            throw new BizException(ResultAdminEnum.USER_REPEAT);
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setEnable(0);
        user.setPassword("123456");
        userManager.save(user);

        // 保存用户角色数据
        userRoleManager.saveUserRole(user.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户更新
     *
     * @param dto
     */
    public void updateOne(UserUpdateDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        userManager.updateById(user);

        userRoleManager.removeUserRoleByUserId(dto.getUserId());
        userRoleManager.saveUserRole(dto.getUserId(), dto.getRoleIds());
    }

    /**
     * 用户删除
     *
     * @param userId
     */
    public void removeOne(Long userId) {
        userManager.removeById(userId);
    }

    /**
     * 用户禁用/启用
     *
     * @param userId
     */
    public void banOne(Long userId, Integer enable) {
        userManager.banOne(userId, enable);
    }

    @Override
    public UserDetails loadUserByUsername(String loginParams) throws UsernameNotFoundException {
        // 获取用户查询参数 [tenantCode:phone]
        String[] loginParamArray = loginParams.split(":");
        String tenantCode = loginParamArray[0];
        String phone = loginParamArray[1];

        // 设置当前操作租户存入当前执行线程
        ThreadLocalModel threadLocalModel = new ThreadLocalModel();
        threadLocalModel.setOperateTenantCode(tenantCode);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(threadLocalModel, null));

        // 根据查询参数查询用户信息
        User user = userManager.getByPhone(phone);

        List<Role> roles = roleManager.listByUserId(user.getUserId());
        List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
        List<RoleMenu> roleMenus = roleMenuManager.listByRoleIds(roleIds);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());
        List<Menu> menus = menuManager.listByIds(menuIds);
        List<String> permissions = menus.stream().map(Menu::getPermission).filter(Objects::nonNull).collect(Collectors.toList());

        // 响应userDetails用于登录校验
        LoginUserDetails loginUserDetails = new LoginUserDetails();
        loginUserDetails.setUser(user);
        loginUserDetails.setPermissions(permissions);

        return loginUserDetails;
    }
}
